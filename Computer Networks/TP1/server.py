'''
TP1 LRSO - Sockets / Threads

Matheus Teixeira Alves
Rafael de Paula Maia
'''

# SERVIDOR

import socket
from _thread import start_new_thread

poltronas_reservadas = []


def threaded(c):
    while True:

        # poltrona selecionada pelo cliente
        poltrona = c.recv(1024)

        if not poltrona:
            c.send("AMARELO Linhas -- Obrigado pela preferencia!".encode('ascii'))
            break

        # enviar resposta ao cliente
        if(poltrona not in poltronas_reservadas):
            poltronas_reservadas.append(poltrona)
            c.send("Poltrona reservada com sucesso!".encode('ascii'))
        else:
            c.send("Essa poltrona ja foi reservada. Selecione outra.".encode('ascii'))

    # conexao encerrada
    print("Encerrando Thread e Conexao com cliente.")
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
        print("Criando uma thread com novo cliente...")
        start_new_thread(threaded, (c,))

    # encerramento do socket e programa
    s.close()


if __name__ == '__main__':
    Main()
