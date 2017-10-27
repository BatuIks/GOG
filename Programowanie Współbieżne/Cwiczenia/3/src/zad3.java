//Zaimplementuj w javie, wykorzystując wątki i semafory problem śpiącego fryzjera.
//
//        Zakład fryzjerski zatrudnia jednego fryzjera, który dysponuje jednym fotelem,
// oraz poczekalnią z pewną liczbą krzeseł. W chwili zakończenia strzyżenia klient opuszcza zakład
// natomiast fryzjer udaje się do poczekalni i sprawdza, czy czeka tam jakiś klient. Jeśli tak,
// zaprasza jednego na swój fotel i strzyże go. Jeśli nie ma czekających klientów usypia na fotelu.
//
//        Każdy klient, po wejściu do zakładu sprawdza, co robi fryzjer. Jeśli ten  śpi, budzi go i
// siada na fotelu. Jeśli fryzjer strzyże kogoś, klient idzie do poczekalni i sprawdza, czy jest
// wolne krzesło. Jeśli tak, siada i czeka, jeśli nie, wychodzi.
//
//        Uwagi:
//
//        Zarówno fryzjer, jak też każdy z klientów powinien zostać zaimplementowany jako oddzielny
// wątek.
//        Do synchronizacji należy użyć semaforów.
//        Każdy z wątków powinien wypisywać w odpowiednich momentach stosowne komunikaty,
// pozwalające na prześledzenie przebiegu programu.
//        Implementacja powinna zawierać metodę main ilustrującą działanie tej implementacji.

import java.util.concurrent.Semaphore;

import sun.misc.Cleaner;

import java.util.concurrent.Semaphore;

public class zad3 {
    public static void main(String args[]) throws InterruptedException {
        Semaphore chair = new Semaphore(1, true);
        Semaphore wRoom = new Semaphore(3, true);
        new Stylist(chair, wRoom);
        for (int i = 0; i < 8; i++) {
            Thread.sleep(Math.round(100*Math.random()));
            new Client(wRoom, chair, i);
        }

    }
}


class Client implements Runnable {
    Semaphore wRoom;
    Semaphore chair;

    public Client(Semaphore wRoom, Semaphore chair, int i) {
        this.wRoom = wRoom;
        this.chair = chair;
        new Thread(this, "Client "+ i).start();
    }


    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " Wszedł do zakładu");
        if (!chair.hasQueuedThreads())
            try {
                chair.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        else if (wRoom.getQueueLength() > 3) {
            System.out.println("Żegnam Panów, za długa kolejka, byłem " + Thread.currentThread().getName());
            return;
        }
            else {
                try {
                    wRoom.acquire();
                    System.out.println("Witam panów w kolejce, jestem " + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    System.out.println("InterruptedException caught");
                }


                try {
                    chair.acquire();
                    System.out.println("Witam Pana Fryzjera jestem "+ Thread.currentThread().getName());

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
    }
}







class Stylist implements Runnable {
    private Semaphore chair;
    private Semaphore wRoom;
    private int i;

    public Stylist(Semaphore chair, Semaphore wRoom) {
        this.chair = chair;
        this.wRoom = wRoom;
        new Thread(this, "Stylist").start();
    }


    public void haircut() throws InterruptedException {
        Thread.sleep(Math.round(100 * Math.random()));
    }


    @Override
    public void run() {
        while (true) {
            while (!chair.hasQueuedThreads()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            wRoom.release();
            i++;
            System.out.println("zaczynam strzyżenie nr " + i);
            try {
                haircut();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            chair.release();
            System.out.println("skończyłem strzyżenie nr " + i);

        }
    }
}