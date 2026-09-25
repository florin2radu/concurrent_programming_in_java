package ro.florinradu.concurrentjava.chapter05.section07;

// Book section: 5.7.3 Future and Result Management
// Adapted into a standalone runnable example.

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureCancellationExample {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = Executors.newSingleThreadExecutor();

        try {
            Future<?> future = pool.submit(() -> {
                long iterations = 0;

                while (!Thread.currentThread().isInterrupted()) {
                    iterations++;
                }

                System.out.println("Worker observed interruption after " + iterations + " iterations.");
            });

            Thread.sleep(2000);
            boolean cancelled = future.cancel(true);
            System.out.println("Cancellation requested: " + cancelled);
        } finally {
            pool.shutdownNow();
        }
    }
}
