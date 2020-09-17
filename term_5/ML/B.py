k = int(input())
cm = []
for _ in range(k):
    cm.append(list(map(int, input().split())))

rs = [sum(row) for row in cm]
cs = list(map(sum, zip(*cm)))
gs = sum(rs)


def f1(p, r):
    return 0 if p + r == 0 else 2 * (p * r) / (p + r)


def macro():
    prc = sum([0 if cs[i] == 0 else cm[i][i] * rs[i] / cs[i] for i in range(k)]) / gs
    rec = sum([cm[i][i] for i in range(k)]) / gs

    return f1(prc, rec)


def micro():
    tmp = 0
    for i in range(k):
        tp = cm[i][i]
        fp = rs[i] - cm[i][i]
        fn = cs[i] - cm[i][i]

        prc = 0 if tp + fp == 0 else tp / (tp + fp)
        rec = 0 if tp + fn == 0 else tp / (tp + fn)

        tmp += rs[i] * f1(prc, rec) / gs

    return tmp


print(macro(), micro(), sep='\n')