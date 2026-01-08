#include <iostream>
#include <omp.h>
using namespace std;

void bubbleSort(int* valori, int n) {
	bool esteSortat = false;
	while (!esteSortat) {
		esteSortat = true;
		for (int i = 0; i < n - 1; i++) {
			if (valori[i] > valori[i + 1])
			{
				int temp = valori[i];
				valori[i] = valori[i + 1];
				valori[i + 1] = temp;
				esteSortat = false;
			}
		}
	}
}

void oddEvenSort(int* valori, int n) {
	for (int i = 0; i < n; i++) {
		if (i % 2 == 0) {
			for (int j = 0; j < n-1; j += 2) {
				if (valori[j] > valori[j + 1]) {
					int temp = valori[j];
					valori[j] = valori[j + 1];
					valori[j + 1] = temp;
				}
			}
		}
		else {
			for (int j = 1; j < n - 1; j += 2) {
				if (valori[j] > valori[j + 1]) {
					int temp = valori[j];
					valori[j] = valori[j + 1];
					valori[j + 1] = temp;
				}
			}
		}
	}
}

void bubbleSortParalel(int* valori, int n) {
	bool esteSortat = false;
	while (!esteSortat) {
		esteSortat = true;
#pragma omp parallel for schedule(static, 2)
		for (int i = 0; i < n - 1; i++) {
			if (valori[i] > valori[i + 1])
			{
				int temp = valori[i];
				valori[i] = valori[i + 1];
				valori[i + 1] = temp;
				esteSortat = false;
			}
		}
	}
}

void afisare(int* valori, int n, int m) {
	printf("\n Valori: ");
	for (int i = 0; i < m; i++) {
		printf(" %d", valori[i]);
	}
}

void oddEvenSortParalel(int* valori, int n) {
	for (int i = 0; i < n; i++) {
		if (i % 2 == 0) {
#pragma omp parallel for
			for (int j = 0; j < n - 1; j += 2) {
				if (valori[j] > valori[j + 1]) {
					int temp = valori[j];
					valori[j] = valori[j + 1];
					valori[j + 1] = temp;
				}
			}
		}
		else {
#pragma omp parallel for
			for (int j = 1; j < n - 1; j += 2) {
				if (valori[j] > valori[j + 1]) {
					int temp = valori[j];
					valori[j] = valori[j + 1];
					valori[j + 1] = temp;
				}
			}
		}
	}
}

int main() {
	const int N = 1e6;
	int* valori = new int[N];
	for (int i = 0; i < N; i++) {
		valori[i] = N - i;
	}

	double tStart = omp_get_wtime();
	oddEvenSortParalel(valori, N);
	double tFinal = omp_get_wtime();

	printf("\n Sortare in %f secunde", tFinal - tStart);
	afisare(valori, N, 1000);

}