package ro.florinradu.concurrentjava.chapter05.section06;

// Book section: 5.6.2 SynchronousQueue
// Adapted into a standalone runnable example.

import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueExample {
    public static void main(String[] args) throws InterruptedException {
        SynchronousQueue<String> queue = new SynchronousQueue<>();

        Thread producer = new Thread(() -> {
            try {
                System.out.println("Producer is trying to send a message...");
                queue.put("Direct message");
                System.out.println("The message was handed off");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "producer");

        Thread consumer = new Thread(() -> {
            try {
                Thread.sleep(2000);
                String message = queue.take();
                System.out.println("Consumer received: " + message);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "consumer");

        producer.start();
        consumer.start();
    }
}
