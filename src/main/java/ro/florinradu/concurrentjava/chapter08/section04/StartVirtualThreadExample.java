package ro.florinradu.concurrentjava.chapter08.section04;

// Book section: 8.4.1 Direct Creation with Thread.startVirtualThread()
// Adapted into a standalone runnable example.

public class StartVirtualThreadExample {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = Thread.startVirtualThread(() ->
                System.out.println("Running in a virtual thread: " + Thread.currentThread()));

        thread.join();
    }
}
