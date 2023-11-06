import random
import math
import matplotlib.pyplot as plt
import numpy as np


def random_value(x, a, b):
    while True:
        x = random.uniform(x - 50.0, x + 50.0)
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


def symulowane_wyzarzanie():
    T = 500.0
    a = 0.999
    x = random.uniform(-150.0, 150.0)
    for j in range(3000):  # 3 proby
        x2 = random_value(x, -150.0, 150.0)

        print(f3(x2))
        print(f3(x))
        if f3(x2) > f3(x):
            print("git")
            x = x2
            print("x=", x)
        else:
            if math.exp(-(f3(x) - f3(x2)) / (0.1 * T)) > random.random():
                print("wyjatek")
                x = x2
        T = T * a

    return x


x = symulowane_wyzarzanie()

print(x)
print(f3(x))
