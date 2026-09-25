package ro.florinradu.concurrentjava.chapter02.section06;

// Book section: 2.6.1. Thread-Based Model
// Adapted into a standalone runnable example.

public class ThreadBasedModelExample {
    public static void main(String[] args) {
        // functional style thread implementation
        new Thread(() -> System.out.println("Executed in a separate thread")).start();

        // anonymous class thread implementation
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Executed in a separate thread");
                    }
                }
        ).start();
    }
}
