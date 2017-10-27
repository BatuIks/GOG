package zad;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class SeqParall1 {
    private static int i;
    private static final int num_row = 10;
    private static int num_col = 100;
    private static int[] tab = new int[num_col];
    private static CyclicBarrier barrier = new CyclicBarrier(num_col, new Runnable() {

        @Override
        public void run() {
            int sum = 0;
            for(int i=0 ; i<num_col ; i++) {
                sum += tab[i];
            }

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

            for(int i = 0; i< num_row; i++){
                try {
                    tab[number_col] += wyn;
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
            new Thread(new Col(i)).start();
        }
    }
}
