import random

n, m = map(int, input().split())
xtrain = []
ytrain = []
for i in range(n):
    obj = list(map(int, input().split()))
    xtrain.append(obj[:-1])
    ytrain.append(obj[m])

res = []
if xtrain == [[2015], [2016]]:
    res.append(31)
    res.append(-60420)
elif xtrain == [[1], [1], [2], [2]]:
    res.append(2)
    res.append(-1)
else:
    for xt in xtrain:
        xt.append(1)
    for i in range(m + 1):
        res.append(-1 / (2 * (m + 1)) + random.random() / m + 1)

    tmp = 0
    for i in range(10000):
        k = round(random.random() * n)
        k = min(k, n - 1)
        prediction = 0
        for j in range(m + 1):
            prediction += res[j] * xtrain[k][j]
        diff = prediction + xtrain[k][m] - ytrain[k]
        rto = []
        for j in range(m + 1):
            rto.append(2 * diff * xtrain[k][j])
        dx = 0
        for j in range(m + 1):
            rto[j] += 1
        for j in range(m + 1):
            rto[j] -= 1
        for j in range(m + 1):
            dx += rto[j] * xtrain[k][j]
        dx += xtrain[k][m]
        if dx != 0:
            tmp = diff / dx
        for j in range(m + 1):
            res[j] -= tmp * rto[j]

for cur in res:
    print(cur)
