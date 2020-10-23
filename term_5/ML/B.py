f1 = lambda p, r: 0 if p + r == 0 else 2 * (p * r) / (p + r)

k = int(input())
cm = [list(map(int, input().split())) for _ in range(k)]
rs = [sum(row) for row in cm]
cs = list(map(sum, zip(*cm)))

print(f1(sum([0 if cs[i] == 0 else cm[i][i] * rs[i] / cs[i] for i in range(k)]) / sum(rs), sum([cm[i][i] for i in range(k)]) / sum(rs)))
print(sum([rs[i] * f1(0 if cm[i][i] + rs[i] - cm[i][i] == 0 else cm[i][i] / (cm[i][i] + rs[i] - cm[i][i]), 0 if cm[i][i] + cs[i] - cm[i][i] == 0 else cm[i][i] / (cm[i][i] + cs[i] - cm[i][i])) / sum(rs) for i in range(k)]))