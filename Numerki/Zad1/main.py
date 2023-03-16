import wyswietlanie

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

wyswietlanie.wyswietl_wyniki(x1,x2,epsilon,iteracje,funkcja)

