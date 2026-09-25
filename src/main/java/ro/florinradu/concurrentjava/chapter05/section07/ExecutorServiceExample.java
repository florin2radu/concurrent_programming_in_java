package ro.florinradu.concurrentjava.chapter05.section07;

// Book section: 5.7.2 Executor and ExecutorService
// Adapted into a standalone runnable example.

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceExample {
    public static void main(String[] args) throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(4);

        try {
            pool.submit(() -> System.out.println("Simple task"));

            Future<Integer> future = pool.submit(() -> 40 + 2);
            System.out.println("Result: " + future.get());
        } finally {
            pool.shutdown();

            if (!pool.awaitTermination(5, TimeUnit.SECONDS)) {
                pool.shutdownNow();
            }
        }
    }
}
