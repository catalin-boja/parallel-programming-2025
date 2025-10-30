package ro.ase.ie.paralel;

import java.util.Random;

public class Consumator extends Thread{
	
	String nume;
	Piata piata;
	
	public Consumator(String nume, Piata piata) {
		super();
		this.nume = nume;
		this.piata = piata;
	}

	@Override
	public void run() {
		while(true) {
			
			if(this.piata.esteInchisa()) {
				return;
			}
			
			Random random = new Random();
			int valoare = random.nextInt(200);
			System.out.println("Consumator " + this.nume + " <<<< " + valoare);
			try {
				this.piata.scadeStoc(valoare);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
}



