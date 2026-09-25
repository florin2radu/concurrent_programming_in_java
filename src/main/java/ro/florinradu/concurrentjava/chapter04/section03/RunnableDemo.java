package ro.florinradu.concurrentjava.chapter04.section03;

// Book section: 4.3.2 Implementing Runnable
// Adapted into a standalone runnable example.

public class RunnableDemo {
    static final class PrintTask implements Runnable {
        private final String message;

        PrintTask(String message) {
            this.message = message;
        }

        @Override
        public void run() {
            System.out.println("Thread: " + Thread.currentThread().getName() + " | message: " + message);

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new PrintTask("Hello"), "T1");
        Thread t2 = new Thread(new PrintTask("Concurrency"), "T2");
        Thread t3 = new Thread(new PrintTask("Java"), "T3");

        t1.start();
        t2.start();
        t3.start();
    }
}
