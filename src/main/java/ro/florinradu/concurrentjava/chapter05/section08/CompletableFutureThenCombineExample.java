package ro.florinradu.concurrentjava.chapter05.section08;

// Book section: 5.8.5 Combining Parallel Tasks
// Adapted into a standalone runnable example.

import java.util.concurrent.CompletableFuture;

public class CompletableFutureThenCombineExample {
    public static void main(String[] args) {
        CompletableFuture<Integer> a =
                CompletableFuture.supplyAsync(() -> 40);
        CompletableFuture<Integer> b =
                CompletableFuture.supplyAsync(() -> 2);

        int result = a.thenCombine(b, Integer::sum).join();
        System.out.println(result); // 42
    }
}
