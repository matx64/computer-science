#include <stdio.h>
#include <omp.h>

static long num_steps = 100000000;
double step;

#define NUM_THREADS 12
#define PAD 8

int main ()
{
    int i, shared_num_threads;
    double pi, sum[NUM_THREADS][PAD];
    
    double start_time, run_time;

    step = 1.0/(double) num_steps;

    start_time = omp_get_wtime();

    omp_set_num_threads(NUM_THREADS);
    #pragma omp parallel
    {
        int i, id, real_num_threads;
        double x;

        id = omp_get_thread_num();
        real_num_threads = omp_get_num_threads();
        if(id == 0) shared_num_threads = real_num_threads;

        for(i = id, sum[id][0] = 0.0; i < num_steps; i += real_num_threads){
            x = (i-0.5)*step;
            sum[id][0] += 4.0/(1.0+x*x);
        }
    }

    for(i = 0, pi = 0.0; i < shared_num_threads; i++){
        pi += sum[i][0] * step;
    }


    run_time = omp_get_wtime() - start_time;
    printf("\n pi with %ld steps is %lf in %lf seconds\n ",num_steps,pi,run_time);
}	  


