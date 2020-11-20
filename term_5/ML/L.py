N = int(input())
data = [input() for i in range(N)]
X = [int(el.split(' ')[0]) for el in data]
Y = [int(el.split(' ')[1]) for el in data]

X_mean = sum(X) / N
Y_mean = sum(Y) / N

numerator = sum([(X[i] - X_mean) * (Y[i] - Y_mean) for i in range(N)])
denominator = (sum([(X[i] - X_mean) ** 2 for i in range(N)]) * sum([(Y[i] - Y_mean) ** 2 for i in range(N)])) ** 0.5
if denominator != 0:
    print(numerator / denominator)
else:
    print(0)
