import math


def f1(x):
    y = math.sqrt(x * x + 1)
    return y


def schemat_hornera(x, tab):
    wynik = tab[0]
    for i in range(len(tab) - 1):
        wynik = wynik * x + tab[i + 1]
    return wynik


def wielomiany_hermitea(i, x):
    if i == 0:
        return 1
    elif i == 1:
        return 2 * x
    elif i == 2:
        return 4 * x ** 2 - 2
    elif i == 3:
        return 8 * x ** 3 - 12 * x
    elif i == 4:
        return 16 * x ** 4 - 48 * x ** 2 + 12
    elif i == 5:
        return 32 * x ** 5 - 160 * x ** 3 + 120 * x
    elif i == 6:
        return 64 * x ** 6 - 480 * x ** 4 + 720 * x ** 2 - 120
    elif i == 7:
        return 128 * x ** 7 - 1344 * x ** 5 + 3360 * x ** 3 - 1680 * x
