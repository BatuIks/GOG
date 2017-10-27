
import java.util.concurrent.Semaphore;

public class SemaphoreTest_1 {

    Semaphore binarny = new Semaphore(3);

    public static void main(String args[]) {
        final SemaphoreTest_1 test = new SemaphoreTest_1();

        for (int i = 5; i > 0; i--) {

            new Thread() {
                @Override
                public void run() {
                    test.wzajemneWykluczanie();
                }
            }.start();

            new Thread() {
                @Override
                public void run() {
                    test.wzajemneWykluczanie();
                }
            }.start();
        }

        System.out.println("koniec");
    }

    private void wzajemneWykluczanie() {
        try {
            binarny.acquire();

            // sekcja krytyczna
            System.out.println(Thread.currentThread().getName() + " w sekcji krytycznej");
            Thread.sleep(2000);

        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            System.err.println("Thread " + Thread.currentThread().getName() + " interrupted");
        } finally {
            binarny.release();
            System.out.println(Thread.currentThread().getName() + " poza sekcją krytyczną");
        }
    }

}
