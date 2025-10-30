package ro.ase.ie.paralel;

import java.util.Random;

public class JocBingo extends Thread{

	volatile int numarExtras;
	int numarExtrageri;
	
	public JocBingo(int numarExtrageri) {
		super();
		this.numarExtrageri = numarExtrageri;
	}
	
	@Override
	public void run() {
		System.out.println("Jocul incepe !");
		Random random = new Random();
		for(int i = 0; i < this.numarExtrageri; i++) {
			//int numar = random.nextInt(100);
			//this.numarExtras = numar;
			this.numarExtras = i+1;
			System.out.println("*** A fost extras " + numarExtras);
		}
		System.out.println("********** Joc terminat ***********");
	}

	public int getNumarExtras() {
		return this.numarExtras;
	}
	
}
