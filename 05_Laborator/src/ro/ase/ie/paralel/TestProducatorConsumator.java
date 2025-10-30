package ro.ase.ie.paralel;

public class TestProducatorConsumator {
	public static void main(String[] args) throws InterruptedException {
		
		Piata piata = new Piata(10000);
		
		Producator p1 = new Producator(piata, "P1");
		Producator p2 = new Producator(piata, "P2");
		
		Consumator c1 = new Consumator("C1", piata);
		Consumator c2 = new Consumator("C2", piata);
		
		//pornim producatorii
		p1.start();
		p2.start();
		
		//suspendam main thread pentru 2 secunda
		//Thread.sleep(1000);
		
		//pornim consumatorii
		c1.start();
		c2.start();
		
		//dupa 10 secunde inchidem piata
		Thread.sleep(10000);
		
		piata.inchidePiata();
		
		p1.join();
		p2.join();
		c1.join();
		c2.join();
		
		System.out.println("Piata inchisa");
		
	}
}
