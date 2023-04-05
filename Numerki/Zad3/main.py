import funkcje
import numpy as np

funkcja = 0
while funkcja != '1' and funkcja != '2' and funkcja != '3' and funkcja != '4' and funkcja != '5':
    funkcja = input("\n1.Liniowa\n2.Modul\n3.Wielomianowa\n4.Trygonometryczna\n5.Zlozona\nWybierz funkcje:")
print("\nWybierz przedzial interpolacji\n")
begin = float(input("od: "))
end = float(input("do: "))
amount = int(input("Podaj ilosc punktow: "))

if funkcja == '1':
    coefficients = funkcje.lagrange_interpolation(begin, end, funkcje.f, amount)
    print(coefficients)
elif funkcja == '2':
    coefficients = funkcje.lagrange_interpolation(begin, end, funkcje.g, amount)
    print(coefficients)
elif funkcja == '3':
    coefficients = funkcje.lagrange_interpolation(begin, end, funkcje.h, amount)
    print(coefficients)
elif funkcja == '4':
    coefficients = funkcje.lagrange_interpolation(begin, end, funkcje.i, amount)
    print(coefficients)
elif funkcja == '5':
    coefficients = funkcje.lagrange_interpolation(begin, end, funkcje.j, amount)
    print(coefficients)
