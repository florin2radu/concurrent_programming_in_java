package ro.florinradu.concurrentjava.chapter02.section05;

// Book section: 2.5.1 Race Conditions
// Adapted into a standalone runnable example.

public class RaceConditionCounterExample {
    static final class Counter {
        private int value;

        public void increment() {
            value++; // read-modify-write: not atomic
        }

        public int getValue() {
            return value;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();
        int incrementsPerThread = 900_000;

        Runnable task = () -> {
            for (int i = 0; i < incrementsPerThread; i++) {
                counter.increment();
            }
        };

        Thread t1 = new Thread(task, "counter-1");
        Thread t2 = new Thread(task, "counter-2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        int expected = incrementsPerThread * 2;
        System.out.println("Expected: " + expected);
        System.out.println("Actual:   " + counter.getValue());
        System.out.println(
                "A lower value demonstrates lost updates caused by the race.");
    }
}
