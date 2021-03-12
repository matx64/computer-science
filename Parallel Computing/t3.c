/* Adapted from: http://w...content-available-to-author-only...s.org/sieve-of-eratosthenes */

/* 
    GRUPO 4 - Computação Paralela
    Nomes:  Matheus Teixeira alves
            Marcos Tadeu
            Rafael Augusto
*/

/* 
    Tempo Sequencial: 
        real    0m1.363s
        user    0m1.313s
        sys     0m0.047s

    Tempo Paralelo:
        real    0m1.078s
        user    0m3.672s
        sys     0m0.063s
*/

#include <omp.h>     
#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>
#include <math.h>
    
int sieveOfEratosthenes(int n) {
// Create a boolean array "prime[0..n]" and initialize
// all entries it as true. A value in prime[i] will
// finally be false if i is Not a prime, else true.
    int primes = 0; 
    bool *prime = (bool*) malloc((n+1)*sizeof(bool));
    int sqrt_n = sqrt(n);
	
    memset(prime, true,(n+1)*sizeof(bool));

	#pragma omp parallel for
	for (int p=2; p <= sqrt_n; p++)
	{
       	if (prime[p] == true) 
			#pragma omp parallel for
			for (int i=p*2; i<=n; i += p)
				prime[i] = false;
	}
     
    // count prime numbers
    for (int p=2; p<=n; p++)
       if (prime[p])
           primes++;
     
    return(primes);
}
     
int main() {
       int n = 100000000;
       printf("%d\n",sieveOfEratosthenes(n));
       return 0;
} 