#include <stdio.h>

// Tarefa 19 - Grupo 4

/* 
	Saídas do Compilador: 

	not vectorized: control flow in loop.
    not vectorized: Bad inner loop.
    not vectorized: not suitable for gather load _35 = x[_34];
    not vectorized: complicated access pattern.
    not vectorized: not enough data-refs in basic block.
    not vectorized: failed to find SLP opportunities in basic block.
*/

#define SIZE 666

void faz_nada(float a[66], float b[66], int n, int i){

}

int main() {
    float a[66];
    float b[66];
    float x[66];
    float y[66][66];
    int index[66];
    int n = 50;

    int i;

    for (int i=0; i<SIZE; i+=2) b[i] += a[i] * x[i];
    for (int j=0; j<SIZE; j++) {
      for (int i=0; i<SIZE; i++) b[i] += y[i][j] * x[j];
    }
    for (int i=0; i<SIZE; i+=2) b[i] += a[i] * x[index[i]];

    for(int j = 1; j < n; j *= 2){
        for (i = 0; i < n; i++) {
            a[i] = a[i + 17] + b[i] - n;
            faz_nada(a, b, n, i);
            if(a[(int) i/3] == 6) main();
        }
    }

    return 0;
}