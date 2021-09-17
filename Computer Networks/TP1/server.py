import socket
from _thread import start_new_thread

poltronas_reservadas = []


def threaded(c):
    while True:

        # poltrona selecionada pelo cliente
        data = c.recv(1024)

        if not data:
            c.send("AMARELO Linhas -- Obrigado pela preferencia!".encode('ascii'))
            break

        # enviar resposta ao cliente
        if(data not in poltronas_reservadas):
            poltronas_reservadas.append(data)
            c.send("Poltrona reservada com sucesso!".encode('ascii'))
        else:
            c.send("Essa poltrona ja foi reservada. Selecione outra.".encode('ascii'))

    # conexao encerrada
    c.close()


def Main():
    host = ""
    port = 12345

    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.bind((host, port))
    print("Socket criado na porta: ", port)

    s.listen(5)
    print("socket is listening")

    # loop ate o cliente sair
    while True:

        # establish connection with client
        c, addr = s.accept()

        print('Cliente conectado em: ', addr[0], ':', addr[1])

        # inicio de uma thread com novo cliente
        start_new_thread(threaded, (c,))

    # encerramento do socket e programa
    s.close()


if __name__ == '__main__':
    Main()
