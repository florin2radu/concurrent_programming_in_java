package ro.florinradu.concurrentjava.chapter07.section01;

// Book section: 7.1.2 Synchronization Barrier Pattern
// Adapted into a standalone runnable example.
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadLocalRandom;

public class BarrierPatternExample {
    public static void main(String[] args) {
        int workers = 3;
        List<Integer> results = new CopyOnWriteArrayList<>();

        CyclicBarrier barrier =
            new CyclicBarrier(workers, () -> {
            int total = results.stream().mapToInt(Integer::intValue).sum();
            System.out.println("All workers reached the barrier. Combined result: " + total);
            results.clear();
        });

        for (int i = 0; i < workers; i++) {
            Thread worker = new Thread(() -> {
                try {
                    for (int round = 1; round <= 3; round++) {
                        System.out.println(Thread.currentThread().getName() + " is working in round " + round);
                        Thread.sleep(ThreadLocalRandom.current().nextLong(100, 700));
                        results.add(ThreadLocalRandom.current().nextInt(100));
                        // synchronization point
                        barrier.await();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (BrokenBarrierException e) {
                    throw new IllegalStateException("Barrier was broken", e);
                }
            }, "worker-" + i);
            worker.start();
        }
    }
}
