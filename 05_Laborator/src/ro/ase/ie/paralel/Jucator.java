package ro.ase.ie.paralel;

public class Jucator extends Thread{
	
	int numarNorocos;
	String nume; 
	JocBingo joc;
	
	public Jucator(String nume, int numarNorocos, JocBingo joc) {
		this.nume = nume;
		this.numarNorocos = numarNorocos;
		this.joc = joc;
	}
	
	@Override
	public void run() {
		System.out.println(this.nume + "a inceput sa joace ");
		while(true) {
			if(this.numarNorocos == joc.getNumarExtras()) {
				System.out.println(this.nume+ " a castigat !!!!!!!!!!!!!");
				break;
			}
		}
	}
	

}




