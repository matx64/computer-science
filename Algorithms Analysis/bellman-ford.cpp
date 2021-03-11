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