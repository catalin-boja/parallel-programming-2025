package ro.ase.ie.paralel;

import java.util.concurrent.atomic.AtomicInteger;

public class Contor {
	//int contor = 0;
	AtomicInteger contor = new AtomicInteger(0);

	public int getContor() {
		return contor.get();
	}
	
	public void increment() {
		//this.contor += 1;
		this.contor.incrementAndGet();
	}
}
