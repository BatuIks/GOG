
import java.util.concurrent.*;

public class SemaphoreTest_3 {

    public static void main(String[] args) {
        final int numberOfProcesses = 3;
        final int numberOfPermits = 2;

        Semaphore semaphore = new Semaphore(numberOfPermits, true);
        ProcessExclusion p[] = new ProcessExclusion[numberOfProcesses];

        for (int i = 0; i < numberOfProcesses; i++) {
            p[i] = new ProcessExclusion(semaphore);
            p[i].setThreadId(p[i].hashCode());
            p[i].start();
            if (i==1) p[1].interrupt();
        }
    }
}

class ProcessExclusion extends Thread {

    int threadId;
    private Semaphore semaphore;

    public ProcessExclusion(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }

    private int random(int n) {
        return (int) Math.round(n * Math.random());
    }

    private void busyCode() {
        int sleepPeriod = random(500);
        try {

            sleep(sleepPeriod);
        } catch (InterruptedException e) {      
        Thread.currentThread().interrupt();
        System.err.println("Thread " + Thread.currentThread().getName() + " interrupted");
        }
        }

    private void noncriticalCode() {
        busyCode();
    }

    private void criticalCode() {
        busyCode();
        System.out.println("Critical code " + Thread.currentThread().getName());
    }

    public void run() {
        for (int i = 0; i < 3; i++) {
            try {
                semaphore.acquire();
                criticalCode();
                semaphore.release();
            } catch (InterruptedException e) {
//              Thread.currentThread().interrupt();
                System.err.println("Exception " + e.toString());
            }
            finally{
                semaphore.release();
            }
        }
        for (int i = 0; i < 3; i++) {
            noncriticalCode();
        }
    }
}
