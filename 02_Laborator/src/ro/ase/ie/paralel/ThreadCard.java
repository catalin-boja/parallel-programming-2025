package ro.ase.ie.paralel;

import java.util.Random;

public class ThreadCard extends Thread{
	
	ContBancar cont;
	String nume;
	
	public ThreadCard(ContBancar cont, String nume) {
		super();
		this.cont = cont;
		this.nume = nume;
	}

	@Override
	public void run() {
		while(true) {
			//cu == exista risc de livelock
			if(this.cont.getSold() <= 0) {
				break;
			}
			Random random = new Random();
			double suma = random.nextInt(100);
			System.out.println(
					this.nume + " doreste plata cu valoarea " 
					+ suma);
			
			this.cont.plata(suma);
		}
		System.out.println("Thread terminat");
	}
	
	
	
	
}
