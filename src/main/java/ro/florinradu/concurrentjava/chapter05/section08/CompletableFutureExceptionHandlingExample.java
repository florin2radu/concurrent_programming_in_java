package ro.florinradu.concurrentjava.chapter05.section08;

// Book section: 5.8.6 Exception Handling in CompletableFuture
// Adapted into a standalone runnable example.

import java.util.concurrent.CompletableFuture;

public class CompletableFutureExceptionHandlingExample {
    public static void main(String[] args) {
        int recovered = CompletableFuture.<Integer>supplyAsync(() -> {
                    throw new RuntimeException("Calculation error");
                })
                .handle((result, error) -> {
                    if (error != null) {
                        System.out.println("An error occurred: " +
                                error.getCause());
                        return -1;
                    }
                    return result * 2;
                })
                .whenComplete((result, error) ->
                        System.out.println("Completed with result: " + result))
                .join();

        System.out.println("Final value: " + recovered);
    }
}
