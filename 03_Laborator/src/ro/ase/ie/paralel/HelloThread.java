package ro.ase.ie.paralel;

public class HelloThread extends Thread{
	String nume;
	HelloThread prieten;

	public HelloThread(String nume) {
		super();
		this.nume = nume;
	}
	
	public void setPrieten(HelloThread t) {
		this.prieten = t;
	}
	
	public String getNume() {
		return nume;
	}

	public synchronized void hello(HelloThread prieten) {
		System.out.println(
				this.nume + " spune hello lui " + prieten.getNume());
		prieten.helloBack(this);
	}
	
	public synchronized void helloBack(HelloThread prieten) {
		System.out.println(this.nume + " raspunde lui " +
				prieten.getNume());
	}
	
	@Override
	public void run() {
		if(this.prieten != null) {
			this.hello(prieten);
		}
	}
	
}








