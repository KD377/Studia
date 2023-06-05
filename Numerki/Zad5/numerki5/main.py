import numpy as np
import matplotlib.pyplot as plt
from scipy.integrate import quad

def linear(x):
    return x

def absolute(x):
    return np.abs(x)

def polynomial(x):
    return x**3 + 2*x**2 - 5*x + 1

def trigonometric(x):
    return np.sin(x)

def composite(x):
    return np.sin(x**2) + np.cos(x**3)

def horner_scheme(coeffs, x):
    result = 0
    for coeff in coeffs[::-1]:
        result = result * x + coeff
    return result

def approx_function(f, x_values, degree, num_nodes):
    # Obliczanie węzłów i wag dla całkowania Gaussa-Hermite'a
    nodes, weights = np.polynomial.hermite.hermgauss(num_nodes)

    # Obliczanie wartości funkcji w węzłach
    y_values = f(x_values)

    # Dopasowanie wielomianu aproksymacyjnego
    coeffs = np.polyfit(x_values, y_values, degree)
    p = np.poly1d(coeffs)

    return p, coeffs

# Funkcja wyliczająca błąd aproksymacji
def approximation_error(f, p, x_values):
    approx_values = p(x_values)
    true_values = f(x_values)
    error = np.abs(approx_values - true_values)
    avg_error = np.mean(error)
    return avg_error

# Wybór aproksymowanej funkcji
print("Wybierz aproksymowaną funkcję:")
print("1. Funkcja liniowa")
print("2. |x|")
print("3. Wielomian")
print("4. Funkcja trygonometryczna")
print("5. Złożenie funkcji trygonometrycznych")
choice = input("Podaj numer opcji: ")

if choice == '1':
    f = linear
elif choice == '2':
    f = absolute
elif choice == '3':
    f = polynomial
elif choice == '4':
    f = trigonometric
elif choice == '5':
    f = composite
else:
    print("Nieprawidłowy wybór.")
    exit()

# Wprowadzenie danych dotyczących aproksymacji
start = float(input("Podaj początek przedziału aproksymacji: "))
end = float(input("Podaj koniec przedziału aproksymacji: "))
degree = int(input("Podaj stopień wielomianu aproksymującego: "))
num_nodes = int(input("Podaj ilość węzłów do całkowania: "))

# Wywołanie funkcji aproksymującej
x_values = np.linspace(start, end, 1000)
p, coeffs = approx_function(f, x_values, degree, num_nodes)

# Obliczanie błędu aproksymacji
error = approximation_error(f, p, x_values)
print("Błąd aproksymacji:", error)

# Rysowanie wykresu
plt.figure()
plt.plot(x_values, f(x_values), label='Funkcja oryginalna')
plt.plot(x_values, p(x_values), label='Wielomian aproksymacyjny')
plt.legend()
plt.xlabel('x')
plt.ylabel('y')
plt.title('Aproksymacja funkcji')
plt.grid(True)
plt.show()
