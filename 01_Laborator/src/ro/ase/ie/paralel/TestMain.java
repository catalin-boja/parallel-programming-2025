package ro.ase.ie.paralel;

public class TestMain {

	public static void main(String[] args) throws InterruptedException {
		
		HelloThread ht1 = new HelloThread(1);
		HelloThread ht2 = new HelloThread(2);
		
		HelloRunnable hr1 = new HelloRunnable();
		HelloRunnable hr2 = new HelloRunnable();
		
		System.out.println("Start exemplu thread-uri");
		ht1.start();
		ht2.start();
		
		Thread t1 = new Thread(hr1);
		Thread t2 = new Thread(hr2);
		
		t1.start();
		t2.start();
		
		System.out.println("Sfarsit exemplu thread-uri");
		
		ht1.join();
		ht2.join();
		t1.join();
		t2.join();		
		
		System.out.println("Terminat:" + ht1.isEsteTerminat());
		
		System.out.println("Sfarsit");
		
	}

}
