package ro.florinradu.concurrentjava.chapter02.section06;

// Book section: 2.6.4 Virtual-Thread Model
// Adapted into a standalone runnable example.

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class VirtualThreadModelExample {
    public static void main(String[] args) throws Exception {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Future<Integer>> futures = new ArrayList<>();

            IntStream.range(0, 20).forEach(i ->
                    futures.add(executor.submit(() -> {
                        Thread.sleep(100);
                        return i;
                    })));

            for (Future<Integer> future : futures) {
                System.out.print(future.get() + " ");
            }

            System.out.println();
        }
    }
}
