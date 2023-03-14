import math

import numpy as np
import matplotlib.pyplot as plt

funkcja = 0
while funkcja != '1' and funkcja != '2' and funkcja != '3' and funkcja != '4':
    funkcja = input("\n1.Wielomianowa\n2.Trygonometryczna\n3.Wykladnicza\nWybierz funkcje:")
print("\nWybierz przedzial szukania miejsca zerowego\n")
x1 = float(input("od: "))
x2 = float(input("do: "))
kryterium = 'c'
while kryterium != "a" and kryterium != "b":
    kryterium = input("Wybierz kryterium zatrzymania algorytmu\na) spełnienie warunku nałożonego na dokładność albo"
                      "\nb) osiągnięcie zadanej liczby iteracji\n")
    if kryterium != 'a' and kryterium != 'b':
        print('Podano zla wartosc\n')
    elif kryterium == 'a':
        epsilon = float(input("podaj epsilon"))
        iteracje = int(0)
    elif kryterium == "b":
        iteracje = int(input("podaj liczbe iteracji"))
        epsilon = int(0)


def schematHornera(x, tab):
    wynik = tab[0]
    for i in range(len(tab)-1):
        wynik = wynik * x + tab[i+1]
    return wynik


def f(x):
    return schematHornera(x, [3, 0, -7, 1])

def g(x):
    return math.sin(x)

def h(x):
    return (1/3)**x-5

def df(x):
    return 9*(x**2)-7

def dg(x):
    return math.cos(x)

def dh(x):
    return -3**(-x)*math.log(3)

def bisekcja(x1, x2, epsilon, iteracje, funkcja):
    f1 = funkcja(x1)
    f2 = funkcja(x2)

    if f1 * f2 >= 0:
        print("Nie ma miejsca zerowego w tym przedziale")
        return None
    else:
        if iteracje == 0:
            while abs(x1-x2) > epsilon:
                srodek = (x1+x2)/2
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
        return srodek


def metodastycznych(x1, x2, epsilon, iteracje, funkcja, pochodna):
    f1 = funkcja(x1)
    f2 = funkcja(x2)
    if f1 * f2 > 0:
        print("Nie ma miejsca zerowego w tym przedziale")
        return None
    else:
        x3 = (x1+x2)/2
        if iteracje == 0:
            while abs(funkcja(x3)) > epsilon:
                a = x3 - (funkcja(x3) / pochodna(x3))
                x3 = a
        elif epsilon == 0:
            while iteracje > 0:
                a = x3 - (funkcja(x3) / pochodna(x3))
                x3 = a
                iteracje -= 1
        return x3

if funkcja == "1":
    print(bisekcja(x1, x2, epsilon, iteracje, f))
    print(metodastycznych(x1, x2, epsilon, iteracje, f, df))
elif funkcja == "2":
    print(bisekcja(x1, x2, epsilon, iteracje, g))
    print(metodastycznych(x1, x2, epsilon, iteracje, g, dg))
elif funkcja == "3":
    print(bisekcja(x1, x2, epsilon, iteracje, h))
    print(metodastycznych(x1, x2, epsilon, iteracje, h, dh))
elif funkcja == "4":
    print(bisekcja(x1, x2, epsilon, iteracje, f))
    print(metodastycznych(x1, x2, epsilon, iteracje, f, df))

x = np.linspace(-10, 10, num=1000)
fx = []
for i in range(len(x)):
    fx.append(schematHornera(x[i], [1, 1, 0, 1]))
plt.plot(x, fx)
ax = plt.gca()
ax.set_ylim(-15,15)
plt.grid()
plt.axvline()
plt.axvline()
plt.show()
