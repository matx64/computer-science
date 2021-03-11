/* 
    ====== PAA. Trabalho Prático ======
     
     Nome: Matheus Teixeira Alves
     Matricula: 636132

    ===================================
*/

#include <stdio.h>
#include <iostream>
#include <limits.h>
#include <vector>
#include <algorithm>

using namespace std;

// Estrutura Aresta do Grafo
struct Aresta
{
    int origem, dest, peso;
};

// Estrutura Grafo
struct Grafo
{
    int V, A;

    struct Aresta *aresta;
};

// Função para criar Grafo
struct Grafo *criarGrafo(int V, int A)
{
    struct Grafo *grafo = new Grafo;
    grafo->V = V;
    grafo->A = A;
    grafo->aresta = new Aresta[A];
    return grafo;
}

// Algoritmo de Dijkstra
void dijkstra()
{
    int tam, inicio;
    scanf("%d %d\n", &tam, &inicio);

    int grafo[tam][tam], distancia[tam], anterior[tam],
        visitados[tam], count, distanciaMinima, proximo;

    // Leitura do Grafo
    for (int i = 0; i < tam; i++)
    {
        for (int j = 0; j < tam; j++)
        {
            scanf("%d ", &grafo[i][j]);
        }
        scanf("\n");
    }

    // Inicia Arestas inexistentes como um valor Maximo
    for (int i = 0; i < tam; i++)
    {
        for (int j = 0; j < tam; j++)
        {
            if (i != j && grafo[i][j] == 0)
                grafo[i][j] = 99999999;
        }
    }

    // Inicia array de distancias a partir da origem com os valores de sua coluna
    // Inicia array de anteriores como inicio
    for (int i = 0; i < tam; i++)
    {
        distancia[i] = grafo[inicio][i];
        anterior[i] = inicio;
        visitados[i] = 0;
    }

    distancia[inicio] = 0;
    visitados[inicio] = 1;
    count = 1;

    while (count < tam - 1)
    {
        distanciaMinima = 99999999;

        // Procura a distancia minima de algum vertice com o vertice de inicio
        for (int i = 0; i < tam; i++)
        {
            if (distancia[i] < distanciaMinima && !visitados[i])
            {
                distanciaMinima = distancia[i];
                proximo = i;
            }
        }

        // Visita o vertice em que a menor distancia foi encontrada
        visitados[proximo] = 1;

        for (int i = 0; i < tam; i++)
        {
            if (!visitados[i])
            {
                if (distanciaMinima + grafo[proximo][i] < distancia[i])
                {
                    distancia[i] = distanciaMinima + grafo[proximo][i];
                    anterior[i] = proximo;
                }
            }
        }
        count++;
    }

    // Output do Algoritmo com todo o caminho percorrido
    for (int i = 0; i < tam; i++)
    {
        if (i != inicio)
        {
            cout << "\nDistancia do vértice " << i << ": " << distancia[i];
            cout << "\nCaminho: " << i;
            int j = i;
            do
            {
                j = anterior[j];
                cout << "<-" << j;
            } while (j != inicio);
        }
    }
}

void BellmanFord(struct Grafo *grafo, int origem)
{
    int V = grafo->V;
    int A = grafo->A;
    int dist[V];

    // Passo 1: Inicia as distancias da origem ate os outros vertices como infinito
    for (int i = 0; i < V; i++)
        dist[i] = INT_MAX;
    dist[origem] = 0;

    /* 
        Passo 2: Encontra o menor caminho para todos os vertices,
                do vertices origem.
    */
    for (int i = 1; i <= V - 1; i++)
    {
        for (int j = 0; j < A; j++)
        {
            int u = grafo->aresta[j].origem;
            int v = grafo->aresta[j].dest;
            int peso = grafo->aresta[j].peso;
            if (dist[u] != INT_MAX && dist[u] + peso < dist[v])
                dist[v] = dist[u] + peso;
        }
    }

    /* 
        Passo 3: Verifica se existem ciclos negativos. 
        O passo acima garante a menor distancia se o grafo nao contem ciclo negativo
        Se obtivermos um caminho ainda menor, quer dizer que existe ciclo negativo.
    */
    for (int i = 0; i < A; i++)
    {
        int u = grafo->aresta[i].origem;
        int v = grafo->aresta[i].dest;
        int peso = grafo->aresta[i].peso;
        if (dist[u] != INT_MAX && dist[u] + peso < dist[v])
        {
            printf("O grafo contem ciclo negativo.\n");
            return;
        }
    }

    printf("Distancias da Origem:\n");
    for (int i = 0; i < V; ++i)
        printf("%d \t\t %d\n", i, dist[i]);

    return;
}

int main()
{
    // Menu de Opções do programa
    int opcao = -1;
    while (opcao != 0)
    {
        cout << "\n\nSelecione uma Opção de Entrada: \n"
             << endl
             << "1. Entrada de Grafo para executar o Algoritmo de Dijkstra" << endl
             << "2. Entrada de Grafo para verificar ciclos negativos e calcular menor distância caso possível (Bellman-Ford)"
             << endl
             << "0. Sair\n"
             << endl;

        scanf("%d\n", &opcao);
        switch (opcao)
        {
        case 1:
            dijkstra();
            break;
        case 2:
            int V, A, origem;
            scanf("%d %d %d\n", &V, &A, &origem);

            struct Grafo *grafo = criarGrafo(V, A);

            for (int i = 0; i < A; i++)
            {
                scanf("%d %d %d\n", &grafo->aresta[i].origem, &grafo->aresta[i].dest, &grafo->aresta[i].peso);
            }

            BellmanFord(grafo, origem);
            break;
        }
    }

    return 0;
}