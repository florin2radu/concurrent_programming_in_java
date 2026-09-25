package ro.florinradu.concurrentjava.chapter04.section08;

// Book section: 4.8.5 sleep() vs. yield()
// Adapted into a standalone runnable example.

public class YieldDemo {
    static final class BusyThread extends Thread {
        private volatile boolean running = true;
        private long counter;
        private final boolean useYield;

        BusyThread(String name, boolean useYield) {
            super(name);
            this.useYield = useYield;
        }

        @Override
        public void run() {
            while (running) {
                counter++;
                if (useYield) {
                    Thread.yield(); // only a scheduler hint
                }
            }
        }

        void stopRunning() {
            running = false;
        }

        long counter() {
            return counter;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        BusyThread withYield = new BusyThread("With-Yield", true);
        BusyThread noYield = new BusyThread("No-Yield", false);

        withYield.start();
        noYield.start();

        Thread.sleep(1_000);
        withYield.stopRunning();
        noYield.stopRunning();

        withYield.join();
        noYield.join();

        System.out.println(withYield.getName() + ": " + withYield.counter());
        System.out.println(noYield.getName() + ": " + noYield.counter());
        System.out.println("Results are platform- and scheduler-dependent.");
    }
}
