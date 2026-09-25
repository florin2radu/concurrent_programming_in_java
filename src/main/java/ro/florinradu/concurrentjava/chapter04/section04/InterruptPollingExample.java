package ro.florinradu.concurrentjava.chapter04.section04;

// Book section: 4.4.6 interrupt() and isInterrupted()
// Adapted into a standalone runnable example.

public class InterruptPollingExample {
    public static void main(String[] args) throws InterruptedException {
        Thread worker = new Thread(() -> {
            long iterations = 0;

            while (!Thread.currentThread().isInterrupted()) {
                iterations++;
            }

            System.out.println("Stopped gracefully after " + iterations + " iterations.");
        }, "polling-worker");

        worker.start();
        Thread.sleep(300);
        worker.interrupt();
    }
}
