#include <omp.h>
#include <string>

using namespace std;

int sumaElementeSecvential(int* valori, int nrElemente) {
	int suma = 0;
	for (int i = 0; i < nrElemente; i++) {
		suma += valori[i];
	}
	return suma;
}

int sumaElementeParalel(int* valori, int nrElemente) {
	int suma = 0;
	const int nrThreaduri = 4;
	int sume[nrThreaduri];

#pragma omp parallel num_threads(nrThreaduri) firstprivate(suma)
	{
#pragma omp for
		for (int i = 0; i < nrElemente; i++) {
			suma += valori[i];
		}
		int idThread = omp_get_thread_num();
		sume[idThread] = suma;
	}
	for (int i = 0; i < nrThreaduri; i++) {
		suma += sume[i];
	}
	return suma;
}


int sumaElementeCritical(int* valori, int nrElemente) {
	int suma = 0;
	int sumaFinala = 0;

#pragma omp parallel num_threads(4) firstprivate(suma)
	{
#pragma omp for
		for (int i = 0; i < nrElemente; i++) {
			suma += valori[i];
		}
#pragma omp critical
		{
			sumaFinala += suma;
		}
	}
	return sumaFinala;
}

int sumaElementeParalelReduction(int* valori, int nrElemente) {
	int suma = 0;
#pragma omp parallel for num_threads(4) reduction(+: suma)
	for (int i = 0; i < nrElemente; i++) {
		suma += valori[i];
	}
	return suma;
}

bool estePrim(int valoare) {
	for (int i = 2; i < valoare / 2 + 1; i++) {
		if (valoare % i == 0)
			return false;
	}
	return true;
}

int testNrPrimeSecvential(int limita) {
	int contor = 0;
	for (int i = 1; i < limita; i++) {
		if (estePrim(i))
			contor += 1;
	}
	return contor;
}

int testNrPrimeParalel(int limita) {
	int contor = 0;
#pragma omp parallel for \
	schedule(dynamic, 10000) reduction(+: contor)
	for (int i = 1; i < limita; i++) {
		if (estePrim(i))
			contor += 1;
	}
	return contor;
}


int main() {
	const int N = 1e5;
	int valori[N];
	for (int i = 0; i < N; i++) {
		valori[i] = N - 1;
	}

	printf("\n Suma calculata secvential: %d",
		sumaElementeSecvential(valori, N));
	printf("\n Suma calculata paralel: %d",
		sumaElementeParalel(valori, N));
	printf("\n Suma calculata paralel: %d",
		sumaElementeParalelReduction(valori, N));
	printf("\n Suma calculata paralel: %d",
		sumaElementeCritical(valori, N));

	//test for nowait
#pragma omp parallel num_threads(4)
	{

		//if (omp_get_thread_num() == 0) {
		//	printf("\n Hello de pe main thread");
		//}



#pragma omp for nowait
		for (int i = 0; i < 10; i++) {
			printf("\nIteratie %d executata de thread %d",
				i, omp_get_thread_num());
		}
		printf("\n Thread %d a terminat", omp_get_thread_num());

#pragma omp single
		{
			printf("\n Hello de pe thread %d", omp_get_thread_num());
		}

#pragma omp master
		{
			printf("\n Hello de pe main thread");
		}

#pragma omp sections
		{
#pragma omp section
			{
				printf("\nSectiune 1 executata de thread %d",
					omp_get_thread_num());
			}
#pragma omp section
			{
				printf("\nSectiune 2 executata de thread %d",
					omp_get_thread_num());
			}
		}
	}

	//test prime
	const int limitaSup = (int)5e5;
	double tStart = omp_get_wtime();
	int nrPrime = testNrPrimeSecvential(limitaSup);
	double tFinal = omp_get_wtime();
	printf("\n Nr prime: %d in %f secunde",
		nrPrime, tFinal - tStart);

	tStart = omp_get_wtime();
	nrPrime = testNrPrimeParalel(limitaSup);
	tFinal = omp_get_wtime();
	printf("\n Nr prime: %d in %f secunde",
		nrPrime, tFinal - tStart);

}

