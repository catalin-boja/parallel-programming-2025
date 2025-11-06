package ro.ase.ie.paralel;

import java.util.Random;
import java.util.concurrent.LinkedBlockingDeque;

public class GeneratorMesaje extends Thread{
	
	LinkedBlockingDeque<Mesaj> coadaMesaje;
	int id;
	boolean esteOprit;
	
	public GeneratorMesaje(
			LinkedBlockingDeque<Mesaj> coadaMesaje, 
			int id) {
		super();
		this.coadaMesaje = coadaMesaje;
		this.id = id;
	}
	
	public void stopGenerator() {
		this.esteOprit = true;
	}
	
	@Override
	public void run() {
		while(!this.esteOprit) {
			Random random = new Random();
			Mesaj m  = new Mesaj(this.id,
					"Mesaj generat de " + this.id,
					random.nextInt(10000));
			System.out.println("Mesaj generat " + random.nextInt(10000));
			try {
				this.coadaMesaje.put(m);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		//cand generatorul se opreste va injecta mesaje cu 
		// valoare -1 (poisen pill) pentru a notifica
		// consumatorii sa se opreasca
		// injectam 5 mesaje 
		// (in sistem nu vom avea mai mult de 5 consumatori)
		
		for(int i = 0; i < 5; i++) {
			Mesaj poisenPill = 
					new Mesaj(this.id, "Stop", -1);
			try {
				this.coadaMesaje.put(poisenPill);
			} catch (InterruptedException e) {
			}
		}
		
		System.out.println("Thread " + this.id + 
				" se inchide");
	}
}








