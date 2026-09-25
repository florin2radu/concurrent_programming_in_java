package ro.florinradu.concurrentjava.chapter04.section03;

// Book section: 4.3.1 Extending the Thread Class
// Adapted into a standalone runnable example.

public class ThreadInheritanceDemo {
    static final class WorkerThread extends Thread {
        private final int id;

        WorkerThread(int id) {
            super("Worker-" + id);
            this.id = id;
        }

        @Override
        public void run() {
            System.out.println(getName() + " started (id=" + id + ")");

            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

            System.out.println(getName() + " finished");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new WorkerThread(1);
        Thread t2 = new WorkerThread(2);

        t1.start();
        t2.start();

        System.out.println("Main finished");
    }
}
