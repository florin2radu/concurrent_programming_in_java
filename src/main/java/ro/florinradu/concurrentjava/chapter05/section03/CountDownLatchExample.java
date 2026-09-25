package ro.florinradu.concurrentjava.chapter05.section03;

// Book section: 5.3.2 CountDownLatch
// Adapted into a standalone runnable example.

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

public class CountDownLatchExample {
    public static void main(String[] args) throws InterruptedException {
        int workers = 5;
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(workers);

        for (int i = 0; i < workers; i++) {
            Thread worker = new Thread(() -> {
                try {
                    startSignal.await();
                    System.out.println(
                            Thread.currentThread().getName() + " executes the task");
                    Thread.sleep(ThreadLocalRandom.current().nextLong(100, 500));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    doneSignal.countDown();
                }
            }, "worker-" + i);
            worker.start();
        }

        startSignal.countDown();
        doneSignal.await();
        System.out.println("All tasks have completed.");
    }
}
