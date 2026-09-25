package ro.florinradu.concurrentjava.chapter07.section04;

// Book section: 7.4.3 Monitor Object Pattern
// Adapted into a standalone runnable example.

public class MonitorObjectPatternExample {
    static final class MonitorBuffer {
        private int value;
        private boolean hasValue;

        public synchronized void put(int newValue) throws InterruptedException {
            while (hasValue) {
                wait();
            }

            value = newValue;
            hasValue = true;
            notifyAll();
        }

        public synchronized int take() throws InterruptedException {
            while (!hasValue) {
                wait();
            }

            int result = value;
            hasValue = false;
            notifyAll();
            return result;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MonitorBuffer buffer = new MonitorBuffer();

        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    buffer.put(i);
                    System.out.println("Produced: " + i);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "producer");

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    System.out.println("Consumed: " + buffer.take());
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "consumer");

        producer.start();
        consumer.start();
    }
}
