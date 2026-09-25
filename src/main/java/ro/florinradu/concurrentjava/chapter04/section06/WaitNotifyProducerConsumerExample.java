package ro.florinradu.concurrentjava.chapter04.section06;

// Book section: 4.6.3 Producer-Consumer Pattern with wait()/notify()
// Adapted into a standalone runnable example.

public class WaitNotifyProducerConsumerExample {
    static final class Buffer {
        private Integer value;

        public synchronized void produce(int newValue) throws InterruptedException {
            while (value != null) {
                wait();
            }

            value = newValue;
            System.out.println("Produced: " + newValue);
            notifyAll();
        }

        public synchronized int consume() throws InterruptedException {
            while (value == null) {
                wait();
            }
            int result = value;
            value = null;
            System.out.println("Consumed: " + result);
            notifyAll();
            return result;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Buffer buffer = new Buffer();
        int itemCount = 8;

        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < itemCount; i++) {
                    buffer.produce(i);
                    Thread.sleep(80);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "producer");

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < itemCount; i++) {
                    buffer.consume();
                    Thread.sleep(120);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "consumer");

        producer.start();
        consumer.start();
    }
}
