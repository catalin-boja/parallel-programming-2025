package ro.ase.ie.paralel;

public class ThreadContor extends Thread{
	Contor contor;
	int nrIteratii;
	
	public ThreadContor(Contor contor, int nrIteratii) {
		super();
		this.contor = contor;
		this.nrIteratii = nrIteratii;
	}
	
	@Override
	public void run() {
		while(this.nrIteratii > 0) {
			this.contor.increment();
			this.nrIteratii -= 1;
		}
	}
	
	
}



