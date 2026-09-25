package ro.florinradu.concurrentjava.chapter02.section03;

// Book section: 2.3 Process vs. Thread
// Adapted into a standalone runnable example.

public class HelloThread extends Thread {
    @Override
    public void run() {
        System.out.println("Hello from thread: " +
                Thread.currentThread().getName());
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new HelloThread();
        Thread t2 = new HelloThread();

        t1.start();
        t2.start();
    }
}
