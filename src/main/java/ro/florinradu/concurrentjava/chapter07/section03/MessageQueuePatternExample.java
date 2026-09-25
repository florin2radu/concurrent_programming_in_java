package ro.florinradu.concurrentjava.chapter07.section03;

// Book section: 7.3.1 Message Queue Pattern
// Adapted into a standalone runnable example.

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageQueuePatternExample {
    record OrderEvent(long orderId, String status) {
    }

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<OrderEvent> queue = new LinkedBlockingQueue<>();

        Thread orderService = new Thread(() -> {
            try {
                queue.put(new OrderEvent(101, "CREATED"));
                queue.put(new OrderEvent(102, "PAID"));
                queue.put(new OrderEvent(103, "SHIPPED"));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread notificationService = new Thread(() -> {
            try {
                for (int i = 0; i < 3; i++) {
                    OrderEvent event = queue.take();
                    System.out.println("Notification for order " + event.orderId() + ": " + event.status());
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        orderService.start();
        notificationService.start();
    }
}
