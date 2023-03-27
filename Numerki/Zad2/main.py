A = [[3, 3, 1], [2, 5, 7], [-4, -10, -14]]
b = [1, 20, -20]


def gauss_jordan(A, b):
    x = len(b)
    for i in range(x):
        #sprawdzenie warunkow
        nieoznaczony = True
        sprzeczny = True
        tablica = [0, 0, 0, 0]
        for j in range(x):
            for k in range(x + 1):
                if k < len(b):
                    tablica[k] = A[k][j]
                else:
                    tablica[k] = b[j]
            for element1 in tablica:
                if element1 != 0:
                    nieoznaczony = False
                    break
            for element2 in range(len(tablica) - 1):
                if tablica[element2] != 0:
                    sprzeczny = False
                    break
            if nieoznaczony:
                return "nieoznaczony"
            elif sprzeczny:
                return "sprzeczny"
        max = abs(A[i][i])
        index = i
        for l in range(x-i):
            if abs(A[i+l][i]) > max:
                index = i + l
        if index != i:
            A[i], A[index] = A[index], A[i]
            b[i], b[index] = b[index], b[i]
        aii = A[i][i]
        b[i] /= aii
        for m in range(x-i):
            A[i][i+m] /= aii
        for n in range(x):
            if n != i:
                ani = A[n][i]
                b[n] -= b[i] * ani
                for o in range(x):
                    A[n][o] -= A[i][o] * ani
        b = [round(element3, 4) for element3 in b]
    return b

print(gauss_jordan(A, b))