package ro.ase.ie.paralel;

public class TestDeadlock {

	public static void main(String[] args) throws InterruptedException {
		
		HelloThread t1 = new HelloThread("Alice");
		HelloThread t2 = new HelloThread("Bob");
		t1.setPrieten(t2);
		t2.setPrieten(t1);
		
		t1.start();
		t2.start();
		
		t1.join();
		t2.join();
		
		System.out.println("Sfarsit comunicare");
		
	}

}





