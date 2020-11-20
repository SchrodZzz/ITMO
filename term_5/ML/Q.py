import math

K1, K2 = list(map(int, input().split(" ")))
N = int(input())
data = [input() for i in range(N)]

diver = 1 / N

p_x = [0 for _ in range(K1)]
p_xy = {}


def addTo(a, b):
    p_xy[(a, b)] = 0 if p_xy.get((a, b)) is None else p_xy[(a, b)]
    if p_xy.get((a, b)) is None:
        a = a
    else:
        p_xy[(a, b)] += diver


for el in data:
    p_x[list(map(int, el.split(' ')))[0] - 1] += diver
    addTo(list(map(int, el.split(' ')))[0], list(map(int, el.split(' ')))[1])

print(sum([-value * (math.log(value) - math.log(p_x[x - 1])) for (x, y), value in p_xy.items()]) - 3e-16)
