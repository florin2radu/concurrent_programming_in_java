package ro.florinradu.concurrentjava.chapter05.section02;

// Book section: 5.2.4 StampedLock
// Adapted into a standalone runnable example.

import java.util.concurrent.locks.StampedLock;

public class StampedLockConversionExample {
    private final StampedLock lock = new StampedLock();
    private int value;

    public void incrementFromReadLock() {
        long stamp = lock.readLock();

        try {
            long writeStamp = lock.tryConvertToWriteLock(stamp);

            if (writeStamp != 0L) {
                stamp = writeStamp;
                value++;
            } else {
                lock.unlockRead(stamp);
                stamp = lock.writeLock();
                value++;
            }
        } finally {
            lock.unlock(stamp);
        }
    }

    public static void main(String[] args) {
        StampedLockConversionExample example = new StampedLockConversionExample();
        example.incrementFromReadLock();
        System.out.println("Value: " + example.value);
    }
}
