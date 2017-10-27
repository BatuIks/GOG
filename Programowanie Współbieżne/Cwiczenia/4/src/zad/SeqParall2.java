package zad;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class SeqParall2 {

    private static int i;
    private static final int num_row = 10;
    private static int num_col = 100;
    private static List<Integer> tab = Collections.synchronizedList(new ArrayList<Integer>());
    private static void wstawiaj() throws InterruptedException {
        for (int i = 0; i < num_col; ++i) {
            if (Thread.currentThread().isInterrupted()) {
                throw new InterruptedException();
            }
            tab.add(i);
        }
    }
    private static CyclicBarrier barrier = new CyclicBarrier(num_col, new Runnable() {

        @Override
        public void run() {
            int sum = 0;
            for(int el:tab) {
                sum += el;
            }

            tab.clear();


            System.out.println(i + "-th " + sum);
            i++;
        }
    });

    //    ##############################################
    static class Col implements Runnable{
        private final int number_col;

        Col(int number_col){
            this.number_col = number_col;
        }


        @Override
        public void run() {

            final int a = 2 * number_col + 1;
            final int wyn = (a % 4 - 2) * a;
            int wyn2 = 0;

            for(int i = 0; i< num_row; i++){
                try {
                    wyn2 = wyn2 + wyn;
                    tab.add(wyn2);
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("Wątek " + number_col + " przerwany");
                }
            }
        }
    }


//#############################################3

    public static void main(final String[] args) {
        for (int i = 0; i < num_col; ++i) {
            new Thread(new SeqParall2.Col(i)).start();
        }
    }
}

