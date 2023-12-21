import json


def read_dictionary(file_name):
    with open(file_name, 'r') as file:
        dict = json.loads(file.read())
    return dict


def save_to_file(file_name, text):
    with open(file_name + ".txt", "w+") as file:
        file.write(str(text))
    file.close()


def save_to_file_bin(file_name, text):
    with open(file_name, "wb") as file:
        file.write(text)
    file.close()


def read_binary_file(file_name):
    # read the contents of the binary file
    with open(file_name, 'rb') as f:
        # read the number of zeros added from the first byte
        num_zeros = int.from_bytes(f.read(1), byteorder='big')
        # read the rest of the file and convert it to a string
        b = f.read()
        arr = ''.join(format(x, '08b') for x in b)

    # remove any trailing zeros that were added
    if num_zeros > 0:
        arr = arr[:-num_zeros]

    return arr


def huffman_decoding(encoded_data, huffman_dict):
    reverse_dict = {v: k for k, v in huffman_dict.items()}
    decoded_data = ""

    i = 0
    while i < len(encoded_data):
        j = i + 1
        while encoded_data[i:j] not in reverse_dict and j <= len(encoded_data):
            j += 1
        decoded_data += reverse_dict[encoded_data[i:j]]
        i = j

    save_to_file("decompressed", decoded_data)
    return decoded_data
