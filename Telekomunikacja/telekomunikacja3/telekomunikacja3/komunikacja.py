import socket
from funkcje import save_to_file, save_to_file_bin


def receive(x):
    ip = "10.11.73.4"
    port = 4455
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.bind((ip, port))
    s.listen()

    conn, addr = s.accept()

    with conn:
        print(f'Polaczono z adresem {addr}')
        data = conn.recv(1024)

    if x == 1:
        received = data.decode('utf-8')
        save_to_file("dictionary2", received)
    elif x == 2:
        save_to_file_bin("compressed2.bin", data)
    s.close()
