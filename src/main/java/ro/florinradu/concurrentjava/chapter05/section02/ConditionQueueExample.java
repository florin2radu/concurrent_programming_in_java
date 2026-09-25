package ro.florinradu.concurrentjava.chapter05.section02;

// Book section: 5.2.2 Condition and await()/signal()
// Adapted into a standalone runnable example.

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionQueueExample {
    static final class MessageQueue {
        private final ReentrantLock lock = new ReentrantLock();
        private final Condition notEmpty = lock.newCondition();
        private final Queue<String> queue = new LinkedList<>();

        public String take() throws InterruptedException {
            lock.lock();
            try {
                while (queue.isEmpty()) {
                    notEmpty.await();
                }

                return queue.poll();
            } finally {
                lock.unlock();
            }
        }

        public void put(String value) {
            lock.lock();

            try {
                queue.add(value);
                notEmpty.signal();
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MessageQueue queue = new MessageQueue();

        Thread consumer = new Thread(() -> {
            try {
                System.out.println("Received: " + queue.take());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "consumer");

        consumer.start();
        Thread.sleep(200);
        queue.put("hello");
        consumer.join();
    }
}
