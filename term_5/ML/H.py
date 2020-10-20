m = int(input())
amount = 2 ** m
f = [0] * amount
tmp = 0
for i in range(amount):
    f[i] = (int(input()))
    tmp += f[i]

if tmp == 0:
    print(1)
    print(1)
    print('0 ' * (m - 1) + '0')
    print(-0.5)
else:
    print(2)
    print(tmp, 1)
    for i in range(amount):
        if f[i] != 0:
            tmps = 0
            for j in range(m):
                tmps += i % 2
                print(1 if (i % 2) else -1, end=' ')
                i //= 2
            print(1/2 - tmps)

    print('1 ' * (tmp - 1) + '1')
    print(-1/2)
