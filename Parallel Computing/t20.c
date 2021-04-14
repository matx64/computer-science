/* 
    Computação Paralela - Tarefa 20
    Discente:   Matheus Teixeira Alves
    Matrícula:  636132
*/

/*
    #### Silly Sort  ####

    Sequencial:
    real    0m16.710s
    user    0m16.705s
    sys     0m0.000s

    Pararelo com CPU:
    real    0m7.965s
    user    0m31.776s
    sys     0m0.008s

    Paralelo com GPU:
    real    0m4.130s
    user    0m3.052s
    sys     0m0.996s

    SpeedUp Sequencial vs Paralelo com GPU: 4.04

*/

#include <stdio.h>
#include <stdlib.h>

int main()
{
    int i, j, n = 100000;

    // Allocate input, output and position arrays
    int *in = (int*) calloc(n, sizeof(int));
    int *pos = (int*) calloc(n, sizeof(int));
    int *out = (int*) calloc(n, sizeof(int));

    // Initialize input array in the reverse order
    for(i=0; i < n; i++)
        in[i] = n-i;

    // Print input array
    //   for(i=0; i < n; i++)
    //      printf("%d ",in[i]);

    // Silly sort (you have to make this code parallel)
    #pragma omp target map(tofrom:pos[:n]) map(to:in[:n])
    #pragma omp teams distribute parallel for simd
    for(i=0; i < n; i++)
        for(j=0; j < n; j++)
            if(in[i] > in[j])
                pos[i]++;

    // Move elements to final position
    for(i=0; i < n; i++)
        out[pos[i]] = in[i];

    // print output array
    //   for(i=0; i < n; i++)
    //      printf("%d ",out[i]);

    // Check if answer is correct
    for(i=0; i < n; i++)
        if(i+1 != out[i])
        {
            printf("test failed\n");
            exit(0);
        }

    printf("test passed\n");
}
