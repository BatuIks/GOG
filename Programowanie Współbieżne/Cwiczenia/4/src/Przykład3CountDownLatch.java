package przyklady;

import java.util.concurrent.CountDownLatch;

public class Przykład3CountDownLatch {

    private static final int N_PRZYPISAŃ = 100000;
    private static final int PO_ILU_POBUDKA = 50000;
    private static volatile int licznik;
    private static final CountDownLatch zatrzask =
            new CountDownLatch(PO_ILU_POBUDKA);

    public static void main(final String[] args) {
        final Thread główny = Thread.currentThread();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < N_PRZYPISAŃ; ++i) {
                    if (Thread.currentThread().isInterrupted()) {
                        główny.interrupt();
                        break;
                    }
                    licznik = i + 1;
                    zatrzask.countDown();
                }
            }
        }).start();
        try {
            zatrzask.await();
            System.out.println(licznik);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Wykonanie przerwane");
        }
    }

}
