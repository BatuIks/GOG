package przyklady;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Przykład2SynchronizedList {

    private static final int N_WSTAWIANYCH = 100000;
    private static final List<Integer> wynik =
            Collections.synchronizedList(new ArrayList<Integer>());

    private static void wstawiaj() throws InterruptedException {
        for (int i = 0; i < N_WSTAWIANYCH; ++i) {
            if (Thread.currentThread().isInterrupted()) {
                throw new InterruptedException();
            }
            wynik.add(i);
        }
    }

    public static void main(final String[] args) {
        final Thread główny = Thread.currentThread();
        final Thread pomocnik = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    wstawiaj();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    główny.interrupt();
                }
            }
        });
        pomocnik.start();
        try {
            wstawiaj();
            pomocnik.join();
            System.out.println(wynik.size());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Wykonanie przerwane");
        }
    }

}
