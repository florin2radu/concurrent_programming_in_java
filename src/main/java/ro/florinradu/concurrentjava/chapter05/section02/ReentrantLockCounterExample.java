package ro.florinradu.concurrentjava.chapter05.section02;

// Book section: 5.2.1 ReentrantLock
// Adapted into a standalone runnable example.

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockCounterExample {
    private final ReentrantLock lock = new ReentrantLock();
    private int count;

    public void increment() {
        lock.lock();

        try {
            count++;
        } finally {
            lock.unlock();
        }
    }

    public int getCount() {
        lock.lock();

        try {
            return count;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockCounterExample counter = new ReentrantLockCounterExample();

        Runnable task = () -> {
            for (int i = 0; i < 100_000; i++) {
                counter.increment();
            }
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("Final value: " + counter.getCount());
    }
}
