package ro.florinradu.concurrentjava.chapter04.section02;

// Book section: 4.2.2 User Threads and Daemon Threads
// Adapted into a standalone runnable example.

public class DaemonThreadExample {
    public static void main(String[] args) throws InterruptedException {
        Thread daemon = new Thread(() -> {
            try {
                while (true) {
                    System.out.println("Daemon thread is running...");
                    Thread.sleep(300);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "background-daemon");

        daemon.setDaemon(true);
        daemon.start();

        System.out.println("Is daemon? " + daemon.isDaemon());
        Thread.sleep(900);
        System.out.println("Main thread is finishing.");
        // Because the only remaining thread is a daemon, the JVM may now exit.
    }
}
