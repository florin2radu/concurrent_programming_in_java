package ro.florinradu.concurrentjava.chapter05.section07;

// Book section: 5.7.4 Types of Thread Pools
// Adapted into a standalone runnable example.

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorExample {
    public static void main(String[] args) throws InterruptedException {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

        try {
            ScheduledFuture<?> periodic = scheduler.scheduleAtFixedRate(
                    () -> System.out.println("Tick"),
                    100,
                    250,
                    TimeUnit.MILLISECONDS);

            Thread.sleep(900);
            periodic.cancel(false);
        } finally {
            scheduler.shutdown();
            scheduler.awaitTermination(2, TimeUnit.SECONDS);
        }
    }
}
