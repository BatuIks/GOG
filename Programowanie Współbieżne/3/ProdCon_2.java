import java.util.concurrent.Semaphore;
import java.util.Random;

public class ProdCon_2 {

	public static void main(String args[]) {
		int C = 5; // liczba konsumentów
		int P = 5; // liczba producentów

		int bufferSize = 5; // rozmiar bufora

		Queue_2 q = new Queue_2(bufferSize);
		/**
		 * Przykład problemu producenta-konsumenta z buforem ustalonej,
		 * ograniczonej pojemności.
		 * 
		 */

		for (int i = C; i > 0; i--) {
			new Consumer_2(q, i);
		}

		for (int i = P; i > 0; i--) {
			new Producer_2(q, i);
		}

		System.out.println("***** Wątków jest: " + Thread.activeCount() + " (w tym P = " + P + ", K = " + C + ".)");
	}
}

class Queue_2 {
	static int j;
	static int k;

	static int bufferSize; // rozmiar bufora
	int[] buffer;

	static Semaphore semCon;
	static Semaphore semProd;
	//
	// semafory do ewentualnej ochrony zmiennych j i k - czy potrzebne ?
	//
	static Semaphore chronJ;
	static Semaphore chronK;

	Queue_2(int bufferSize) {
		Queue_2.bufferSize = bufferSize;
		buffer = new int[bufferSize];
		k = 0;
		j = 0;
		System.out.println("***** Wielkość bufora: " + Queue_2.bufferSize);
		semCon = new Semaphore(0);
		semProd = new Semaphore(bufferSize);
		chronJ = new Semaphore(1);
		chronK = new Semaphore(1);
	}

	private Random r = new Random();

	void get() {
		try {
			semCon.acquire();
			System.out.println(Thread.currentThread().getName() + " mija semafor semCon");
		} catch (InterruptedException e) {
			System.out.println("InterruptedException caught");
		}
		try {
			chronJ.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + " pobiera " + buffer[j] + " z miejsca " + j);
		j = (j + 1) % bufferSize;
		chronJ.release();
		semProd.release();
	}

	void put(int n) {
		try {
			semProd.acquire();
			System.out.println(Thread.currentThread().getName() + " mija semafor semProd");
		} catch (InterruptedException e) {
			System.out.println("InterruptedException caught");
		}
		try {
			chronK.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		buffer[k] = n;
		System.out.println(Thread.currentThread().getName() + " wstawia " + n + " na miejsce " + k);
		k = (k + 1) % bufferSize;
		chronK.release();
		semCon.release();
	}

	int getNextRandomInt() {
		return r.nextInt(500);
	}
}

class Producer_2 implements Runnable {
	Queue_2 q;
	int myNumber;

	Producer_2(Queue_2 q, int i) {
		this.q = q;
		myNumber = i;
		new Thread(this, "Producer " + i).start();
	}

	public void run() {
		System.out.println("***** Producent " + myNumber + " startuje");

		for (int i = 0; i < 5; i++) {
			System.out.println(myNumber + " chce wstawić");
			q.put(i);
			try {
				Thread.sleep(q.getNextRandomInt());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

class Consumer_2 implements Runnable {
	Queue_2 q;
	int myNumber;

	Consumer_2(Queue_2 q, int i) {
		this.q = q;
		myNumber = i;
		new Thread(this, "Consumer " + i).start();
	}

	public void run() {
		System.out.println("***** Konsument " + myNumber + " startuje");

		for (int i = 0; i < 5; i++) {
			System.out.println(myNumber + " chce pobrać");
			q.get();
			try {
				Thread.sleep(q.getNextRandomInt());

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
