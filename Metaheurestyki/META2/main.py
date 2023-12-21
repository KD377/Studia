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


def simulated_annealing(funkcja, T):
    if funkcja == f3:
        min_val = -150.0
        max_val = 150.0
        a = 0.999
        M = 3000
    elif funkcja == f4:
        min_val = -1.0
        max_val = 2.0
        a = 0.997
        M = 1200

    x_values = []
    for i in range(5):
        x = random.uniform(min_val, max_val)
        for j in range(M):
            x2 = random_value(x, min_val, max_val, T)

            if funkcja(x2) > funkcja(x):
                x = x2
            else:
                if math.exp(-(funkcja(x) - funkcja(x2)) / (0.1 * T)) > random.random():
                    x = x2
            T = T * a
        x_values.append(x)

    return x_values


temp_list = [50, 100, 200, 300, 400]
temp_list2 = [0.5, 1, 2, 3, 4]
x_values = []
print(temp_list)
choice = int(input("Choose function (1 for f3, 2 for f4): "))

for i in range(5):
    if choice == 1:
        print(temp_list)
        x_values = simulated_annealing(f3, temp_list[i])
        print(f'Optimal x: {x_values}')
        for x in range(5):
            print(f3(x_values[x]))
    else:
        print(temp_list2)
        x_values = simulated_annealing(f4, temp_list2[i])
        print(f'Optimal x: {x_values}')
        for x in range(5):
            print(f4(x_values[x]))
    print(".........")


