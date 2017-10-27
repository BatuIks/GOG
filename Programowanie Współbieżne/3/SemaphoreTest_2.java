
import java.util.concurrent.*;

public class SemaphoreTest_2 {

    public static void main(String args[]) {
        Semaphore semaphore = new Semaphore(2);
        MyThread mt1 = new MyThread(semaphore);
        MyThread mt2 = new MyThread(semaphore);
        MyThread mt3 = new MyThread(semaphore);
        MyThread mt4 = new MyThread(semaphore);
        mt1.start();
        mt2.start();
//      mt2.interrupt();
        mt3.start();
        mt4.start();
    }
}

class MyThread extends Thread {

    Semaphore semaphore;

    MyThread(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    public void run() {
        try {
            semaphore.acquire();
            System.out.println("Hello " + this.getName());
            try {
                sleep(2000);
            } catch (Exception e) {
            }
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            System.err.println("Thread " + Thread.currentThread().getName() + " interrupted");
        } finally {
            semaphore.release();
            System.out.println("Goodbye " + this.getName());
        }
    }
}
