package ro.florinradu.concurrentjava.chapter08.section04;

// Book section: 8.4.3 Creating a ThreadFactory for Virtual Threads
// Adapted into a standalone runnable example.

import java.util.concurrent.ThreadFactory;

public class VirtualThreadFactoryExample {
    public static void main(String[] args) throws InterruptedException {
        ThreadFactory factory = Thread.ofVirtual()
                .name("factory-vt-", 0)
                .factory();

        Thread thread = factory.newThread(() ->
                System.out.println("Created through a factory: " + Thread.currentThread()));

        thread.start();
        thread.join();
    }
}
