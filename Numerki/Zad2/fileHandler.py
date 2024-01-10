def read_file(name):
    with open(name, 'r') as file:
        contents = file.read()
    return contents
