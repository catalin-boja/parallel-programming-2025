package ro.ase.ie.paralel;

public class MathThread extends Thread{
	
	long limitaInferioara;
	long limitaSuperioara;
	Contor contor;
	
	public MathThread(long limitaInferioara, long limitaSuperioara, Contor contor) {
		super();
		this.limitaInferioara = limitaInferioara;
		this.limitaSuperioara = limitaSuperioara;
		this.contor = contor;
	}
	
	public void run() {
		for(long i = limitaInferioara; 
				i <= limitaSuperioara; i++) {
			//calculam numarul de divizori ai fiecarui numar i
			for(long j = 1; j <= i/2; j++) {
				if(i % j == 0)
					this.contor.increment();
			}
		}
	}
	
	
}




