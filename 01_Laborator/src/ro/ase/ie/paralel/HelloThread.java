package ro.ase.ie.paralel;

public class HelloThread extends Thread{
	
	int id;
	boolean esteTerminat = false;
	
	public HelloThread(int id) {
		super();
		this.id = id;
	}
	
	public boolean isEsteTerminat() {
		return esteTerminat;
	}

	@Override
	public void run() {
		System.out.println(
				"Hello din thread HelloThread cu id " + 
		this.id);
		this.esteTerminat = true;
	}
}
