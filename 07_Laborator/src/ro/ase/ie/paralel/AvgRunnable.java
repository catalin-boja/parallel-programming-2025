package ro.ase.ie.paralel;

import java.util.List;

public class AvgRunnable implements Runnable{
	
	//input
	List<Integer> valori;
	//output
	Double average;
		
	public AvgRunnable(List<Integer> valori) {
		this.valori = valori;
	}
	
	public Double getAverage() {
		return average;
	}

	@Override
	public void run() {
		double suma = 0;
		for(Integer value : valori) {
			suma += value;
		}
		this.average = suma / valori.size();
	}

}
