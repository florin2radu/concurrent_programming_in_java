package ro.florinradu.concurrentjava.chapter04.section04;

// Book section: 4.4.3 setPriority(int) and getPriority()
// Adapted into a standalone runnable example.

public class ThreadPriorityExample {
    public static void main(String[] args) throws InterruptedException {
        Runnable task = () -> {
            for (int i = 1; i <= 5; i++) {
                Thread current = Thread.currentThread();
                System.out.println(current.getName() + " (priority: " + current.getPriority() + ") -> " + i);
            }
        };

        Thread low = new Thread(task, "Low");
        Thread high = new Thread(task, "High");

        low.setPriority(Thread.MIN_PRIORITY);
        high.setPriority(Thread.MAX_PRIORITY);

        low.start();
        high.start();

        // The output order is deliberately not asserted: priorities do not
        // provide a portable ordering guarantee.
    }
}
