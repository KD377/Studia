from funkcje import huffman_encoding, read_file, read_file_bin
from komunikacja import send


def main():
    print("Wybierz \n1.Wysylanie- slownik\n2Wysylanie-skompresowane")
    x = int(input())
    if x == 1:
        before = read_file("before")
        huffman_encoding(before)
        dictionary = read_file("dictionary.txt")
        send(dictionary, 2)
    elif x == 2:
        before = read_file("before")
        huffman_encoding(before)
        compressed = read_file_bin("compressed.bin")
        send(compressed, 1)


main()
