import numpy as np
import math
import matplotlib.pyplot as plt


def f(x):
    return x ** 3 + x ** 2 + x + 5


def g(x):
    return math.sin(2 * x ** 2)


def h(x):
    return 12 * math.log(x ** 2 + 5)


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


def plot_function(func, x_min, x_max):
    x = np.linspace(x_min, x_max, 100)
    y = [func(i) for i in x]

    function_name = func.__name__  # Get the name of the function
    plt.plot(x, y)
    plt.xlabel('x')
    plt.ylabel('y')
    plt.title('Plot of {}'.format(function_name))  # Set the title using the function name
    plt.grid(True)
    plt.show()


choice = input("Wybierz funkcje:\n1. x^3+x^2+x+5\n2. sin(2x^2)\n3. 12log(x^2+5) ")
a = int(input("podaj dolny przedział: "))
b = int(input("podaj gorny przedział: "))
eps = float(input("podaj dokladnosc: "))
if choice == "1":
    for i in range(2, 6):
        wynik1 = gauss_hermite_quadrature(f, i)
        print("Wynik dla hermite dla ", i, " wezlow: ", wynik1)
    plot_function(f, a, b)
if choice == "2":
    for i in range(2, 6):
        wynik1 = gauss_hermite_quadrature(g, i)
        print("Wynik dla hermite dla ", i, " wezlow: ", wynik1)
    plot_function(g, a, b)
if choice == "3":
    for i in range(2, 6):
        wynik1 = gauss_hermite_quadrature(h, i)
        print("Wynik dla hermite dla ", i, " wezlow: ", wynik1)
    plot_function(h, a, b)
