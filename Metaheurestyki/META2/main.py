import random
import math


def random_value(x, a, b, T):
    while True:
        x = random.uniform(x - 2.0 * T, x + 2.0 * T)
        if a < x < b:
            break
    return x


def f3(x):
    if -105 < x < -95:
        return -2 * abs(x + 100) + 10
    elif 95 < x < 105:
        return -2.2 * abs(x - 100) + 11
    else:
        return 0


def f4(x):
    return x * math.sin(10 * math.pi * x) + 1


def simulated_annealing(funkcja, min_val, max_val, T, a, M):
    x = random.uniform(min_val, max_val)
    for j in range(M):  # 3 attempts
        x2 = random_value(x, min_val, max_val, T)

        if funkcja(x2) > funkcja(x):
            x = x2
        else:
            if math.exp(-(funkcja(x) - funkcja(x2)) / (0.1 * T)) > random.random():
                x = x2
        T = T * a

    return x


choice = int(input("Choose function (1 for f3, 2 for f4): "))
min_val = float(input("Enter the lower bound: "))
max_val = float(input("Enter the upper bound: "))
T = float(input("Enter the starting temperature: "))
a = float(input("Enter the step size: "))
M = int(input("Enter the number of iterations: "))

if choice == 1:
    x = simulated_annealing(f3, min_val, max_val, T, a, M)
else:
    x = simulated_annealing(f4, min_val, max_val, T, a, M)

print(f'Optimal x: {x}')
print(f'Function value at x: {f3(x) if choice == 1 else f4(x)}')
