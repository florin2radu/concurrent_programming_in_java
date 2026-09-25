package ro.florinradu.concurrentjava.chapter04.section03;

// Book section: 4.3.1 Extending the Thread Class
// Adapted into a standalone runnable example.

public class RunVsStartDemo {
    static final class WorkerThread extends Thread {
        WorkerThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            System.out.println(getName() + " executes on " + Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t = new WorkerThread("Worker-run");

        System.out.println("Direct run() call:");
        t.run(); // no new thread is created; it runs on the main thread

        System.out.println("start() call:");
        Thread started = new WorkerThread("Worker-start");
        started.start();
    }
}
