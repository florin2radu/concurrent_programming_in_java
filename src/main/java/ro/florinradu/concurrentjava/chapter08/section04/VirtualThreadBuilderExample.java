package ro.florinradu.concurrentjava.chapter08.section04;

// Book section: 8.4.2 Explicit Creation with Thread.ofVirtual()
// Adapted into a standalone runnable example.

public class VirtualThreadBuilderExample {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = Thread.ofVirtual()
                .name("virtual-thread-1")
                .start(() -> System.out.println("Running in " + Thread.currentThread()));

        thread.join();
    }
}
