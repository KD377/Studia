import math
import numpy as np
from matplotlib import pyplot as plt

def schemat_hornera(x, tab):
    wynik = tab[0]
    for i in range(len(tab)-1):
        wynik = wynik * x + tab[i+1]
    return wynik


def f(x):
    return schemat_hornera(x, [3, 0, -7, 1])

def g(x):
    return (1/2)*math.sin(3*x)

def h(x):
    return (1/3)**x-5

def i(x):
    return (-x)**3 + math.sin(x) -3**x

def df(x):
    return 9*(x**2)-7

def dg(x):
    return (3/2)*math.cos(3*x)

def dh(x):
    return -3**(-x)*math.log(3)

def di(x):
    return -3*x**2 - 3**x * math.log(3) + math.cos(x)

def bisekcja(x1, x2, epsilon, iteracje, funkcja):
    f1 = funkcja(x1)
    f2 = funkcja(x2)
    bi_iter_counter = 0
    if f1 * f2 >= 0:
        print("Nie ma miejsca zerowego w tym przedziale")
        return None
    else:
        if iteracje == 0:
            while abs(x1-x2) > epsilon:
                srodek = (x1+x2)/2
                bi_iter_counter += 1
                if funkcja(srodek) == 0:
                    return srodek
                elif funkcja(x1) * funkcja(srodek) < 0:
                    x2 = srodek
                else:
                    x1 = srodek
        elif epsilon == 0:
            while iteracje > 0:
                srodek = (x1+x2)/2
                if funkcja(srodek) == 0:
                    return srodek
                elif funkcja(x1) * funkcja(srodek) < 0:
                    x2 = srodek
                elif funkcja(x2) * funkcja(srodek) < 0:
                    x1 = srodek
                else:
                    print("error")
                iteracje = iteracje - 1
        return srodek,bi_iter_counter


def metoda_stycznych(x1, x2, epsilon, iteracje, funkcja, pochodna):
    f1 = funkcja(x1)
    f2 = funkcja(x2)
    new_iter_counter = 0
    if f1 * f2 > 0:
        print("Nie ma miejsca zerowego w tym przedziale")
        return None
    else:
        x3 = (x1+x2)/2
        if iteracje == 0:
            while abs(funkcja(x3)) > epsilon:
                new_iter_counter += 1
                a = x3 - (funkcja(x3) / pochodna(x3))
                x3 = a
        elif epsilon == 0:
            while iteracje > 0:
                a = x3 - (funkcja(x3) / pochodna(x3))
                x3 = a
                iteracje -= 1
        return x3,new_iter_counter

def rysuj_wykres(x1, x2, funkcja, root,title):
    x = np.linspace(x1-3, x2+3, num=1000)
    fx = []
    for i in range(len(x)):
        fx.append(funkcja(x[i]))
    plt.plot(x, fx)
    ax = plt.gca()
    ax.set_ylim(-10, 10)
    ax.set_xlim(x1-1, x2+1)
    plt.grid()
    plt.scatter(root, 0, color='red')
    plt.axhline(color='black')
    plt.title(title)
    plt.axvline(color='black')
    plt.axvspan(x1, x2, color='yellow', alpha=0.2)
    plt.show()