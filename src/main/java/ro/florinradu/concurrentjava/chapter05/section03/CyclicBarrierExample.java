package ro.florinradu.concurrentjava.chapter05.section03;

// Book section: 5.3.3 CyclicBarrier
// Adapted into a standalone runnable example.

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadLocalRandom;

public class CyclicBarrierExample {
    public static void main(String[] args) throws InterruptedException {
        int workers = 3;
        CyclicBarrier barrier = new CyclicBarrier(workers);

        for (int i = 0; i < workers; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextLong(100, 400));
                    System.out.println(Thread.currentThread().getName() + " reached phase 1");
                    barrier.await();

                    Thread.sleep(ThreadLocalRandom.current().nextLong(100, 400));
                    System.out.println(Thread.currentThread().getName() + " reached phase 2");
                    barrier.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (BrokenBarrierException e) {
                    throw new IllegalStateException(e);
                }
            }, "worker-" + i).start();
        }
    }
}
