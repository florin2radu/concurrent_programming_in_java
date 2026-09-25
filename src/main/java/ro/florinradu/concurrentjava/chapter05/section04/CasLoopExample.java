package ro.florinradu.concurrentjava.chapter05.section04;

// Book section: 5.4.3 Lock-Free Structures and CAS Algorithms
// Adapted into a standalone runnable example.

import java.util.concurrent.atomic.AtomicInteger;

public class CasLoopExample {
    public static void main(String[] args) {
        AtomicInteger counter = new AtomicInteger();

        int oldValue;
        int newValue;

        do {
            oldValue = counter.get();
            newValue = oldValue + 1;
        } while (!counter.compareAndSet(oldValue, newValue));

        System.out.println("Counter: " + counter.get());
    }
}
