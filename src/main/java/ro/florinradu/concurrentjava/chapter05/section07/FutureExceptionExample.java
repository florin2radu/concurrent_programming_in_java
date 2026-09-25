package ro.florinradu.concurrentjava.chapter05.section07;

// Book section: 5.7.3 Future and Result Management
// Adapted into a standalone runnable example.

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureExceptionExample {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = Executors.newSingleThreadExecutor();

        try {
            Future<Integer> future = pool.submit(() -> {
                throw new IllegalStateException("Task failed");
            });

            try {
                future.get();
            } catch (ExecutionException e) {
                System.out.println("Original exception: " + e.getCause());
            }
        } finally {
            pool.shutdown();
        }
    }
}
