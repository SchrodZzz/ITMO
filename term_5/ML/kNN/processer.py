import math


# === distances ===


def manhattan(p, q):
    s = 0
    for a, b in zip(p, q):
        s += abs(a - b)
    return s


def euclidean(p, q):
    s = 0
    for a, b in zip(p, q):
        s += (a - b) ** 2
    return s ** (1 / 2)


def chebyshev(p, q):
    mx = 0
    for a, b in zip(p, q):
        if mx < abs(a - b):
            mx = abs(a - b)
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


# === processers ===


def getDist(t, a, b):
    return distances[t](a, b)


def getKern(t, dist, p):
    return kernel[t](dist / p)


def getClassW(kt, dt, wt, wp, points, classes, arg):
    sorted_points = sorted(list(map(lambda elem: (elem[0], elem[1], getDist(dt, elem[0], arg)),
                                    zip(points, classes))), key=lambda x: x[2])
    if wt == "fixed":
        p = wp
    else:
        p = sorted_points[wp][2]
    ans = [0 for _ in range(max(classes) + 1)]
    for point, point_class, dist in sorted_points:
        ans[point_class] += getKern(kt, dist, p)
    return ans
