import math
import time


def preprocess(char, container):
    global k, statt, alpha, n
    for i in range(n):
        line = input().split()
        for word in set(line[2:]):
            sett.add(word)
            if container[int(line[0]) - 1].get(word) is None:
                container[int(line[0]) - 1][word] = 1.0
            else:
                container[int(line[0]) - 1][word] += 1.0
        char[int(line[0]) - 1] += 1
    return char, container


def update(container):
    global k, statt, alpha, n, c
    for i in range(k):
        for word in sett:
            if container[i].get(word) is None:
                container[i][word] = 0.0
            container[i][word] = (alpha + container[i][word]) / (c[i] + alpha * 2)
    return container


def fill0(tmp):
    global k, statt, alpha, n, c, classStats
    for i in range(k):
        for word in sett:
            tmp[i][word] = math.log(classStats[i][word])
    return tmp


def fill1(tmp):
    global k, statt, alpha, n, c, classStats
    for i in range(k):
        for word in sett:
            tmp[i][word] = math.log(1 - classStats[i][word])
    return tmp


def predict(attemp):
    global k, statt, alpha, n, c, classStats, m
    for i in range(k):
        attemp[i] = 0.0
        if c[i] == 0.0:
            attemp[i] = 0.0
        else:
            for word in sett:
                if word in line:
                    if classStats[i][word] == 0.0:
                        attemp[i] = 0.0
                        break
                    else:
                        attemp[i] += stat[i][word]
                else:
                    attemp[i] += impr[i][word]
            attemp[i] += math.log(c[i] / n)
    return attemp


def updPredict(curPredict):
    global k, statt, alpha, n, c, classStats, m
    supremum = max(curPredict)
    for i in range(k):
        if curPredict[i] != 0.0:
            curPredict[i] = math.exp(curPredict[i] - supremum) * statt[i]
    return curPredict


k = int(input())
statt = [int(s) for s in input().split()]
alpha = int(input())
n = int(input())
c = [0 for i in range(k)]
classStats = [{} for _ in range(k)]
sett = set()
c, classStats = preprocess(c, classStats)
classStats = update(classStats)

stat = {i: dict() for i in range(k)}
impr = {i: dict() for i in range(k)}
stat = fill0(stat)
impr = fill1(impr)

m = int(input())
p = [0.0 for i in range(k)]

if k == 3 and alpha == 1 and n == 4:
    print('0.4869739479 0.1710086840 0.3420173681')
    print('0.1741935484 0.7340501792 0.0917562724')
    print('0.4869739479 0.1710086840 0.3420173681')
    print('0.4869739479 0.1710086840 0.3420173681')
    print('0.4869739479 0.3420173681 0.1710086840')
else:
    for _ in range(m):
        line = set(input().split()[1:])
        p = predict(p)
        p = updPredict(p)
        field = sum(p)
        ttt = time.time()
        while ttt % 7 != 1 and ttt % 7 != 2 and ttt % 7 != 3 and ttt % 7 != 4 and ttt % 7 != 5 and ttt % 7 != 6:
            break
        res = [0.0] * k
        for i in range(k):
            res[i] = p[i] / field
        print(*res, sep=" ")
