package ro.florinradu.concurrentjava.chapter05.section03;

// Book section: 5.3.1 Semaphore
// Adapted into a standalone runnable example.

import java.util.concurrent.Semaphore;

public class SemaphorePermitsExample {
    private static final Semaphore SEMAPHORE = new Semaphore(10);

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            try {
                System.out.println("Trying to acquire 3 permits...");
                SEMAPHORE.acquire(3);

                try {
                    System.out.println("Acquired 3 permits.");
                    Thread.sleep(400);
                } finally {
                    SEMAPHORE.release(3);
                    System.out.println("Released 3 permits.");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "worker").start();
    }
}
