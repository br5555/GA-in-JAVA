package hr.fer.zemris.optjava.dz11;

import java.util.concurrent.*;

public class Proba1 {
	public static void main(String[] args) {
		ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<String>();
		ConcurrentLinkedQueue<String> queue2 = new ConcurrentLinkedQueue<String>();

		// Thread producer = new Thread(new Producer(queue));
		// Thread consumer = new Thread(new Consumer(queue));
		// producer.start();
		// consumer.start();
		int numberOfThreads = Runtime.getRuntime().availableProcessors();
		Thread[] workers = new Thread[numberOfThreads];
//		for (int j = 0; j < numberOfThreads; j++) {
//			workers[j] = new Thread(new Consumer(queue, queue2));
//
//		}

//		for (int i = 0; i < 10; i++) {
//			queue.add("String" + i);
//
//		}
//		for (int j = 0; j < numberOfThreads; j++) {
//			workers[j].start();
//
//		}
		for (int k = 0; k < 100; k++) {
			for (int j = 0; j < numberOfThreads; j++) {
				workers[j] = new Thread(new Consumer(queue, queue2));

			}
			for (int j = 0; j < numberOfThreads; j++) {
				workers[j].start();

			}
		
			for (int i = 0; i < 10; i++) {
				queue.add("String" + k+" "+i);
	
			}
			String str;

		for (int j = 0; j < numberOfThreads; j++) {
			while(true){
				try {
					workers[j].join();
					break;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}


		}
		
			while((str = queue2.poll()) != null) {
				System.out.println(str);
			}
		}
	}
}

// the producer puts strings on the queue
class Producer implements Runnable {

	ConcurrentLinkedQueue<String> queue;
	ConcurrentLinkedQueue<String> queue2;

	Producer(ConcurrentLinkedQueue<String> queue, ConcurrentLinkedQueue<String> queue2) {
		this.queue = queue;
		this.queue2 = queue2;
	}

	public void run() {
		System.out.println("Producer Started");
		try {
			for (int i = 1; i < 10; i++) {
				queue.add("String" + i);
				System.out.println("Added: String" + i);
				Thread.currentThread().sleep(200);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}

// the consumer removes strings from the queue
class Consumer implements Runnable {

	ConcurrentLinkedQueue<String> queue;
	ConcurrentLinkedQueue<String> queue2;

	public Consumer(ConcurrentLinkedQueue<String> queue, ConcurrentLinkedQueue<String> queue2){
	      this.queue = queue;
	      this.queue2 = queue2;
	   }

	public void run() {
		String str;
		System.out.println("Consumer Started");

		while((str = queue.poll()) != null) {
			queue2.add("procito "+str);
			System.out.println(str);
		}

	}
}
