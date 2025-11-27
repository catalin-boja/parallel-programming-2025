package ro.ase.ie.paralel;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class TestExecutors {

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		Integer[] valori = { 1, 2, 3, 4, 5, 6 };

		Thread avgThread = new Thread(new AvgRunnable(Arrays.asList(valori)));
		Callable<Double> avgCallable = new AvgCallable(Arrays.asList(valori));

		ExecutorService singleThread = Executors.newSingleThreadExecutor();

		// Solutie 1
		singleThread.execute(avgThread);
		singleThread.shutdown();

		while (!singleThread.isTerminated()) {
			System.out.println("Wait on worker thread");
		}

		// Nu avem cum sa obtinem rezultatul din thread-ul clasic
		// ....

		// Solutie 2
		singleThread = Executors.newSingleThreadExecutor();
		Future<Void> rezultatThread = (Future<Void>) singleThread.submit(avgThread);

//		while(!singleThread.isTerminated()) {
//			System.out.println("Wait on worker thread");
//		}

		// get este blocant - nu e nevoie de while
		// nu trece mai departe pana cand nu se termina thread-ul de executat
		rezultatThread.get();

		System.out.println("Rezultat: nu poate fi obtinut");

		// Solutia 3
		singleThread = Executors.newSingleThreadExecutor();
		Future<Double> rezultatAvg = singleThread.submit(avgCallable);

		System.out.println("Rezultat: " + rezultatAvg.get());

		// Test mai multe thread-uri
		ExecutorService multipleService = Executors.newFixedThreadPool(3);
		ArrayList<Callable<Double>> fire = new ArrayList<>();
		fire.add(avgCallable);
		fire.add(new AvgCallable(Arrays.asList(10, 20, 30)));
		fire.add(new AvgCallable(Arrays.asList(100, 50)));

		List<Future<Double>> threads = multipleService.invokeAll(fire);

		for (Future<Double> future : threads) {
			if (future.isDone()) {
				System.out.println("Rezultat: " + future.get());
			} else {
				System.out.println("Not finished");
			}
		}

		multipleService.shutdown();

		try {
			if (!multipleService.awaitTermination(800, 
					TimeUnit.MILLISECONDS)) {
				multipleService.shutdownNow();
			}
		} catch (InterruptedException e) {
			multipleService.shutdownNow();
		}
		
		System.out.println("Terminat");

	}

}
