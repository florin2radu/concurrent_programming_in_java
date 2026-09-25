package ro.florinradu.concurrentjava.chapter05.section05;

// Book section: 5.5.4 BlockingQueue
// Adapted into a standalone runnable example.

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BlockingQueueExample {
    private static final String STOP = "<STOP>";

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> queue = new LinkedBlockingQueue<>();

        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    String item = "element-" + i;
                    queue.put(item);
                    System.out.println("Produced: " + item);
                }
                queue.put(STOP);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "producer");

        Thread consumer = new Thread(() -> {
            try {
                while (true) {
                    String item = queue.take();
                    if (STOP.equals(item)) {
                        return;
                    }
                    System.out.println("Consumed: " + item);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "consumer");

        producer.start();
        consumer.start();
    }
}
