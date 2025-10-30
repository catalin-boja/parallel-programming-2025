package ro.ase.ie.paralel;

public class Piata {
	
	int stoc;
	int capacitateMaxima;
	
	volatile boolean seInchide = false;
	
	public boolean esteInchisa() {
		return seInchide;
	}
	
	public synchronized void inchidePiata() {
		this.seInchide = true;
		this.notifyAll();
	}

	public void setSeInchide(boolean seInchide) {
		this.seInchide = seInchide;
	}

	public Piata(int capacitateMaxima) {
		super();
		this.capacitateMaxima = capacitateMaxima;
	}
	
	public synchronized void adaugaStoc(int valoare) throws InterruptedException {
		this.stoc += valoare;
		System.out.println("*** Capacitate curenta: " + this.stoc);
		if(this.stoc >= this.capacitateMaxima) {
			
			//notificam posibilele thread-uri consumator sa isi reia activitatea
			this.notifyAll();
			
			System.out.println("Blocam thread-ul producator curent");
			this.wait();
		}
	}
	
	public synchronized void scadeStoc(int valoare) throws InterruptedException {
		this.stoc -= valoare;
		System.out.println("*** Capacitate curenta: " + this.stoc);
		if(this.stoc <= 0) {
			
			//notificam posibilele thread-uri producator sa isi reia activitatea
			this.notifyAll();
			
			System.out.println("Blocam thread-ul consumator curent");
			this.wait();
		}
		
	}
	
	
}
