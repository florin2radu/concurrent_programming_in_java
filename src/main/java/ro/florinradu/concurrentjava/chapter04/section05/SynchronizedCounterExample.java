package ro.florinradu.concurrentjava.chapter04.section05;

// Book section: 4.5.1 Synchronized Blocks
// Adapted into a standalone runnable example.

public class SynchronizedCounterExample {
    static final class Counter {
        private int value;

        public void increment() {
            synchronized (this) {
                value++;
            }
        }

        public int getValue() {
            synchronized (this) {
                return value;
            }
        }
    }

    static final class IncrementerThread extends Thread {
        private final Counter counter;
        private final int increments;

        IncrementerThread(Counter counter, int increments, String name) {
            super(name);
            this.counter = counter;
            this.increments = increments;
        }

        @Override
        public void run() {
            for (int i = 0; i < increments; i++) {
                counter.increment();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();

        Thread t1 = new IncrementerThread(counter, 10_000, "incrementer-1");
        Thread t2 = new IncrementerThread(counter, 10_000, "incrementer-2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("Final value: " + counter.getValue());
    }
}
