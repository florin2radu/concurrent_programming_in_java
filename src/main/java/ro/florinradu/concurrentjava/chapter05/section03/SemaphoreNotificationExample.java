package ro.florinradu.concurrentjava.chapter05.section03;

// Book section: 5.3.1 Semaphore
// Adapted into a standalone runnable example.

import java.util.concurrent.Semaphore;

public class SemaphoreNotificationExample {
    public static void main(String[] args) throws InterruptedException {
        Semaphore semaphore = new Semaphore(0);

        Thread waiting = new Thread(() -> {
            try {
                semaphore.acquire();
                System.out.println("Waiting thread was notified.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "waiting");

        Thread notifier = new Thread(() -> {
            try {
                Thread.sleep(2000);
                semaphore.release();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "notifier");

        waiting.start();
        notifier.start();
    }
}
