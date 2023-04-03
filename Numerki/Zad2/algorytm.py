def jordan(matrix, vector):
    rows = len(vector)
    for k in range(rows):
        for row in matrix:
            row_copy = [round(value, 8) for value in row]
            if row_copy == [0] * rows:
                vector_copy = [round(value, 8) for value in vector]
                if vector_copy[matrix.index(row)] == 0:
                    print("Układ nieoznaczony")
                else:
                    print("Układ sprzeczny")
                return None
        max = abs(matrix[k][k])
        index = k
        for i in range(rows - k):
            if abs(matrix[k + i][k]) > max:
                max = abs(matrix[k + i][k])
                index = k + i
        if index != k:
            matrix[k], matrix[index] = matrix[index], matrix[k]
            vector[index], vector[k] = vector[k], vector[index]
        akk = matrix[k][k]
        vector[k] /= akk
        for j in range(rows - k):
            matrix[k][k + j] /= akk
        for i in range(rows):
            if i != k:
                aik = matrix[i][k]
                vector[i] -= vector[k] * aik
                for j in range(rows):
                    matrix[i][j] -= matrix[k][j] * aik
    vector = [round(x, 8) for x in vector]
    return vector


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
