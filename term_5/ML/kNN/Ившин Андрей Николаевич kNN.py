#%%
# https://www.openml.org/d/1508 - dataset
import numpy as np
import pandas as pd
from tqdm import tqdm
import matplotlib.pyplot as plt
import processer
#%%
data = pd.read_csv('dataset.csv')
data.head()
#%%
X = data[data.columns[:-1]]
X.head()
#%%
cls, cnt = np.unique(data.Class, return_counts=True)
dict(zip(cls, cnt))
#%%
y = data.Class.apply(lambda status: np.where(cls == status)[0][0])
y.head()
#%%
means = X.mean()
means
#%%
stds = X.std()
stds
#%%
normX = pd.DataFrame(dict([(column, (X.loc[:, column] - means[column]) / stds[column])
                            for column in X.columns]))
X = normX
X.head()
#%%
X.mean()
#%%
X.std()
#%%
def getClass(kt, dt, wt, wp, X, y, argument):
    weights = processer.getClassW(kt, dt, wt, wp, X, y, argument)
    return weights.index(max(weights))
#%%
def getConfusionMatrix(kt, dt, wt, wp):
    classesCnt = len(y.unique())
    res = [[0 for _ in range(classesCnt)] for _ in range(classesCnt)]
    for i in range(data.shape[0]):
        expClass = y[i]
        argument = X.loc[i]
        trainX = X.drop([i])
        trainy = y.drop([i])
        predictedClass = getClass(kt, dt, wt, wp, trainX.values, trainy, argument)
        res[expClass][predictedClass] += 1
    return res
#%%
def getFscore(cm):
    def getPrecisionRecall(idx):
        tp = cm[idx][idx]
        tp_fp = sum([cm[j][idx] for j in range(len(cm))])
        tp_fn = sum(cm[idx])
        precision = 0 if tp_fp == 0 else tp / tp_fp
        recall = 0 if tp_fn == 0 else tp / tp_fn
        return precision, recall


    def calcFscore(precision, recall):
        if precision + recall == 0:
            return 0
        else:
            return 2 * precision * recall / (precision + recall)


    totalCnt = sum([sum(cm[i]) for i in range(len(cm))])
    microPrecision = sum([getPrecisionRecall(i)[0] * sum(cm[i]) for i in range(len(cm))]) / totalCnt
    microRecall = sum([getPrecisionRecall(i)[1] * sum(cm[i]) for i in range(len(cm))]) / totalCnt
    return calcFscore(microPrecision, microRecall)
#%%
distances = []
for i in range(X.shape[0]):
    for j in range(X.shape[0]):
        first = X.loc[i].values
        second = X.loc[j].values
        distances.append(processer.getDist("euclidean", first, second))
distances.sort()
total_dists = len(distances)
distances[int(0.90 * total_dists)]
#%%
def genParams():
    for kt in processer.kernel:
        for dt in processer.distances:
            for wt in ["fixed", "variable"]:
                if wt == "fixed":
                    wps = np.linspace(0.1, 4, 10)
                else:
                    wps = range(1, 101, 10)
                for wp in wps:
                    yield (kt, dt, wt, wp)
#%%
maxFscore = -1
bkt = None
bdt = None
bwt = None
bwp = None
for kt, dt, wt, wp in tqdm(list(genParams())):
    ConfusionMatrix = getConfusionMatrix(kt, dt, wt, wp)
    curFscore = getFscore(ConfusionMatrix)
    if curFscore > maxFscore:
        maxFscore = curFscore
        bkt = kt
        bdt = dt
        bwt = wt
        bwp = wp
#%%
maxFscore, bkt, bdt, bwt, bwp
#%%
fScoresFixed = []
hFixed = np.linspace(0.1, 5, 80)
for h in tqdm(hFixed):
    ConfusionMatrix = getConfusionMatrix(bkt, bdt, "fixed", h)
    curFscore = getFscore(ConfusionMatrix)
    fScoresFixed.append(curFscore)
#%%
fScoresVariable = []
kVariable = range(1, 201, 2)
for k in tqdm(kVariable):
    ConfusionMatrix = getConfusionMatrix(bkt, bdt, "variable", k)
    cur_f_score = getFscore(ConfusionMatrix)
    fScoresVariable.append(cur_f_score)
#%%
plt.plot(hFixed, fScoresFixed)
#%%
plt.plot(kVariable, fScoresVariable)
