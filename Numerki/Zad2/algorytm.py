def jordan(macierz, wektor):
    liczba_wierszy = len(wektor)
    for k in range(liczba_wierszy):
        for wiersz in macierz:
            wiersz_kopia = [wartosc for wartosc in wiersz]
            if wiersz_kopia == [0] * liczba_wierszy:
                wektor_kopia = [wartosc for wartosc in wektor]
                if wektor_kopia[macierz.index(wiersz)] == 0:
                    print("Układ nieoznaczony")
                else:
                    print("Układ sprzeczny")
                return None
        maks = abs(macierz[k][k])
        index = k
        for i in range(liczba_wierszy - k):
            if abs(macierz[k + i][k]) > maks:
                maks = abs(macierz[k + i][k])
                index = k + i
        if index != k:
            for i in range(liczba_wierszy):
                macierz[k][i], macierz[index][i] = macierz[index][i], macierz[k][i]
            wektor[index], wektor[k] = wektor[k], wektor[index]
        akk = macierz[k][k]
        wektor[k] /= akk
        for j in range(liczba_wierszy - k):
            macierz[k][k + j] /= akk
        for i in range(liczba_wierszy):
            if i != k:
                aik = macierz[i][k]
                wektor[i] -= wektor[k] * aik
                for j in range(liczba_wierszy):
                    macierz[i][j] -= macierz[k][j] * aik
    wektor = [round(x, 8) for x in wektor]
    return wektor


def get_matrix(text_string):
    characters = list(text_string)
    size = 0
    for j in range(len(characters)):
        if characters[j] == "|":
            size += 1
    matrix = [[0.0 for _ in range(size)] for _ in range(size)]
    vector = []
    buffer = []
    row = 0
    column = 0
    for i in range(len(characters)):
        if characters[i] == " ":
            matrix[row][column] = float(''.join(buffer))
            column += 1
            buffer = []
        elif characters[i] == "|":
            matrix[row][column] = float(''.join(buffer))
            buffer = []
        elif characters[i] == "\n":
            vector.append(float(''.join(buffer)))
            row += 1
            column = 0
            buffer = []
        else:
            buffer.append(characters[i])

    return matrix, vector
