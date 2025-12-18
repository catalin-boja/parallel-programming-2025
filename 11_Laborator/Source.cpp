#include <omp.h>
#include <stdio.h>
#include <string>

using namespace std;

int minimVectorSecvential(int* valori, int noValori) {
	int minim = valori[0];
	for (int i = 0; i < noValori; i++) {
		if (minim > valori[i])
			minim = valori[i];
	}
	return minim;
}

int minimVectorParalel(int* valori, int noValori) {
	int minim = valori[0];
	int minimFinal = valori[0];
	omp_lock_t mutex;
	omp_init_lock(&mutex);

#pragma omp parallel num_threads(4) firstprivate(minim)
	{
		int nrThreaduri = omp_get_num_threads();
		int idThread = omp_get_thread_num();
		int interval = noValori / nrThreaduri;

		int start = idThread * interval;
		int final = (idThread + 1) * interval;
		if (idThread == nrThreaduri - 1)
			final = noValori;

		for (int i = start; i < final; i++) {
			if (minim > valori[i])
				minim = valori[i];
		}

		omp_set_lock(&mutex);
		if (minimFinal > minim) {
			minimFinal = minim;
		}
		omp_unset_lock(&mutex);
	}
	return minimFinal;
}

int minimVectorParalelFor(int* valori, int noValori) {
	int minim = valori[0];
	omp_lock_t mutex;
	omp_init_lock(&mutex);

#pragma omp parallel for num_threads(4)
	for (int i = 0; i < noValori; i++) {
		omp_set_lock(&mutex);
		if (minim > valori[i])
			minim = valori[i];
		omp_unset_lock(&mutex);
	}
	return minim;
}


int main() {

	const int N = 100000;
	int valori[N];
	for (int i = 0; i < N; i++) {
		valori[i] = N - i;
	}

	int minim = minimVectorSecvential(valori, N);
	printf("\nMinim determinat secvential: %d", minim);
	minim = minimVectorParalel(valori, N);
	printf("\nMinim determinat paralel: %d", minim);
	minim = minimVectorParalelFor(valori, N);
	printf("\nMinim determinat paralel - for: %d", minim);


#pragma omp parallel num_threads(4)
	{
		//schedule de tip static este implicit
#pragma omp for schedule(guided, 3)
		for (int i = 0; i < 100; i++) {
			printf("\nIteratie %d executat de thread %d",
				i, omp_get_thread_num());
		}
	}


}