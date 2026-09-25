package ro.florinradu.concurrentjava.chapter04.section04;

// Book section: 4.4.4 sleep(long millis)
// Adapted into a standalone runnable example.

public class SleepExample {
    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("Message: " + i);
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }, "sleeper").start();
    }
}
