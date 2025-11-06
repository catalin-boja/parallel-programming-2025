package ro.ase.ie.paralel;

public class Mesaj {

	int id;
	String text;
	int valoare;
	
	public Mesaj(int id, String text, int valoare) {
		super();
		this.id = id;
		this.text = text;
		this.valoare = valoare;
	}

	public int getId() {
		return id;
	}

	public String getText() {
		return text;
	}

	public int getValoare() {
		return valoare;
	}
	
	
	
	
}
