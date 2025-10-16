package ro.ase.ie.paralel;

import java.util.ArrayList;

public class TestSync {

	public static void main(String[] args) throws InterruptedException {
		
		long limita = (long) 8e4;
		Contor contor = new Contor();
		
		//Test pe 1 thread
		System.out.println("Test pe 1 thread");
		MathThread t = new MathThread(1, limita, contor);
		double tStart = System.currentTimeMillis();
		t.run();
		double tFinal = System.currentTimeMillis();
		System.out.println("Rezultat: " + contor.getValoare());
		System.out.println("Durata: "+ (tFinal-tStart)/1000 
				+ "secunde");
		
		//Test pe 4 thread-uri
		int nrThreaduri = 4;
		int interval = (int) (limita / nrThreaduri);
		ArrayList<Thread> fireExecutie = new ArrayList<>();
		contor.reset();
		System.out.println("Test pe 4 thread-uri");
		
		tStart = System.currentTimeMillis();
		for(int i = 0; i <nrThreaduri; i++) {
			MathThread th = new MathThread(
					i * interval + 1, interval*(i+1), contor);
			fireExecutie.add(th);
			th.start();
		}
		for(Thread th : fireExecutie) {
			th.join();
		}
		tFinal = System.currentTimeMillis();
		System.out.println("Rezultat: " + contor.getValoare());
		System.out.println("Durata: "+ (tFinal-tStart)/1000 
				+ "secunde");
		
		
		

	}

}





