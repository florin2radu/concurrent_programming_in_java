package ro.florinradu.concurrentjava.chapter08.section05;

// Book section: 8.5.14 The Importance of Interruption
// Adapted into a standalone runnable example.

public class InterruptionCooperationExample {
    public static void main(String[] args) throws InterruptedException {
        Thread worker = Thread.startVirtualThread(() -> {
            try {
                Thread.sleep(60_000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Subtask observed interruption and stopped.");
            }
        });

        Thread.sleep(300);
        worker.interrupt();
        worker.join();
    }
}
