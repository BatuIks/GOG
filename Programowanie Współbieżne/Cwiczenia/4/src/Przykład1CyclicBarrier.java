import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Przykład1CyclicBarrier {

    private static final int OPÓŹNIENIE = 2;
    private static final int N_PRZESTAWIANYCH = 10;
    private static final int[] dane = new int[N_PRZESTAWIANYCH];
    private static final Random random = new Random();

    private static final CyclicBarrier bariera1;
    private static final CyclicBarrier bariera2;

    static {
        bariera1 = new CyclicBarrier(N_PRZESTAWIANYCH);
        bariera2 = new CyclicBarrier(N_PRZESTAWIANYCH, new Runnable() {
            @Override
            public void run() {
                for (int x : dane) {
                    System.out.print(", " + x);
                }
                System.out.println();
            }
        });
    }

    private static void losoweOpóźnienie() throws InterruptedException {
        Thread.sleep(random.nextInt(OPÓŹNIENIE));
    }

    private static class Pracownik implements Runnable {

        private final int która;

        private Pracownik(final int która) {
            this.która = która;
        }

        @Override
        public void run() {
            try {
                losoweOpóźnienie();
                dane[która] = która + 1;
                bariera1.await();
                losoweOpóźnienie();
                final int druga = dane[N_PRZESTAWIANYCH - 1 - która];
                bariera1.await();
                losoweOpóźnienie();
                dane[która] = druga;
                bariera2.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                Thread.currentThread().interrupt();
                System.err.println("Wątek " + która + " przerwany");
            }
        }

    }

    public static void main(final String[] args) {
        for (int i = 0; i < N_PRZESTAWIANYCH; ++i) {
            new Thread(new Pracownik(i)).start();
        }
    }

}
