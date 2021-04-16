// Tarefa 22 GRUPO 4 - Computação Paralela

/*
  Sequencial:
  real    1m29.986s
  user    1m12.718s
  sys     0m0.104s


  Paralelo com CPU:
  real    0m20.882s
  user    1m14.579s
  sys     0m0.168s


  Paralelo GPU com Distribute:
  real    1m50.092s
  user    1m26.104s
  sys     0m23.898s

  Invocations                                Event Name         Min         Max         Avg       Total
  Device "GeForce GT 1030 (0)"
      Kernel: mm$_omp_fn$0
            1                            warps_launched     1269720     1269720     1269720     1269720

  ==13327== Metric result:
  Invocations                               Metric Name                        Metric Description         Min         Max         Avg
  Device "GeForce GT 1030 (0)"
      Kernel: mm$_omp_fn$0
            1                 warp_execution_efficiency                 Warp Execution Efficiency     100.00%     100.00%     100.00%


  Paralelo GPU com Distribute Parallel For:
  real    0m45.140s
  user    0m12.951s
  sys     0m3.578s

  Invocations                                Event Name         Min         Max         Avg       Total
  Device "GeForce GT 1030 (0)"
      Kernel: mm$_omp_fn$0
            1                            warps_launched       54720       54720       54720       54720

  ==13570== Metric result:
  Invocations                               Metric Name                        Metric Description         Min         Max         Avg
  Device "GeForce GT 1030 (0)"
      Kernel: mm$_omp_fn$0
            1                 warp_execution_efficiency                 Warp Execution Efficiency     100.00%     100.00%     100.00%


  Paralelo GPU com SIMD:
  real    0m9.602s
  user    0m7.188s
  sys     0m2.252s

  Invocations                                Event Name         Min         Max         Avg       Total
  Device "GeForce GT 1030 (0)"
      Kernel: mm$_omp_fn$0
            1                            warps_launched          72          72
  72          72

  ==13625== Metric result:
  Invocations                               Metric Name                        Metric Description         Min         Max         Avg
  Device "GeForce GT 1030 (0)"
      Kernel: mm$_omp_fn$0
            1                 warp_execution_efficiency                 Warp Execution Efficiency      86.81%      86.81%      86.81%

*/

#include <stdio.h>
#include <stdlib.h>

void mm(double* a, double* b, double* c, int width) 
{
#pragma omp target map(to:a[0:width*width], b[0:width*width]) map(from:c[0:width*width])
#pragma omp teams distribute parallel for simd
  for (int i = 0; i < width; i++) {
    for (int j = 0; j < width; j++) {
      double sum = 0;
      for (int k = 0; k < width; k++) {
	double x = a[i * width + k];
	double y = b[k * width + j];
	sum += x * y;
      }
      c[i * width + j] = sum;
    }
  }
}

int main()
{
  int width = 2000;
  double *a = (double*) malloc (width * width * sizeof(double));
  double *b = (double*) malloc (width * width * sizeof(double));
  double *c = (double*) malloc (width * width * sizeof(double));

#pragma omp parallel for
  for(int i = 0; i < width; i++) {
    for(int j = 0; j < width; j++) {
      a[i*width+j] = i;
      b[i*width+j] = j;
      c[i*width+j] = 0;
    }
  }

  mm(a,b,c,width);

  /*for(int i = 0; i < width; i++) {
    for(int j = 0; j < width; j++) {
      printf("%f\n",c[i*width+j]);
    }
  }*/

}
