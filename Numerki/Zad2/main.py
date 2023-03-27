macierz = [[3, 3, 1], [2, 5, 7], [-4, -10, -14]]

wektor = [1, 20, -20]


def gauss_jordan(A, b):
    n = len(b)

    # eliminacja Gaussa
    for i in range(n):
        # pivotowanie - szukanie największego elementu w kolumnie i-tej
        max_row = i
        for j in range(i + 1, n):
            if abs(A[j][i]) > abs(A[max_row][i]):
                max_row = j
        A[i], A[max_row] = A[max_row], A[i]
        b[i], b[max_row] = b[max_row], b[i]

        # sprawdzenie, czy macierz jest diagonalna
        if A[i][i] == 0:
            for k in range(i + 1, n):
                if A[k][i] != 0:
                    # układ jest sprzeczny
                    return "sprzeczny"
            # układ jest nieoznaczony
            return "nieoznaczony"

        for j in range(n):
            if j != i:
                c = A[j][i] / A[i][i]
                for k in range(i, n):
                    A[j][k] -= c * A[i][k]
                b[j] -= c * b[i]

    # wyliczenie niewiadomych
    for i in range(n):
        if A[i][i] == 0:
            # to nigdy się nie powinno zdarzyć, ale w razie czego
            return None
        b[i] /= A[i][i]

    return b

print(gauss_jordan(macierz, wektor))