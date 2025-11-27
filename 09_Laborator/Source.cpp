#include <thread>
#include <stdio.h>
#include <mutex>

void hello() {
	printf("\n Hello !");
}

void helloCuId(int id) {
	printf("\n Hello de pe thread %d!", id);
}

//int suma(int a, int b) {
//	return a + b;
//}

//definire mutex global
std::mutex semaforGlobal;

void suma(int a, int b, int& rezultat) {
	semaforGlobal.lock();
	rezultat = a + b;
	semaforGlobal.unlock();
}

void incrementContor(int& contor, std::mutex& semafor) {
	semafor.lock();
	contor += 1;
	semafor.unlock();
}

int main() {

	hello();

	//std::thread t1(hello);
	//std::thread t2(hello);

	int rezultatSuma = 0;
	std::mutex semaforLocal;
	int contor = 0;

	std::thread t1(helloCuId, 1);
	std::thread t2(helloCuId, 2);
	std::thread tSuma(suma, 2, 3, std::ref(rezultatSuma));

	std::thread tc1(incrementContor,
		std::ref(contor), std::ref(semaforLocal));
	std::thread tc2(incrementContor,
		std::ref(contor), std::ref(semaforLocal));

	t1.join();
	t2.join();
	tSuma.join();

	tc1.join();
	tc2.join();

	printf("\n Rezultatul este %d", rezultatSuma);
	printf("\n Contorul este %d", contor);

	printf("\n Sfarsit");

}
