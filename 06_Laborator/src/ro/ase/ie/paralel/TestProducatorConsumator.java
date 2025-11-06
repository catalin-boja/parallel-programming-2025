package ro.ase.ie.paralel;

import java.util.concurrent.LinkedBlockingDeque;

public class TestProducatorConsumator {

	public static void main(String args[]) throws InterruptedException {
		
		LinkedBlockingDeque<Mesaj> coadaMesaje = 
				new LinkedBlockingDeque<>(1000);
		
		GeneratorMesaje g1 = new GeneratorMesaje(coadaMesaje, 1);
		GeneratorMesaje g2 = new GeneratorMesaje(coadaMesaje, 2);
		
		ConsumatorMesaje c1 = 
				new ConsumatorMesaje(coadaMesaje, "Procesator 1");
		ConsumatorMesaje c2 = 
				new ConsumatorMesaje(coadaMesaje, "Procesator 2");
		
		g1.start();
		g2.start();
		
		c1.start();
		c2.start();
		
		Thread.sleep(10000);
		g1.stopGenerator();
		g2.stopGenerator();
		
		g1.join();
		g2.join();
		c1.join();
		c2.join();
		
		System.out.println("Mesaje ramase in coada: " + 
				coadaMesaje.size());
		
		System.out.println("Program terminat");
		
	}
}






