n, m, k = map(int, input().split())
c = list(map(int, input().split()))

idx = 0
indexed = sorted(range(len(c)), key=lambda i: c[i])
groups = [[] for i in range(k)]
for i in indexed:
    groups[idx].append(i+1)
    if idx < k-1:
        idx = idx+1
    else:
        idx = 0

for i in range(k):
    print(len(groups[i]), " ".join(map(str, sorted(groups[i]))))