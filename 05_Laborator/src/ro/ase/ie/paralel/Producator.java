package ro.ase.ie.paralel;

import java.util.Random;

public class Producator extends Thread {
	
	Piata piata;
	String nume;
	
	public Producator(Piata piata, String nume) {
		super();
		this.piata = piata;
		this.nume = nume;
	}

	@Override
	public void run() {
		while(true) {
			
			if(this.piata.esteInchisa()) {
				return;
			}
			
			Random random = new Random();
			int valoare = random.nextInt(200);
			System.out.println("Producator " + this.nume + " >>>> " + valoare);
				try {
					this.piata.adaugaStoc(valoare);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
	}

}




