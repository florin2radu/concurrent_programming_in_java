package ro.florinradu.concurrentjava.chapter06.section05;

// Book section: 6.5.2 Uncontrolled Busy-Waiting
// Adapted into a standalone runnable example.

public class BusyWaitingExample {
    private static volatile boolean ready;

    public static void main(String[] args) throws InterruptedException {
        Thread busyWaitingThread = new Thread(() -> {
            while (!ready) {
                // keep checking
            }
            System.out.println("Ready!");
        }, "busy-thread");

        busyWaitingThread.start();
        Thread.sleep(2000);
        ready = true;
        busyWaitingThread.join();

        System.out.println("A blocking primitive is usually preferable to this busy loop.");
    }
}
