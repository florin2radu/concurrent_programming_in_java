package ro.florinradu.concurrentjava.chapter07.section01;

// Book section: 7.1.1 Producer–Consumer Pattern
// Adapted into a standalone runnable example.

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProducerConsumerPatternExample {
    private static final String STOP = "<STOP>";

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);

        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    String item = "Item-" + i;
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
                    Thread.sleep(80);
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
