package ro.florinradu.concurrentjava.chapter05.section08;

// Book section: 5.8.5 Combining Parallel Tasks
// Adapted into a standalone runnable example.

import java.util.concurrent.CompletableFuture;

public class CompletableFutureAllOfExample {
    public static void main(String[] args) {
        CompletableFuture<String> f1 = delayed("Result 1", 500);
        CompletableFuture<String> f2 = delayed("Result 2", 300);
        CompletableFuture<String> f3 = delayed("Result 3", 100);

        // completes after all three tasks complete
        CompletableFuture<Void> all = CompletableFuture.allOf(f1, f2, f3);

        // process the individual results after all tasks have completed
        all.thenRun(() -> {
            try {
                System.out.println(f1.get());
                System.out.println(f2.get());
                System.out.println(f3.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        all.join(); // keeps main thread alive
    }

    private static CompletableFuture<String> delayed(String value, long millis) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException(e);
            }

            return value;
        });
    }
}