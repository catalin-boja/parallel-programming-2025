package ro.ase.ie.paralel;

public class ContBancar {
	double sold;

	public double getSold() {
		return sold;
	}
	
	public ContBancar(double sold) {
		super();
		this.sold = sold;
	}

	public synchronized void plata(double suma) {
		System.out.println("Se doreste plata a " + suma);
		if(suma <= this.sold) {
			System.out.println("Se plateste " + suma);
			this.sold -= suma;
			System.out.println("Sold disponibil " + sold);
		}
	}
}
