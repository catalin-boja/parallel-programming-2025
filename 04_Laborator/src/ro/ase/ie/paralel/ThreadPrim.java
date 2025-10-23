package ro.ase.ie.paralel;

public class ThreadPrim extends Thread{
	
	int contor = 0;
	int limitaInf;
	int limitaSup;
	
	public ThreadPrim(int limitaInf, int limitaSup) {
		super();
		this.limitaInf = limitaInf;
		this.limitaSup = limitaSup;
	}

	public int getContor() {
		return contor;
	}
	
	@Override
	public void run() {
		this.contor = 
				TestPrime.calculPrimeSecvential(limitaInf, 
						limitaSup);
	}

}









