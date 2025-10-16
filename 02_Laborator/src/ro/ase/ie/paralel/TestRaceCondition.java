package ro.ase.ie.paralel;

public class TestRaceCondition {

	public static void main(String[] args) throws InterruptedException {

		ContBancar cont = new ContBancar(1000);
		ThreadCard card1 = new ThreadCard(cont, "Card1");
		ThreadCard card2 = new ThreadCard(cont, "Card2");
		
		card1.start();
		card2.start();
		
		card1.join();
		card2.join();
		
		System.out.println("Sold final: " + cont.getSold());
		
		Contor contor = new Contor();
		ThreadContor t1 = new ThreadContor(contor, (int) 1e6);
		ThreadContor t2 = new ThreadContor(contor, (int) 1e6);
		
		t1.start();
		t2.start();
		
		t1.join();
		t2.join();
		
		System.out.println("Valoare contor: " + 
				contor.getContor());
		
		
	}

}
