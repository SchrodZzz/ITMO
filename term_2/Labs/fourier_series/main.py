import sys
import matplotlib.pyplot as plt
import numpy as np


# 11
N = 100


# main function


def f(x):
    if 0 <= x < 1:
        return 2 * x
    elif 1 <= x <= 2:
        return 1
    else:
        print("-> exception : ", x, " ∈ [0,2]")
        sys.exit(1)


xs_1 = np.array([i / 100 for i in range(100)])
xs_2 = np.array([i / 100 for i in range(100, 201)])
ys_1 = np.array([f(x) for x in xs_1])
ys_2 = np.array([f(x) for x in xs_2])

fig, ax = plt.subplots(figsize=(7, 5))
ax.plot(xs_1, ys_1)
ax.plot(xs_2, ys_2)
ax.set_xlabel('График данной в задании функции')
plt.show()


# sin and cos fourier

def a_n(n):
    return ((2 * (np.pi * n * np.math.sin(np.pi * n) + np.math.cos(np.pi * n) - 1)) / (np.pi * np.pi * n * n)) \
           + ((np.math.sin(2 * np.pi * n) - np.math.sin(np.pi * n)) / (np.pi * n))


def b_n(n):
    return ((2 * (np.math.sin(np.pi * n) - np.pi * n * np.math.cos(np.pi * n))) / (np.pi * np.pi * n * n)) \
           - ((np.math.cos(2 * np.pi * n) - np.math.cos(np.pi * n)) / (np.pi * n))


def trig_fourier(n_, x):
    fur_sum = 2 / 2
    for n in range(1, n_ + 1):
        fur_sum += a_n(n) * np.math.cos(np.pi * n * x) + b_n(n) * np.math.sin(np.pi * n * x)
    return fur_sum


xs = np.array([i / 100 for i in range(200)])
ys = np.array([trig_fourier(N, x) for x in xs])
fig_2, ax_2 = plt.subplots(figsize=(7, 5))
ax_2.plot(xs_1, ys_1)
ax_2.plot(xs_2, ys_2)
ax_2.plot([], [])
ax_2.plot(xs, ys)
ax_2.set_xlabel('Ряд Фурье')
plt.show()


# sin only


def f_odd(x):
    if -1 < x < 1:
        return 2 * x
    elif 1 <= x <= 2:
        return 1
    elif -2 <= x <= -1:
        return -1
    else:
        print("-> exception : ", x, " ∈ [-2,2]")
        sys.exit(1)


def b_n_odd(n):
    return ((16 * np.math.sin(np.pi * n / 2) - 8 * np.pi * n * np.math.cos(np.pi * n / 2)) / (np.pi * np.pi * n * n)) \
           - (2 * (np.math.cos(np.pi * n) - np.math.cos(np.pi * n / 2)) / (np.pi * n)) \
           - (2 * (np.math.cos(np.pi * n) - np.math.cos(np.pi * n / 2)) / (np.pi * n))


def sin_fourier(n_, x):
    fur_sum = 0
    for n in range(1, n_ + 1):
        fur_sum += 1 / 2 * b_n_odd(n) * np.math.sin(np.pi * n * x / 2)
    return fur_sum


xs_1 = np.array([i / 100 for i in range(-99, 100)])
xs_2 = np.array([i / 100 for i in range(100, 201)])
xs_3 = np.array([i / 100 for i in range(-200, -100)])
ys_1 = np.array([f_odd(x) for x in xs_1])
ys_2 = np.array([f_odd(x) for x in xs_2])
ys_3 = np.array([f_odd(x) for x in xs_3])

xs = np.array([i / 100 for i in range(-200, 200)])
ys = np.array([sin_fourier(N, x) for x in xs])
fig_3, ax_3 = plt.subplots(figsize=(7, 5))
ax_3.plot(xs_1, ys_1)
ax_3.plot(xs_2, ys_2)
ax_3.plot(xs_3, ys_3)
ax_3.plot(xs, ys)
ax_3.set_xlabel('Ряд Фурье по синусам')
plt.show()


# cos only


def f_even(x):
    if 0 <= x < 1:
        return 2 * x
    elif -1 < x <= 0:
        return -2 * x
    elif 1 <= x <= 2 or -2 <= x <= -1:
        return 1
    else:
        print("-> exception : ", x, " ∈ [-2,2]")
        sys.exit(1)


def a_n_even(n):
    return (2 * (4 * np.pi * n * np.math.sin(np.pi * n / 2) + 8 * np.math.cos(np.pi * n / 2) - 8) / (
            np.pi * np.pi * n * n)) \
           + (2 * (np.math.sin(np.pi * n) - np.math.sin(np.pi * n / 2)) / (np.pi * n)) \
           + (2 * (np.math.sin(np.pi * n) - np.math.sin(np.pi * n / 2)) / (np.pi * n))


def cos_fourier(n_, x):
    fur_sum = 2 / 2
    for n in range(1, n_ + 1):
        fur_sum += 1 / 2 * a_n_even(n) * np.math.cos(np.pi * n * x / 2)
    return fur_sum


xs_1 = np.array([i / 100 for i in range(-99, 100)])
xs_2 = np.array([i / 100 for i in range(100, 201)])
xs_3 = np.array([i / 100 for i in range(-200, -100)])
ys_1 = np.array([f_even(x) for x in xs_1])
ys_2 = np.array([f_even(x) for x in xs_2])
ys_3 = np.array([f_even(x) for x in xs_3])

xs = np.array([i / 100 for i in range(-200, 200)])
ys = np.array([cos_fourier(N, x) for x in xs])
fig_4, ax_4 = plt.subplots(figsize=(7, 5))
ax_4.plot(xs_1, ys_1)
ax_4.plot(xs_2, ys_2)
ax_4.plot(xs_3, ys_3)
ax_4.plot(xs, ys)
ax_4.set_xlabel('Ряд Фурье по косинусам')
plt.show()
