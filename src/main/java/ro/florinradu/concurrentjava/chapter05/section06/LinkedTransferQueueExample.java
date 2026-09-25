package ro.florinradu.concurrentjava.chapter05.section06;

// Book section: 5.6.3 LinkedTransferQueue
// Adapted into a standalone runnable example.

import java.util.concurrent.LinkedTransferQueue;

public class LinkedTransferQueueExample {
    public static void main(String[] args) throws InterruptedException {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();

        Thread consumer = new Thread(() -> {
            try {
                Thread.sleep(2000);
                String message = queue.take();
                System.out.println("Consumer received: " + message);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "consumer");

        Thread producer = new Thread(() -> {
            try {
                System.out.println("Producer starts direct transfer...");
                queue.transfer("Transferred message");
                System.out.println("Producer knows the message was received.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "producer");

        consumer.start();
        producer.start();
    }
}
