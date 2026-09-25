package ro.florinradu.concurrentjava.chapter08.section04;

// Book section: 8.4.5 Example: Starting a Large Number of Virtual Threads
// Adapted into a standalone runnable example.

import java.util.concurrent.Executors;

public class ManyVirtualThreadsExample {
    public static void main(String[] args) throws InterruptedException {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 100_000; i++) {
                int id = i;
                executor.submit(() -> {
                    try {
                        Thread.sleep(1000);
                        System.out.println("Task " + id + " completed");
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
            }
        }
    }
}
