from funkcje import huffman_decoding, read_dictionary, read_binary_file
from komunikacja import receive


def main():
    print("Odbieranie")
    receive(1)
    receive(2)
    dict = read_dictionary("dictionary2.txt")
    comp = read_binary_file("compressed2.bin")
    huffman_decoding(comp, dict)
    print(huffman_decoding(comp, dict))



main()
