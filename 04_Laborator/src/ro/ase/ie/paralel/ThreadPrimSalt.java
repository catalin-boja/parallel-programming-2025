package ro.ase.ie.paralel;

public class ThreadPrimSalt extends Thread{

	int contor = 0;
	int limitaInf;
	int limitaSup;
	int pas;
	
	public ThreadPrimSalt(int limitaInf, int limitaSup, int pas) {
		super();
		this.limitaInf = limitaInf;
		this.limitaSup = limitaSup;
		this.pas = pas;
	}

	public int getContor() {
		return contor;
	}
	
	@Override
	public void run() {
		System.out.println("Thread pornit");
		double tStart = System.currentTimeMillis();
		
		for(int i = this.limitaInf; i <= this.limitaSup; i += this.pas) {
			if(TestPrime.estePrim(i))
					this.contor += 1;
		}
		
		double tFinal = System.currentTimeMillis();
		System.out.println(String.format("Nr gasite: %d in %f",
				this.getContor(),
				(tFinal-tStart)/1000
				));
	}

}
