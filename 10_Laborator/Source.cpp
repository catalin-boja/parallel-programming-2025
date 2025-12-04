#include <omp.h>
#include <stdio.h>

using namespace std;

int main() {

	printf("\nNumar core-uri disponibile: %d", omp_get_num_procs());
	printf("\nNumar thread-uri active: %d", omp_get_num_threads());

	if (omp_in_parallel()) {
		printf("Secventa care se executa in paralel");
	}

	printf("\nHello de pe main thread");
	printf("\n Id main thread = %d", omp_get_thread_num());

	omp_set_num_threads(4);

#pragma omp parallel
	{
		printf("\nHello de pe un alt thread cu id = %d",
			omp_get_thread_num());
		//secventa de executat 1 data
		if (omp_get_thread_num() == 0) {
			printf("\nNumar thread-uri active: %d", omp_get_num_threads());
			
			printf("\nBye !");

			if (omp_in_parallel()) {
				printf("\nSecventa care se executa in paralel");
			}

		}
	}

	//test race condition in omp
	int contor = 0;
	omp_set_num_threads(1000);

	omp_lock_t mutex;
	omp_init_lock(&mutex);

#pragma omp parallel
	{
		omp_set_lock(&mutex);
		contor += 1;
		omp_unset_lock(&mutex);
	}

	printf("\n Valoare contor: %d", contor);

	//executie paralela conditionata
	int nrProcesoare = omp_get_num_procs();

#pragma omp parallel if(nrProcesoare > 4) num_threads(10)
	{
		printf("\nExecutie paralela pe mai mult de 4 core-uri");
	}

	int contorShared = 0;
	int contorPrivate = 100;
	int contorFirstPrivate = 1000;

#pragma omp parallel num_threads(10) shared(contorShared) \
	private(contorPrivate) firstprivate(contorFirstPrivate)
	{
		for (int i = 0; i < 10; i++) {
			contorShared += 1;
			contorPrivate += 1;
			contorFirstPrivate += 1;
		}
		printf("\n Contor shared in thread %d = %d",
			omp_get_thread_num(), contorShared);
		printf("\n Contor private in thread %d = %d",
			omp_get_thread_num(), contorPrivate);
		printf("\n Contor first private in thread %d = %d",
			omp_get_thread_num(), contorFirstPrivate);
	}

	printf("\n -----------------");
	printf("\n Contor shared in thread %d = %d",
		omp_get_thread_num(), contorShared);
	printf("\n Contor private in thread %d = %d",
		omp_get_thread_num(), contorPrivate);
	printf("\n Contor first private in thread %d = %d",
		omp_get_thread_num(), contorFirstPrivate);

	//suma elemente vector
	const int N = 10000;
	int valori[N];
	for (int i = 0; i < N; i++) {
		valori[i] = i + 1;
	}

	int suma = 0;

#pragma omp parallel num_threads(4)
	{
		int nrThreaduri = omp_get_num_threads();
		int dimInterval = N / nrThreaduri;
		int indexStart = omp_get_thread_num() * dimInterval;
		int indexFinal = (omp_get_thread_num() + 1) * dimInterval;
		if (omp_get_thread_num() == nrThreaduri - 1)
			indexFinal = N;

		for (int i = indexStart; i < indexFinal; i++) {
			omp_set_lock(&mutex);
			suma += valori[i];
			omp_unset_lock(&mutex);
		}
	}
	printf("\n Suma este %d",suma);


	printf("\nSfarsit main thread");
}
