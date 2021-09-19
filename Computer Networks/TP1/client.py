'''
TP1 LRSO - Sockets / Threads

Matheus Teixeira Alves
Rafael de Paula Maia
'''

import socket


def input_poltrona():
    poltrona = 0
    while(int(poltrona) not in range(1, 11)):
        poltrona = input("Selecione sua poltrona (1-10): ")

    return poltrona


def Main():
    host = '127.0.0.1'
    port = 12345

    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    # conectando no servidor
    s.connect((host, port))

    print("\nBem vindo a AMARELO Linhas Aereas!\n")
    print("=== Venda de passagens do voo privado 19/09/2021 BH -> SP  ===\n")

    while True:

        # poltrona a ser enviada para o servidor
        poltrona = input_poltrona()

        # enviando poltrona ao servidor
        s.send(poltrona.encode('ascii'))

        # resposta recebida do servidor
        data = s.recv(1024)
        print(str(data.decode('ascii')))

        # pergunta se o cliente quer continuar
        ans = input('\nDeseja continuar? (s/n):')
        if ans == 's':
            continue
        else:
            break

    # encerrar conexao
    s.close()


if __name__ == '__main__':
    Main()
