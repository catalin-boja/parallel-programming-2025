#include <thread>
#include <mutex>
#include <omp.h>
#include <iostream>
#include <vector>

using namespace std;

//metoda care determina numarul de 
// elemente multiplu de 3 din sir
void numara(int* valori, int nrValori,
	int indexStart, int indexFinal, int& contor) {
	for (int i = indexStart; i <= indexFinal; i++) {
		float temp = valori[i] / 5 + valori[i] / 7 + 1;
		float temp2 = valori[i] / 5 + valori[i] / 7 + 1;

		if (valori[i] % 3 == 0)
			contor += 1;
	}
}

mutex semaforGlobal;

void numaraSync(int* valori, int nrValori,
	int indexStart, int indexFinal, int& contor) {
	for (int i = indexStart; i <= indexFinal; i++) {
		float temp = valori[i] / 5 + valori[i] / 7 + 1;
		float temp2 = valori[i] / 5 + valori[i] / 7 + 1;

		if (valori[i] % 3 == 0)
		{
			semaforGlobal.lock();
			contor += 1;
			semaforGlobal.unlock();
		}
	}
}


int testSecvential(int* valori, int nrValori) {
	int contor = 0;
	numara(valori, nrValori, 0, nrValori - 1, contor);
	return contor;
}

//test Paralel cu Race Condition
int testParalel(int* valori, int nrValori) {
	int contor = 0;
	int nrFire = 8;
	vector<thread> fire;
	int dimensiuneInterval = nrValori / nrFire;

	for (int i = 0; i < nrFire; i++) {
		int indexStart = i * dimensiuneInterval;
		int indexFinal = (i + 1) * dimensiuneInterval - 1;
		if (i == nrFire - 1)
			indexFinal = nrValori - 1;
		fire.push_back(
			thread(numara, valori, nrValori, 
				indexStart, indexFinal, ref(contor)));
	}
	for (int i = 0; i < nrFire; i++) {
		fire[i].join();
	}
	return contor;
}

//test Paralel fara Race Condition
int testParalelSync(int* valori, int nrValori) {
	int contor = 0;
	int nrFire = 8;
	vector<thread> fire;
	int dimensiuneInterval = nrValori / nrFire;

	for (int i = 0; i < nrFire; i++) {
		int indexStart = i * dimensiuneInterval;
		int indexFinal = (i + 1) * dimensiuneInterval - 1;
		if (i == nrFire - 1)
			indexFinal = nrValori - 1;
		fire.push_back(
			thread(numaraSync, valori, nrValori,
				indexStart, indexFinal, ref(contor)));
	}
	for (int i = 0; i < nrFire; i++) {
		fire[i].join();
	}
	return contor;
}


//test Paralel cu variabile contor diferite
int testParalelVariabile(int* valori, int nrValori) {

	const int nrFire = 8;
	int contoare[nrFire];

	memset(contoare, 0, nrFire * sizeof(int));

	vector<thread> fire;
	int dimensiuneInterval = nrValori / nrFire;

	for (int i = 0; i < nrFire; i++) {
		int indexStart = i * dimensiuneInterval;
		int indexFinal = (i + 1) * dimensiuneInterval - 1;
		if (i == nrFire - 1)
			indexFinal = nrValori - 1;
		fire.push_back(
			thread(numara, valori, nrValori,
				indexStart, indexFinal, ref(contoare[i])));
	}
	for (int i = 0; i < nrFire; i++) {
		fire[i].join();
	}

	int rezultat = 0;
	for (int i = 0; i < nrFire; i++) {
		rezultat += contoare[i];
	}

	return rezultat;

}

//test Paralel cu variabile contor care nu 
// se gasesc pe aceeasi linie de cache
int testParalelVariabileDiferite(int* valori, int nrValori) {

	const int nrFire = 8;
	int contoare[nrFire * 1024];

	alignas(1024) int vb;

	memset(contoare, 0, nrFire * sizeof(int) * 1024);

	vector<thread> fire;
	int dimensiuneInterval = nrValori / nrFire;

	for (int i = 0; i < nrFire; i++) {
		int indexStart = i * dimensiuneInterval;
		int indexFinal = (i + 1) * dimensiuneInterval - 1;
		if (i == nrFire - 1)
			indexFinal = nrValori - 1;
		fire.push_back(
			thread(numara, valori, nrValori,
				indexStart, indexFinal, ref(contoare[i*1024])));
	}
	for (int i = 0; i < nrFire; i++) {
		fire[i].join();
	}

	int rezultat = 0;
	for (int i = 0; i < nrFire; i++) {
		rezultat += contoare[i*1024];
	}

	return rezultat;

}

void benchmark(string mesaj, int* valori,
	int nrValori, int (*pf)(int*, int)) {

	cout << endl << mesaj;
	double tStart = omp_get_wtime();
	int rezultat = pf(valori, nrValori);
	double tFinal = omp_get_wtime();

	printf("Rezultat %d obtinut in %f secunde",
		rezultat, (tFinal - tStart));
}


int main() {
	const int N = 1e9;
	int* valori = new int[N];
	for (int i = 0; i < N; i++)
		valori[i] = i;

	benchmark("Test solutie secventiala",
		valori, N, testSecvential);
	benchmark("Test solutie paralela cu Race Condition",
		valori, N, testParalel);
	//benchmark("Test solutie paralela cu Mutex",
	//	valori, N, testParalelSync);
	benchmark("Test solutie paralela cu variabile in linii de cache diferite",
		valori, N, testParalelVariabileDiferite);
	benchmark("Test solutie paralela cu variabile diferite",
		valori, N, testParalelVariabile);


}