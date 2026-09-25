package ro.florinradu.concurrentjava.chapter05.section04;

// Book section: 5.4.1 Atomic Classes
// Adapted into a standalone runnable example.

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCounterExample {
    public static void main(String[] args) throws InterruptedException {
        AtomicInteger counter = new AtomicInteger();

        Runnable task = () -> {
            for (int i = 0; i < 1_000_000; i++) {
                counter.incrementAndGet();
            }
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("Final value: " + counter.get());
    }
}
