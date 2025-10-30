package ro.ase.ie.paralel;

public class TestJoc {

	public static void main(String[] args) throws InterruptedException {
		
		JocBingo joc = new JocBingo(100);
		Jucator john = new Jucator("John",13, joc);
		Jucator alice = new Jucator("Alice",50, joc);
		Jucator bob = new Jucator("Bob",25, joc);
		
		john.start();
		alice.start();
		bob.start();
		
		joc.start();
		
		joc.join();
		john.join();
		alice.join();
		bob.join();
		
		System.out.println("Sfarsit exemplu");
		
	}

}
