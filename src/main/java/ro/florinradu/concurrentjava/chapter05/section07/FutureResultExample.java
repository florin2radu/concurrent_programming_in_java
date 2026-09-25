package ro.florinradu.concurrentjava.chapter05.section07;

// Book section: 5.7.3 Future and Result Management
// Adapted into a standalone runnable example.

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureResultExample {
    public static void main(String[] args) throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(2);

        try {
            Future<Integer> future = pool.submit(() -> {
                Thread.sleep(2000);
                return 42;
            });

            if (!future.isDone()) {
                System.out.println("Task is still running...");
            }

            System.out.println("The result is: " + future.get());
        } finally {
            pool.shutdown();
        }
    }
}
