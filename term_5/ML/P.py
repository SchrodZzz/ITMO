K1, K2 = list(map(int, input().split(" ")))
N = int(input())
diver = 1 / N
data = [input() for i in range(N)]

f1 = [0 for _ in range(K1)]
f2 = [0 for _ in range(K2)]
contingency = {}


def addTo(a, b):
    contingency[(a, b)] = 0 if contingency.get((a, b)) is None else contingency[(a, b)]
    if contingency.get((a, b)) is None:
        a = a
    else:
        contingency[(a, b)] += 1


for el in data:
    f1[list(map(int, el.split(' ')))[0] - 1] += diver
    f2[list(map(int, el.split(' ')))[1] - 1] += diver
    addTo(list(map(int, el.split(' ')))[0], list(map(int, el.split(' ')))[1])

res = 0
for (x, y), observed in contingency.items():
    expected = N * f1[x - 1] * f2[y - 1]
    res += (observed - expected) ** 2 / expected - expected

print(res + N)
