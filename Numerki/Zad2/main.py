import algorytm
import fileHandler
choice = 'a'
while choice != 'e':
    path = "./przyklady/plik" + input("Podaj numer pliku: ") + ".txt"
    matrix, vector = algorytm.get_matrix(fileHandler.read_file(path))
    print("Wczytano ponizsza macierz współczynników:")
    for wiersz in matrix:
        print(wiersz)
    print("oraz wektor rozwiazan:")
    print(vector)
    print("\nWynik metody gaussa-jordana:")
    outcome = algorytm.jordan(matrix, vector)
    if outcome is not None:
        for i in range(len(outcome)):
            print("x"+str(i+1)+" = "+str(outcome[i])+" ", end="")
        print()
    choice = input("Wprowadz dowolny znak by powtorzyc lub 'e' by zakonczyc: ")
