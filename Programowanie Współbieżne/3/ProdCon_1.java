package threads;

import java.util.concurrent.Semaphore;
import java.util.Random;

public class ProdCon_1 {

    public static void main(String args[]) {
        Queue_1 q = new Queue_1();
        /**
         * Prosty przykład problemu producenta-konsumenta z buforem
         * jednoelementowym.
         *
         */
        new Consumer_1(q, 1);
        new Consumer_1(q, 2);
        new Consumer_1(q, 3);
        new Producer_1(q, 1);
        new Producer_1(q, 2);
        new Producer_1(q, 3);
    }
}

class Queue_1 {

    int value;
    private Random r = new Random();

    static Semaphore semCon = new Semaphore(0);
    static Semaphore semProd = new Semaphore(1);

    void get() {
        try {
            semCon.acquire();
        } catch (InterruptedException e) {
            System.out.println("InterruptedException caught");
        }
        System.out.println(Thread.currentThread().getName() + ": got: " + value);
        semProd.release();
    }

    void put(int n) {
        try {
            semProd.acquire();
        } catch (InterruptedException e) {
            System.out.println("InterruptedException caught");
        }
        this.value = n;
        System.out.println(Thread.currentThread().getName() + ": put: " + n);
        semCon.release();
    }

    int getNextRandomInt() {
        return r.nextInt(500);
    }
}

class Producer_1 implements Runnable {

    Queue_1 q;

    Producer_1(Queue_1 q, int i) {
        this.q = q;
        new Thread(this, "Producer " + i).start();
     
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            q.put(i);
            try {  
//              if(i==1) Thread.currentThread().interrupt();
                Thread.sleep(q.getNextRandomInt());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Thread " + Thread.currentThread().getName() + " interrupted");
            }
        }
    }
}

class Consumer_1 implements Runnable {

    Queue_1 q;

    Consumer_1(Queue_1 q, int i) {
        this.q = q;
        new Thread(this, "Consumer " + i).start();
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            q.get();
            try {
                Thread.sleep(q.getNextRandomInt());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Thread " + Thread.currentThread().getName() + " interrupted");
            }
        }
    }
}
