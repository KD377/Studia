import socket


def send(file, x):
    ip = "10.11.73.4"
    port = 4455
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.connect((ip, port))
    if x == 1:
        s.sendall(file)
    elif x == 2:
        s.sendall(file.encode('utf-8'))
    s.close()

