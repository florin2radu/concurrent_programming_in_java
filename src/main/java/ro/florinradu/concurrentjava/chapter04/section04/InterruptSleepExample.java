package ro.florinradu.concurrentjava.chapter04.section04;

// Book section: 4.4.6 interrupt() and isInterrupted()
// Adapted into a standalone runnable example.

public class InterruptSleepExample {
    public static void main(String[] args) throws InterruptedException {
        Thread worker = new Thread(() -> {
            System.out.println("Worker is going to sleep.");

            try {
                Thread.sleep(60_000);
            } catch (InterruptedException e) {
                System.out.println("Sleep was interrupted.");
                Thread.currentThread().interrupt();
            }
        }, "sleeping-worker");

        worker.start();
        Thread.sleep(1000);
        worker.interrupt();
    }
}
