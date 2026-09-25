package ro.florinradu.concurrentjava.chapter04.section04;

// Book section: 4.4.5 join()
// Adapted into a standalone runnable example.

public class JoinExample {
    public static void main(String[] args) throws InterruptedException {
        Thread worker = new Thread(() -> {
            System.out.println("Working...");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "worker");

        worker.start();
        worker.join(); // main waits for worker

        System.out.println("Worker finished; continuing.");
    }
}
