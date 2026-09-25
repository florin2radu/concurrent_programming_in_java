package ro.florinradu.concurrentjava.chapter07.section04;

// Book section: 7.4.2 Thread-Local Storage Pattern
// Adapted into a standalone runnable example.

import java.text.SimpleDateFormat;
import java.util.Date;

public class ThreadLocalStoragePatternExample {
    private static final ThreadLocal<SimpleDateFormat> FORMATTER =
            ThreadLocal.withInitial(() -> new SimpleDateFormat("dd-MM-yyyy"));

    public static String formatDate(Date date) {
        return FORMATTER.get().format(date);
    }

    public static void main(String[] args) {
        Runnable task = () -> {
            for (int i = 0; i < 3; i++) {
                String formatted = formatDate(new Date());
                System.out.println(Thread.currentThread().getName() + " -> " + formatted);

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        };

        new Thread(task, "Thread-1").start();
        new Thread(task, "Thread-2").start();
        new Thread(task, "Thread-3").start();
    }
}
