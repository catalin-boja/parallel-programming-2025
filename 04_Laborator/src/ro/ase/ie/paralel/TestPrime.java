package ro.ase.ie.paralel;

import java.util.ArrayList;

public class TestPrime {
	
	public static boolean estePrim(int valoare) {
		for(int i = 2; i < valoare/2 + 1; i++) {
			if(valoare % i == 0) {
				return false;
			}
		}
		return true;
	}
	
	//calcul numar valori prime intr-un interval
	
	public static int calculPrimeSecvential(int limitaInferioara, int limitaSuperioara) {
		int contor = 0;
		for(int i = limitaInferioara; i <= limitaSuperioara; i++) {
			if(estePrim(i))
				contor += 1;
		}
		return contor;
	}
	
	// test solutie paralela
	public static int calculPrimeParalel(int limitaInf, int limitaSup) throws InterruptedException {
		int nrFire = 4;
		ArrayList<ThreadPrim> fire = new ArrayList<>();
		int interval = (limitaSup - limitaInf)/ nrFire;
		for(int i = 0 ;i < nrFire; i++) {
			int limitaI = i*interval + limitaInf;
			int limitaS = (i == nrFire-1) ? limitaSup : (i+1)*interval;
			ThreadPrim tp = new ThreadPrim(limitaI, limitaS);
			fire.add(tp);
			tp.start();
		}
		
		int rezultat = 0;
		for(int i =0 ; i < nrFire; i++) {
			fire.get(i).join();
			rezultat += fire.get(i).getContor();
		}
		
		return rezultat;
	}
	
	
	public static void main(String[] args) throws InterruptedException {

		final int limitaInf = 1;
		final int limitaSup = (int) 5e5;
		
		System.out.println("Test solutie secventiala");
		double tStart = System.currentTimeMillis();
		int rezultat = calculPrimeSecvential(limitaInf, limitaSup);
		double tFinal = System.currentTimeMillis();
		System.out.println(String.format(
				"Rezultat = %d in %f secunde", 
				rezultat, 
				(tFinal-tStart)/1000));
		
		
		System.out.println("Test solutie paralela");
		tStart = System.currentTimeMillis();
		rezultat = calculPrimeParalel(limitaInf, limitaSup);
		tFinal = System.currentTimeMillis();
		System.out.println(String.format(
				"Rezultat = %d in %f secunde", 
				rezultat, 
				(tFinal-tStart)/1000));
		
	}

}




