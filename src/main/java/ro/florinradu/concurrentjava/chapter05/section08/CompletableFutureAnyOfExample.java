package ro.florinradu.concurrentjava.chapter05.section08;

// Book section: 5.8.5 Combining Parallel Tasks
// Adapted into a standalone runnable example.

import java.util.concurrent.CompletableFuture;

public class CompletableFutureAnyOfExample {
    public static void main(String[] args) {
        CompletableFuture<String> f1 = delayed("Result from f1", 700);
        CompletableFuture<Integer> f2 = delayed(42, 300);
        CompletableFuture<Double> f3 = delayed(Math.PI, 500);

        Object first = CompletableFuture.anyOf(f1, f2, f3).join();
        System.out.println("First completed task produced: " + first);
    }

    private static <T> CompletableFuture<T> delayed(T value, long millis) {
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
