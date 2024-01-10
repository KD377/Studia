import json


class Nodes:
    def __init__(self, probability, symbol, left=None, right=None):
        # probability of the symbol
        self.probability = probability

        # the symbol
        self.symbol = symbol

        # the left node
        self.left = left

        # the right node
        self.right = right

        # the tree direction (0 or 1)
        self.code = ''


def calculate_probability(the_data):
    the_symbols = dict()
    for item in the_data:
        if the_symbols.get(item) is None:
            the_symbols[item] = 1
        else:
            the_symbols[item] += 1
    return the_symbols


the_codes = dict()


def calculate_codes(node, value=''):
    # a huffman code for current node
    new_value = value + str(node.code)

    if node.left:
        calculate_codes(node.left, new_value)
    if node.right:
        calculate_codes(node.right, new_value)

    if not node.left and not node.right:
        the_codes[node.symbol] = new_value

    return json.dumps(the_codes)


def output_encoded(the_data, coding):
    if not isinstance(coding, dict):
        coding = json.loads(coding)

    encoding_output = []
    for element in the_data:
        encoding_output.append(coding[element])

    the_string = ''.join([str(item) for item in encoding_output])
    return the_string


def read_file(file_name):
    with open(file_name, "r") as file:
        before = file.read()
        before = before.rstrip()
    file.close()
    return before


def read_file_bin(file_name):
    with open(file_name, "rb") as file:
        before = file.read()
    file.close()
    return before


def save_dictionary(file_name, dictionary):
    with open(file_name + ".txt", "w+") as file:
        string_dict = str(dictionary)
        file.write(string_dict)
    file.close()


def save_binary_file(arr, file_name):
    # determine the number of zeros needed to make the array divisible by 8
    num_zeros = 8 - (len(arr) % 8)
    if num_zeros == 8:
        num_zeros = 0

    # append the required number of zeros to the array
    arr += '0' * num_zeros

    # convert the array to bytes
    b = bytearray()
    for i in range(0, len(arr), 8):
        byte = arr[i:i + 8]
        b.append(int(byte, 2))

    # write the bytes to a binary file, along with the number of zeros added
    with open(file_name, 'wb') as f:
        f.write(num_zeros.to_bytes(1, byteorder='big'))
        f.write(b)


def huffman_encoding(the_data):
    symbol_with_probs = calculate_probability(the_data)
    the_symbols = symbol_with_probs.keys()
    the_probabilities = symbol_with_probs.values()
    print("symbols: ", the_symbols)
    print("probabilities: ", the_probabilities)

    the_nodes = []

    # converting symbols and probabilities into huffman tree nodes
    for symbol in the_symbols:
        the_nodes.append(Nodes(symbol_with_probs.get(symbol), symbol))

    while len(the_nodes) > 1:
        # sorting all the nodes in ascending order based on their probability
        the_nodes = sorted(the_nodes, key=lambda x: x.probability)

        # picking two smallest nodes
        right = the_nodes[1]
        left = the_nodes[0]

        left.code = 0
        right.code = 1

        # combining the 2 smallest nodes to create new node
        new_node = Nodes(left.probability + right.probability, left.symbol + right.symbol, left, right)

        the_nodes.remove(left)
        the_nodes.remove(right)
        the_nodes.append(new_node)

    huffmanEncoding = calculate_codes(the_nodes[0])
    save_dictionary("dictionary", huffmanEncoding)
    print("symbols with codes", huffmanEncoding)
    encoded = output_encoded(the_data, huffmanEncoding)
    save_binary_file(encoded, "compressed.bin")
