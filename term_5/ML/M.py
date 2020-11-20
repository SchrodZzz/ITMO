N = int(input())
data = [input() for i in range(N)]
__X = [list(map(int, el.split(' '))) for el in data]

X = {value: index + 1 for index, value in enumerate(sorted([int(el.split(' ')[0]) for el in data]))}
Y = {value: index + 1 for index, value in enumerate(sorted([int(el.split(' ')[1]) for el in data]))}

d = 0
for el in __X:
    d += ((X.get(el[0])) - (Y.get(el[1]))) ** 2

print(1 if N == 1 else 1 - 6 * d / (N * (N ** 2 - 1)))
