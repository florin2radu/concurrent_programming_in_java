package ro.florinradu.concurrentjava.chapter04.section07;

// Book section: 4.7.2 The volatile Keyword
// Adapted into a standalone runnable example.

public class VolatileStopFlagExample {
    private static volatile boolean running = true;

    public static void main(String[] args) throws InterruptedException {
        Thread worker = new Thread(() -> {
            long iterations = 0;

            while (running) {
                iterations++;
            }

            System.out.println("Worker stopped after " + iterations + " iterations.");
        }, "worker");

        worker.start();
        Thread.sleep(300);
        running = false; // the volatile write becomes visible to the worker
        worker.join();
    }
}
