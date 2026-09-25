package ro.florinradu.concurrentjava.chapter04.section03;

// Book section: 4.3.3 Using Lambda Expressions
// Adapted into a standalone runnable example.

public class LambdaThreadsDemo {
    public static void main(String[] args) throws InterruptedException {
        Runnable task = () -> {
            System.out.println("Thread: " + Thread.currentThread().getName());

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        Thread t1 = new Thread(task, "Lambda-1");
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                System.out.println("Lambda-2 step " + i);
            }
        }, "Lambda-2");

        t1.start();
        t2.start();
    }
}
