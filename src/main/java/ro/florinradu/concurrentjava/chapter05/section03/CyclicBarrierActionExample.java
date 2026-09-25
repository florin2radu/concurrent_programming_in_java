package ro.florinradu.concurrentjava.chapter05.section03;

// Book section: 5.3.3 CyclicBarrier
// Adapted into a standalone runnable example.

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierActionExample {
    public static void main(String[] args) throws InterruptedException {
        Runnable action = () -> System.out.println("All threads reached the barrier.");
        CyclicBarrier barrier = new CyclicBarrier(3, action);

        for (int i = 1; i <= 3; i++) {
            int id = i;

            new Thread(() -> {
                try {
                    System.out.println("Thread " + id + " is working...");
                    Thread.sleep(id * 500L);

                    System.out.println("Thread " + id + " reached the barrier.");
                    barrier.await();

                    System.out.println("Thread " + id + " continues.");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (BrokenBarrierException e) {
                    throw new IllegalStateException(e);
                }
            }).start();
        }
    }
}
