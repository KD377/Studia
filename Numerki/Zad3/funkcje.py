import math
import numpy as np
import matplotlib.pyplot as plt


def offset(begining, end, amount):
    return abs(end - begining) / amount


def schemat_hornera(x, tab):
    wynik = tab[0]
    for i in range(len(tab)-1):
        wynik = wynik * x + tab[i+1]
    return wynik


def f(x):
    return schemat_hornera(x, [1, 18])


def g(x):
    return abs(schemat_hornera(x, [1, -1]))


def h(x):
    return schemat_hornera(x, [2, 3, -1])


def i(x):
    return math.cos(3*x) + 1


def j(x):
    return math.sin(2 * x) + abs(schemat_hornera(x, [3, 0, -1])) + schemat_hornera(x, [2, 0, -5, 0])


def add_knots(begining, end, funkcja, amount):
    x = []
    y = []
    n = offset(begining, end, amount)
    i = 0

    while (begining + n * i) <= end:
        x.append(begining + n*i)
        i += 1

    for elements in x:
        tmp = funkcja(elements)
        y.append(tmp)

    return x, y


def lagrange_interpolation(begin, end, funkcja, amount):

    x_values = np.array(add_knots(begin, end, funkcja, amount)[0])
    y_values = np.array(add_knots(begin, end, funkcja, amount)[1])
    interpolation_points = np.linspace(x_values[0]-0.1, x_values[-1]+0.1)
    interpolated_values = np.array([])
    for x in interpolation_points:
        result = 0
        for k in range(len(x_values)):
            term = y_values[k]
            for l in range(len(x_values)):
                if k != l:
                    term *= (x - x_values[l]) / (x_values[k] - x_values[l])
            result += term
        interpolated_values = np.append(interpolated_values, result)
        coefficients = np.polyfit(x_values, y_values, deg=amount - 1)
    x1 = np.linspace(begin-1, end+1, num=1000)
    y1 = np.zeros(len(x1))
    for m in range(len(x1)):
        y1[m] = funkcja(x1[m])
    plt.plot(x1, y1, label="base polynominal")
    plt.plot(interpolation_points, interpolated_values, "r--", label="interpolation")
    plt.plot(x_values, y_values, "ro", label="knots")

    plt.xlabel('x')
    plt.xlim(begin - 0.5, end + 0.5)
    plt.ylabel('y')
    plt.axhline(y=0, color="#cccccc")
    plt.axvline(x=0, color="#cccccc")
    plt.legend()
    plt.grid()
    plt.show()
    return coefficients
