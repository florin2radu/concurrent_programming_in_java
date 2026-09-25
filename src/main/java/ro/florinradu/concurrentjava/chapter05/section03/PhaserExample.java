package ro.florinradu.concurrentjava.chapter05.section03;

// Book section: 5.3.4 Phaser
// Adapted into a standalone runnable example.

import java.util.concurrent.Phaser;
import java.util.concurrent.ThreadLocalRandom;

public class PhaserExample {
    public static void main(String[] args) throws InterruptedException {
        int workers = 3;
        Phaser phaser = new Phaser(1);

        for (int i = 0; i < workers; i++) {
            phaser.register(); // register before the thread starts

            new Thread(() -> {
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextLong(100, 400));
                    System.out.println(Thread.currentThread().getName() + " finished phase 1");
                    phaser.arriveAndAwaitAdvance();

                    Thread.sleep(ThreadLocalRandom.current().nextLong(100, 400));
                    System.out.println(Thread.currentThread().getName() + " finished phase 2");
                    phaser.arriveAndAwaitAdvance();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    phaser.arriveAndDeregister();
                }
            }, "worker-" + i).start();
        }

        phaser.arriveAndDeregister();
    }
}
