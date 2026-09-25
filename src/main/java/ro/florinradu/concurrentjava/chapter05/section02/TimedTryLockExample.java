package ro.florinradu.concurrentjava.chapter05.section02;

// Book section: 5.2.1 ReentrantLock
// Adapted into a standalone runnable example.

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class TimedTryLockExample {
    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();

        Thread holder = new Thread(() -> {
            lock.lock();

            try {
                System.out.println("Holder acquired the lock.");
                Thread.sleep(700);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
        }, "holder");

        Thread contender = new Thread(() -> {
            try {
                if (lock.tryLock(200, TimeUnit.MILLISECONDS)) {
                    try {
                        System.out.println("Contender acquired the lock.");
                    } finally {
                        lock.unlock();
                    }
                } else {
                    System.out.println("Contender timed out.");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "contender");

        holder.start();
        Thread.sleep(50);
        contender.start();
    }
}
