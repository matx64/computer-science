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