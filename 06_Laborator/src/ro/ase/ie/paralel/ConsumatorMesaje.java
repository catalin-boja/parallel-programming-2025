package ro.ase.ie.paralel;

import java.util.concurrent.LinkedBlockingDeque;

public class ConsumatorMesaje extends Thread{

	LinkedBlockingDeque<Mesaj> coadaMesaje;
	String nume;
	
	public ConsumatorMesaje(LinkedBlockingDeque<Mesaj> coadaMesaje, String nume) {
		super();
		this.coadaMesaje = coadaMesaje;
		this.nume = nume;
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				Mesaj m = this.coadaMesaje.take();
				
				System.out.println(String.format(
						"Procesare mesaj %s cu valoare %d", m.getText(), m.getValoare()));
				
				if(m.getValoare() == -1) {
					System.out.println("Consumator " + this.nume + 
							" se inchide !");
					return;
				}
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	
}



