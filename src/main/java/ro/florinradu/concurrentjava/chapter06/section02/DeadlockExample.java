package ro.florinradu.concurrentjava.chapter06.section02;

// Book section: 6.2.2 Example: Deadlock Caused by Inconsistent Lock Ordering
// Adapted into a standalone runnable example.

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.Arrays;

public class DeadlockExample {
    public static void main(String[] args) throws InterruptedException {
        Object lockA = new Object();
        Object lockB = new Object();

        Thread t1 = new Thread(() -> {
            synchronized (lockA) {
                sleepQuietly(100);
                synchronized (lockB) {
                    System.out.println("T1 acquired A and B");
                }
            }
        }, "deadlock-t1");

        Thread t2 = new Thread(() -> {
            synchronized (lockB) {
                sleepQuietly(100);
                synchronized (lockA) {
                    System.out.println("T2 acquired B and A");
                }
            }
        }, "deadlock-t2");

        // Daemon threads let this demonstration terminate after detection.
        // If you uncomment the next line the program will end,
        // otherwise you have to close it manually since it's kept alive by the 2 stuck threads.
//        t1.setDaemon(true);
//        t2.setDaemon(true);
        t1.start();
        t2.start();

        Thread.sleep(500);

        // A cool way of detecting deadlocks programmatically
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        long[] ids = bean.findDeadlockedThreads();
        if (ids == null) {
            System.out.println("No deadlock detected in this run.");
        } else {
            System.out.println("Deadlock detected among thread IDs: " +
                    Arrays.toString(ids));
        }
    }

    private static void sleepQuietly(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
