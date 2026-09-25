package ro.florinradu.concurrentjava.chapter04.section04;

// Book section: 4.4.1 currentThread()
// Adapted into a standalone runnable example.

public class CurrentThreadExample {
    private static void identifyThread() {
        Thread current = Thread.currentThread();
        System.out.println("Current thread: " + current.getName());
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(CurrentThreadExample::identifyThread, "Worker-1");
        Thread t2 = new Thread(CurrentThreadExample::identifyThread, "Worker-2");

        identifyThread(); // runs on main
        t1.start();
        t2.start();
    }
}
