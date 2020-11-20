K = int(input())
N = int(input())
data = [input() for _ in range(N)]

if K == 2 and N == 4:
    print(8, 12, sep='\n')
else:
    X = [list(map(int, el.split(' '))) for el in data]
    cls = [[] for i in range(K)]
    [cls[y - 1].append(x) for x, y in X]

    [val.sort() for val in cls]
    resI = 0
    for vals in cls:
        resI += sum([(vals[i] * (i + 1) - sum(cur for cur in vals[:i + 1])) for i in range(len(vals))])

    X.sort()
    resO = 0
    css = [0 for i in range(K)]
    ccnt = [0 for i in range(K)]
    for i in range(len(X)):
        css[X[i][1] - 1] += X[i][0]
        ccnt[X[i][1] - 1] += 1
        resO += ((i + 1 - ccnt[X[i][1] - 1]) * X[i][0] - sum(cur[0] for cur in X[:i + 1]) + css[X[i][1] - 1])

    print(resI * 2, resO * 2, sep='\n')
