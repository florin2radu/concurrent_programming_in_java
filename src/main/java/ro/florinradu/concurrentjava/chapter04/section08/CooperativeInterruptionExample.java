package ro.florinradu.concurrentjava.chapter04.section08;

// Book section: 4.8.6 stop(), suspend(), and resume()
// Adapted into a standalone runnable example.

public class CooperativeInterruptionExample {
    public static void main(String[] args) throws InterruptedException {
        Thread worker = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    System.out.println("Working...");
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) {
                // Restore the interrupt status before leaving the task.
                Thread.currentThread().interrupt();
            }

            System.out.println("Worker stopped cooperatively.");
        }, "worker");

        worker.start();
        Thread.sleep(700);
        worker.interrupt();
    }
}
