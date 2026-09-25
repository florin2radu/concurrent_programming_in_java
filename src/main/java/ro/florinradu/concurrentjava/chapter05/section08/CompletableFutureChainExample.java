package ro.florinradu.concurrentjava.chapter05.section08;

// Book section: 5.8.4 Transformations and Operation Chains
// Adapted into a standalone runnable example.

import java.util.concurrent.CompletableFuture;

public class CompletableFutureChainExample {
    public static void main(String[] args) {
        CompletableFuture<Void> future =
                CompletableFuture.supplyAsync(() -> 10)
                        .thenApply(x -> x * 2)
                        .thenApply(x -> x + 5)
                        .thenAccept(System.out::println);

        future.join(); // keep main thread alive
    }
}
