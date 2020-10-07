import random

n = y = c = k = lambdas = errors = tol = y2 = a2 = e2 = None

b = 0
eps = 1e-3
MAX_ITER = 3000


def setVals(_n, _y, _c, _k, _tol=0.001):
    global n, y, c, k, lambdas, b, errors, tol
    n = _n
    y = _y
    c = _c
    k = _k
    lambdas = [0] * n
    b = 0
    errors = [0] * n
    tol = _tol


def update(i1, i2):
    global n, y, c, k, lambdas, b, errors, tol, y2, a2, e2
    if i1 == i2:
        return False

    a1 = lambdas[i1]
    y1 = y[i1]
    e1 = errors[i1] if (0 < lambdas[i1] < c) else (sum([lambdas[j] * y[j] * k[j][i1] for j in range(n)]) - b) - y[i1]

    s = y1 * y2

    if y1 != y2:
        L = max(0, a2 - a1)
        H = min(c, c + a2 - a1)
    else:
        L = max(0, a2 + a1 - c)
        H = min(c, a2 + a1)

    if L == H:
        return False

    k11 = k[i1][i1]
    k12 = k[i1][i2]
    k22 = k[i2][i2]

    eta = k11 + k22 - 2 * k12

    if eta > 0:
        a2_new = a2 + y2 * (e1 - e2) / eta

        if a2_new < L:
            a2_new = L
        elif a2_new > H:
            a2_new = H
    else:
        f1 = y1 * (e1 + b) - a1 * k11 - s * a2 * k12
        f2 = y2 * (e2 + b) - s * a1 * k12 - a2 * k22
        L1 = a2 + s * (a2 - L)
        H1 = a1 + s * (a2 - H)
        Lobj = L1 * f1 + L * f2 + 0.5 * (L1 ** 2) * k11 + 0.5 * (L ** 2) * k22 + s * L * L1 * k12
        Hobj = H1 * f1 + H * f2 + 0.5 * (H1 ** 2) * k11 + 0.5 * (H ** 2) * k22 + s * H * H1 * k12

        if Lobj < Hobj - eps:
            a2_new = L
        elif Lobj > Hobj + eps:
            a2_new = H
        else:
            a2_new = a2

    if abs(a2_new - a2) < eps * (a2_new + a2 + eps):
        return False

    a1_new = a1 + s * (a2 - a2_new)

    new_b = updB(e1, a1, a1_new, a2_new, k11, k12, k22, y1)

    delta_b = new_b - b

    b = new_b

    delta1 = y1 * (a1_new - a1)
    delta2 = y2 * (a2_new - a2)

    for i in range(n):
        if 0 < lambdas[i] < c:
            errors[i] += delta1 * k[i1][i] + delta2 * k[i2][i] - delta_b

    errors[i1] = 0
    errors[i2] = 0

    lambdas[i1] = a1_new
    lambdas[i2] = a2_new

    return True


def updB(e1, a1, a1_new, a2_new, k11, k12, k22, y1):
    global n, y, c, k, lambdas, b, errors, tol, y2, a2, e2
    b1 = e1 + y1 * (a1_new - a1) * k11 + y2 * (a2_new - a2) * k12 + b

    b2 = e2 + y1 * (a1_new - a1) * k12 + y2 * (a2_new - a2) * k22 + b

    if (0 < a1_new) and (c > a1_new):
        new_b = b1
    elif (0 < a2_new) and (c > a2_new):
        new_b = b2
    else:
        new_b = (b1 + b2) / 2
    return new_b


def attemp(i2):
    global n, y, c, k, lambdas, b, errors, tol, y2, a2, e2
    y2 = y[i2]
    a2 = lambdas[i2]
    e2 = errors[i2] if (0 < lambdas[i2] < c) else (sum([lambdas[j] * y[j] * k[j][i2] for j in range(n)]) - b) - y[i2]

    r2 = e2 * y2

    if not ((r2 < -tol and a2 < c) or (r2 > tol and a2 > 0)):
        return 0

    non_bound_indices = list([i for i in range(n) if 0 < lambdas[i] < c])
    jj = -1
    if len(non_bound_indices) > 1:
        max = 0

        for j in non_bound_indices:
            e1 = errors[j] - y[j]
            step = abs(e1 - e2)
            if step > max:
                max = step
                jj = j
    i1 = jj

    if i1 >= 0 and update(i1, i2):
        return 1

    rand_i = random.randrange(n)
    all_indices = list(range(n))
    for i1 in all_indices[rand_i:] + all_indices[:rand_i]:
        if update(i1, i2):
            return 1

    return 0


def execute():
    global n, y, c, k, lambdas, b, errors, tol, y2, a2, e2
    diff = 0
    isOk = True

    while diff > 0 or isOk:
        diff = 0

        if isOk:
            for i in range(n):
                diff += attemp(i)
            else:
                tmp = 0
                non_bound_idx = [i for i in range(n) if 0 < lambdas[i] < c]
                for i in non_bound_idx:
                    tmp += attemp(i)
                diff += tmp

            if isOk:
                isOk = False
            elif diff == 0:
                isOk = True


def sol():
    global n, y, c, k, lambdas, b, errors, tol, y2, a2, e2
    n = int(input())
    k = []
    y = []
    for i in range(n):
        *ki, yi = (int(x) for x in input().split())
        k.append(ki)
        y.append(yi)
    c = int(input())

    setVals(n, y, c, k)
    execute()

    for l in lambdas:
        print(l)
    print(-b)


sol()
