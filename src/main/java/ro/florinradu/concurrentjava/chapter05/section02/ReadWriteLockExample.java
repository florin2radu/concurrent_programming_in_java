package ro.florinradu.concurrentjava.chapter05.section02;

// Book section: 5.2.3 ReadWriteLock and ReentrantReadWriteLock
// Adapted into a standalone runnable example.

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockExample {
    private int value;
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock readLock = rwLock.readLock();
    private final Lock writeLock = rwLock.writeLock();

    public int getValue() {
        readLock.lock();

        try {
            return value;
        } finally {
            readLock.unlock();
        }
    }

    public void setValue(int value) {
        writeLock.lock();

        try {
            this.value = value;
        } finally {
            writeLock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReadWriteLockExample data = new ReadWriteLockExample();

        Thread writer = new Thread(() -> data.setValue(42), "writer");
        Thread reader = new Thread(() -> System.out.println("Read: " + data.getValue()), "reader");

        writer.start();
        reader.start();
    }
}
