import math


# === distances ===


def manhattan(p, q):
    s = 0
    for i in range(m):
        s += abs(p[i] - q[i])
    return s


def euclidean(p, q):
    s = 0
    for i in range(m):
        s += (p[i] - q[i]) ** 2
    return s ** (1 / 2)


def chebyshev(p, q):
    mx = 0
    for i in range(m):
        if mx < abs(p[i] - q[i]):
            mx = abs(p[i] - q[i])
    return mx


# === karnels ===


def uniform(u):
    if abs(u) < 1:
        return 1 / 2
    return 0


def triangular(u):
    if abs(u) < 1:
        return 1 - abs(u)
    return 0


def epanechnikov(u):
    if abs(u) < 1:
        return (1 - u ** 2) * 3 / 4
    return 0


def quartic(u):
    if abs(u) < 1:
        return ((1 - u ** 2) ** 2) * 15 / 16
    return 0


def triweight(u):
    if abs(u) < 1:
        return ((1 - u ** 2) ** 3) * 35 / 32
    return 0


def tricube(u):
    if abs(u) < 1:
        return ((1 - abs(u) ** 3) ** 3) * 70 / 81
    return 0


def gaussian(u):
    return math.e ** ((-1 / 2) * u ** 2) / (2 * math.pi) ** (1 / 2)


def cosine(u):
    if abs(u) < 1:
        return (math.pi / 4) * math.cos(u * math.pi / 2)
    return 0


def logistic(u):
    return 1 / (math.e ** u + 2 + math.e ** (-u))


def sigmoid(u):
    return 2 / (math.pi * (math.e ** u + math.e ** (-u)))


distances = {
    "manhattan": manhattan,
    "euclidean": euclidean,
    "chebyshev": chebyshev
}

kernel = {
    "uniform": uniform,
    "triangular": triangular,
    "epanechnikov": epanechnikov,
    "quartic": quartic,
    "triweight": triweight,
    "tricube": tricube,
    "gaussian": gaussian,
    "cosine": cosine,
    "logistic": logistic,
    "sigmoid": sigmoid
}

# === input ===

n, m = map(int, input().split())
train = []
for _ in range(n):
    train.append(list(map(int, input().split(' '))))

q = list(map(int, input().split(' ')))
dis = input()
ker = input()
win = input()
h = int(input())

ds = []
ms = []

for i in train:
    ds.append((distances[dis](i[:-1], q), i[-1]))

ds = sorted(ds, key=lambda x: x[0])

for i in range(n):
    ms.append(ds[i][1])
    ds[i] = ds[i][0]

kr = []
krm = []
answer = 0
count = 0
for i in range(n):
    if ds[i] != 0 and (h == 0 or (win == "variable" and ds[h] == 0)):
        kr.append(0)
        krm.append(0)
    elif h == 0 and (0 not in ds):
        print(sum(ms) / n)
        break
    elif h == 0 and (0 in ds):
        if ds[i] == 0:
            count += 1
            answer += ms[i]
    else:
        if win == "fixed":
            u = ds[i] / h
        else:
            if ds[h] != 0:
                u = ds[i] / ds[h]
            else:
                u = 0
        kr.append(kernel[ker](u))
        krm.append(ms[i] * kernel[ker](u))
if count != 0:
    print(answer / count)
if sum(kr) != 0:
    print(sum(krm) / sum(kr))
else:
    print(sum(ms) / n)
