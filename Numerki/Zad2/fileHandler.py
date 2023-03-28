def read_file(name):
    tab = []
    matrix = []
    vector = []
    i = 0
    with open(name, 'r') as file:
        # Read each character in the file
        while True:
            # Read a single character
            char = file.read(1)
            # If the character is an empty string, we have reached the end of the file
            if not char:
                break
            # Print the character
            tab[i] = char
            i += 1
    k = 0
    for i in range(len(tab)):
        j = 0
        if tab[i] == ' ':
            vector[k] = tab[i+1]
        elif tab[i] == "\n":
            k += 1
        else:
            matrix[k][j] = tab[i]
            j += 1
    print(matrix)