package ro.ase.ie.paralel;

import java.util.List;
import java.util.concurrent.Callable;

public class AvgCallable implements Callable<Double>{
	
	//input
	List<Integer> valori;
	
	public AvgCallable(List<Integer> valori) {
		super();
		this.valori = valori;
	}

	@Override
	public Double call() throws Exception {
		double suma = 0;
		for(Integer value : valori) {
			suma += value;
		}
		return suma / valori.size();
	}

}
