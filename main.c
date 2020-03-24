#include <stdlib.h>
#include <stdio.h>
#include <pthread.h>
#include <unistd.h>
#include <math.h>

pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;
double glob = 0;
double frob = 0;

/* Argument funkcji wątku (multiplicationThread) */
typedef struct threadMulArg {
    double **A, **B, **C;
    int threadsNum, rows, cols;
} threadMulArg_t;

/* Funkcja przekazywana do wątku */
void* multiplicationThread(void *arg) {
    threadMulArg_t *threadMulArg = (threadMulArg_t *)arg;
    int i; 
    int ind1 = threadMulArg->rows;
    int ind2 = threadMulArg->cols;
    double sum = 0;
    for (i=0; i<threadMulArg->threadsNum; ++i){
        sum += threadMulArg->A[ind1][i] * threadMulArg->B[i][ind2];
    }

    threadMulArg->C[ind1][ind2] = sum;
    pthread_mutex_lock(&mutex);
    glob += sum;
    frob += pow(sum, 2);
    pthread_mutex_unlock(&mutex);
    pthread_exit(NULL);
}

/* Procedura wypisująca macierz */
void print_matrix(double**A, int m, int n) {
    int i, j;
    printf("[");
    for (i=0; i<m; ++i) {
        for (j=0; j<n; ++j) printf("%f ", A[i][j]);
        printf("\n");
    }
    printf("]\n");
}


int main(int argc, char *argv[]) {  
    /* Wczytanie argumentów */
    FILE *fpa, *fpb;
    double **A, **B, **C;
    int ma, na, mb, nb, i, j;
    double x;

    fpa = argc > 1 ? fopen(argv[2], "r") : fopen("A.txt", "r");
    fpb = argc > 2 ? fopen(argv[3], "r") : fopen("B.txt", "r");

    if( fpa == NULL || fpb == NULL ) {
        perror("Błąd otwarcia pliku\n");
        exit(EXIT_FAILURE);
    }

    fscanf(fpa, "%d", &ma);
    fscanf (fpa, "%d", &na);

    fscanf (fpb, "%d", &mb);
    fscanf (fpb, "%d", &nb);

    printf("Pierwsza macierz ma wymiar %d x %d, a druga %d x %d\n", ma, na, mb, nb);

    if (na != mb) {
        printf("Złe wymiary macierzy!\n");
        exit(EXIT_FAILURE);
    }
    
    /* Alokacja pamięci */
    A = malloc(ma*sizeof(double));
    for (i=0; i<ma; ++i) {
        A[i] = malloc(na*sizeof(double));
    }

    B = malloc(mb*sizeof(double));
    for (i=0; i<mb; ++i) {
        B[i] = malloc(nb*sizeof(double));
    }

    int mc = ma;
    int nc = nb;
    C = malloc(mc*sizeof(double));
    for (i=0; i<ma; ++i) {
        C[i] = malloc(nc*sizeof(double));
    }

    /* Inicjalizacja danych */
    for (i=0; i<ma; ++i) {
        for (j=0; j<na; ++j) {
            fscanf(fpa, "%lf", &x);
            A[i][j] = x;
        }
    }
    printf("A:\n");
    print_matrix(A, ma, na);


    for (i=0; i<mb; ++i)
        for (j=0; j<nb; ++j) {
            fscanf(fpb, "%lf", &x);
            B[i][j] = x;
        }
    printf("B:\n");
    print_matrix(B, mb, nb);

    /* Tworzenie wątków */
    pthread_t mythread[mc][nc];
    threadMulArg_t threadMulArg[mc][nc];

    for (i=0; i<mc; ++i)
        for (j=0; j<nc; ++j) {
            threadMulArg[i][j].A = A;
            threadMulArg[i][j].B = B;
            threadMulArg[i][j].C = C;
            threadMulArg[i][j].threadsNum = na;
            threadMulArg[i][j].rows = i;
            threadMulArg[i][j].cols = j;
            if (pthread_create(&(mythread[i][j]), NULL, multiplicationThread, &(threadMulArg[i][j]))) {
                printf("Błąd podczas tworzenia wątków\n");
                exit(EXIT_FAILURE);
            }
            
        }

    /* Oczekiwanie na zakończenie wątków i połączenie wyników */
    for (i=0; i<mc; ++i) {
        for (j=0; j<nc; ++j) {
            if (pthread_join(mythread[i][j], NULL)) {
                printf("Błąd podczas łączenia wątków\n");
                exit(EXIT_FAILURE);
            }
        }
    }

    /* Wypisanie wyników */
    printf("Macierz wynikowa C:\n");
    print_matrix(C, mc, nc);
    printf("Suma elementów macierzy wynikowej wynosi: %f\n", glob);
    printf("Norma Frobeniusa dla macierzy wynikowej wynosi: %f\n", sqrt(frob));
    
    /* Zwolnienie pamięci */
    for (i=0; i<ma; ++i) 
        free(A[i]);
    free(A);
    
    for (i=0; i<mb; ++i) 
        free(B[i]);
    free(B);

    for (i=0; i<ma; ++i) 
        free(C[i]);
    free(C);

    exit(EXIT_SUCCESS);
}
