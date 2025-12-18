#include "sha1.h"
#include <omp.h>
#include <stdio.h>
#include <string>
#include <fstream>

using namespace std;

int main() {


	string hashDeGasit = "00c3cc7c9d684decd98721deaa0729d73faa9d9b";

	string pass = "1234";
	string hash = sha1(sha1("parallel" + pass));
	printf("\nHash: %s", hash.c_str());


	ifstream passFile("password-list.txt");
	if (passFile.is_open()) {
		//while (!passFile.eof()) {
		//	string password;
		//	passFile >> password;

		//	printf("\n%s", password.c_str());
		//}
		passFile.close();
	}
	else {
		printf("\n ********** File Error ************");
	}


}
