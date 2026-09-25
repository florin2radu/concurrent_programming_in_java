package ro.florinradu.concurrentjava.chapter05.section05;

// Book section: 5.5.5 ConcurrentLinkedQueue
// Adapted into a standalone runnable example.

import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentLinkedQueueExample {
    public static void main(String[] args) throws InterruptedException {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();

        Thread producer = new Thread(() -> {
            queue.add("A");
            queue.add("B");
        });

        producer.start();
        producer.join();

        String value;
        while ((value = queue.poll()) != null) {
            System.out.println("Polled: " + value);
        }
    }
}
