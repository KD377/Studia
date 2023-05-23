import numpy as np
import math
from numpy.polynomial import legendre
import matplotlib.pyplot as plt
#from scipy.special import hermite
#from scipy.special import hermite_norm
#from scipy.integrate import quad


def f(x):
    return x**2 + 3

def g(x):
    return math.cos(2*x)

def h(x):
    return 4*math.log(x+3)


def simpson_approximation(f, a, b, n):
    h = (b - a) / n

    first = f(a)
    last = f(b)

    x = a
    sum = 0
    for i in range(n-1):
        x += h
        value = f(x)
        if i % 2 == 0:
            sum += 4 * value
        else:
            sum += 2 * value
    return (h/3) * (first + sum + last)


def approximate_integral_with_accuracy(f, a, b, desired_accuracy):
    n = 2
    previous_approximation = simpson_approximation(f, a, b, n)
    current_approximation = previous_approximation

    while abs(current_approximation - previous_approximation) > desired_accuracy:
        n *= 2
        previous_approximation = current_approximation
        current_approximation = simpson_approximation(f, a, b, n)

    return current_approximation

def gauss_hermite_quadrature(func, num_nodes):
    nodes_weights = {
        2: {
            'nodes': [-0.707107, 0.707107],
            'weights': [0.886227, 0.886227]
        },
        3: {
            'nodes': [-1.224745, 0.000000, 1.224745],
            'weights': [0.295409, 1.181636, 0.295409]
        },
        4: {
            'nodes': [-1.650680, -0.534648, 0.534648, 1.650680],
            'weights': [0.081313, 0.804914, 0.804914, 0.081313]
        },
        5: {
            'nodes': [-2.020183, -0.958572, 0.000000, 0.958572, 2.020183],
            'weights': [0.019953, 0.393619, 0.945309, 0.393619, 0.019953]
        }
    }

    if num_nodes not in nodes_weights:
        raise ValueError("Number of nodes must be 2, 3, 4, or 5.")

    nodes = nodes_weights[num_nodes]['nodes']
    weights = nodes_weights[num_nodes]['weights']

    result = 0.0
    for i in range(num_nodes):
        result += weights[i] * func(nodes[i])

    return result




choice = input("Wybierz funkcje:\n1. x^2 + 5\n2. sin(2x)\n3. 2log(x+1) ")
a = int(input("podaj dolny przedział: "))
b = int(input("podaj gorny przedział: "))
eps = float(input("podaj dokladnosc: "))
if choice == "1":
    wynik=approximate_integral_with_accuracy(f,a,b,eps)
    print("Wynik kwadratura Simpsona: ",wynik)
    for i in range(2,6):
        wynik1 = gauss_hermite_quadrature(f,i)
        print("Wynik dla hermite dla ",i," wezlow: ",wynik1)
if choice == "2":
    wynik = approximate_integral_with_accuracy(g, a, b, eps)
    print("Wynik kwadratura Simpsona: ", wynik)
    for i in range(2,6):
        wynik1 = gauss_hermite_quadrature(g,i)
        print("Wynik dla hermite dla ",i," wezlow: ",wynik1)
if choice == "3":
    wynik = approximate_integral_with_accuracy(h, a, b, eps)
    print("Wynik kwadratura Simpsona: ", wynik)
    for i in range(2,6):
        wynik1 = gauss_hermite_quadrature(h,i)
        print("Wynik dla hermite dla ",i," wezlow: ",wynik1)





