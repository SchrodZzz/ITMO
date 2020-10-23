def updIdx():
    global idx
    idx = idx + 1 if idx < k - 1 else 0
    return idx


n, m, k = map(int, input().split())
c = list(map(int, input().split()))

idx = -1
indexed = sorted(range(len(c)), key=lambda i: c[i])
groups = [[] for i in range(k)]
[groups[updIdx()].append(i + 1) for i in indexed]

[print(len(groups[i]), " ".join(map(str, sorted(groups[i])))) for i in range(k)]
