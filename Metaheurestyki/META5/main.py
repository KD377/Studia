import random
import math


def bunkin_dunction_n6(x, y):
    return 100 * math.sqrt(abs(y - 0.01 * x ** 2)) + 0.01 * abs(x + 10)


def booth_function(x, y):
    return (x + 2 * y - 7) ** 2 + (2 * x + y - 5) ** 2


# Klasa reprezentująca cząstkę
class Czastka:
    def __init__(self, min_x, max_x, min_y, max_y):
        self.x = random.uniform(min_x, max_x)  # Losowa pozycja x
        self.y = random.uniform(min_y, max_y)  # Losowa pozycja y
        self.vx = 0  # Prędkość x
        self.vy = 0  # Prędkość y
        self.najlepsze_x = self.x  # Najlepsza znaleziona pozycja x
        self.najlepsze_y = self.y  # Najlepsza znaleziona pozycja y
        self.najlepszy_wynik = float('inf')  # Najlepszy wynik nieskonczonosc


# PSO
def pso(liczba_czastek, liczba_iteracji, min_x, max_x, min_y, max_y, funkcja):
    global najlepszy_wynik_globalny, najlepsze_x_globalne, najlepsze_y_globalne

    czastki = [Czastka(min_x, max_x, min_y, max_y) for _ in range(liczba_czastek)]
    najlepszy_wynik_globalny = float('inf')

    for _ in range(liczba_iteracji):
        for p in czastki:
            wynik = funkcja(p.x, p.y)
            if wynik < p.najlepszy_wynik:
                p.najlepszy_wynik = wynik
                p.najlepsze_x = p.x
                p.najlepsze_y = p.y

            if wynik < najlepszy_wynik_globalny:
                najlepszy_wynik_globalny = wynik
                najlepsze_x_globalne = p.x
                najlepsze_y_globalne = p.y

        # inercja 0.2 wspolczynnik poznawczy 0.35 wspolczynnik spoleczny 0.35 z wykladu
        for p in czastki:
            p.vx = p.vx * 0.2 + 0.35 * random.random() * (p.najlepsze_x - p.x) + 0.45 * random.random() * (
                    najlepsze_x_globalne - p.x)
            p.vy = p.vy * 0.2 + 0.35 * (p.najlepsze_y - p.y) + 0.45 * random.random() * (
                    najlepsze_y_globalne - p.y)
            p.x = p.x + p.vx
            p.y = p.y + p.vy

    return najlepsze_x_globalne, najlepsze_y_globalne, najlepszy_wynik_globalny


# Przykładowe użycie PSO
liczba_czastek = 75
liczba_iteracji = 100
#najlepsze_x, najlepsze_y, najlepszy_wynik = pso(liczba_czastek, liczba_iteracji, -15, -5, -3, 3, bunkin_dunction_n6)
najlepsze_x, najlepsze_y, najlepszy_wynik = pso(liczba_czastek, liczba_iteracji, -10, 10, -10, 10, booth_function)
print(f"Najlepsze x: {najlepsze_x}")
print(f"Najlepsze y: {najlepsze_y}")
print(f"Najlepszy wynik: {najlepszy_wynik}")