package ro.florinradu.concurrentjava.chapter08.section04;

// Book section: 8.4.4 Using ExecutorService with Virtual Threads
// Adapted into a standalone runnable example.

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class VirtualThreadExecutorExample {
    public static void main(String[] args) throws Exception {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Future<?>> futures = new ArrayList<>();

            for (int i = 0; i < 5; i++) {
                int id = i;
                futures.add(executor.submit(() ->
                        System.out.println("Task " + id + " executed by " + Thread.currentThread())));
            }

            for (Future<?> future : futures) {
                future.get();
            }
        }
    }
}
