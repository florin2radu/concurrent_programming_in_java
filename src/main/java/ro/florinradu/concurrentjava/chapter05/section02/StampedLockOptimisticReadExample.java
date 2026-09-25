package ro.florinradu.concurrentjava.chapter05.section02;

// Book section: 5.2.4 StampedLock
// Adapted into a standalone runnable example.

import java.util.concurrent.locks.StampedLock;

public class StampedLockOptimisticReadExample {
    private final StampedLock lock = new StampedLock();
    private int x;
    private int y;

    public void move(int dx, int dy) {
        long stamp = lock.writeLock();

        try {
            x += dx;
            y += dy;
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    public int readSum() {
        long stamp = lock.tryOptimisticRead();
        int a = x;
        int b = y;

        if (!lock.validate(stamp)) {
            stamp = lock.readLock();

            try {
                a = x;
                b = y;
            } finally {
                lock.unlockRead(stamp);
            }
        }

        return a + b;
    }

    public static void main(String[] args) {
        StampedLockOptimisticReadExample point = new StampedLockOptimisticReadExample();
        point.move(10, 20);
        System.out.println("x + y = " + point.readSum());
    }
}
