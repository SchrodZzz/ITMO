K = int(input())
N = int(input())
data = [list(map(int, input().split())) for i in range(N)]

diver = lambda x: x / N
y_from_x = [[0, 0] for _ in range(K)]
for el in data:
    y_from_x[el[0] - 1][0] += diver(el[1])
    y_from_x[el[0] - 1][1] += diver(1)

print(sum([y * y / N for x, y in data]) - sum([ey * ey / p if p != 0 else 0 for ey, p in y_from_x]))
