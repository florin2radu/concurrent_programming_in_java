package ro.florinradu.concurrentjava.chapter06.section04;

// Book section: 6.4.2 Example
// Adapted into a standalone runnable example.

public class StarvationDemo {
    private static volatile boolean running = true;

    static final class CountingTask implements Runnable {
        private long count;

        @Override
        public void run() {
            while (running) {
                count++;
            }
        }

        long count() {
            return count;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CountingTask highTask = new CountingTask();
        CountingTask lowTask = new CountingTask();

        Thread high = new Thread(highTask, "High-Priority");
        Thread low = new Thread(lowTask, "Low-Priority");

        high.setPriority(Thread.MAX_PRIORITY);
        low.setPriority(Thread.MIN_PRIORITY);

        high.start();
        low.start();

        Thread.sleep(1_000);
        running = false;

        high.join();
        low.join();

        System.out.println("High-priority iterations: " + highTask.count());
        System.out.println("Low-priority iterations:  " + lowTask.count());
        System.out.println("Thread priorities are hints; results vary by operating system and JVM.");
    }
}
