package ro.florinradu.concurrentjava.chapter07.section01;

// Book section: 7.1.3 Guarded Suspension Pattern
// Adapted into a standalone runnable example.
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class GuardedBox {
    private String value;
    private final Lock lock = new ReentrantLock();
    private final Condition stateChanged = lock.newCondition();

    public void put(String v) throws InterruptedException {
        lock.lock();

        try {
            while (value != null) {
                stateChanged.await();
            }

            value = v;
            stateChanged.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public String take() throws InterruptedException {
        lock.lock();

        try {
            while (value == null) {
                stateChanged.await();
            }

            String result = value;
            value = null;
            stateChanged.signalAll();
            return result;
        } finally {
            lock.unlock();
        }
    }
}

public class GuardedSuspensionPatternExample {
    public static void main(String[] args) {
        GuardedBox box = new GuardedBox();

        new Thread(() -> { // producer thread
            try {
                for (int i = 0; i < 5; i++) {
                    String item = "Item " + i;
                    box.put(item);
                    System.out.println("Produced: " + item);
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();

        new Thread(() -> { // consumer thread
            try {
                for (int i = 0; i < 5; i++) {
                    String item = box.take();
                    System.out.println("Consumed: " + item);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
}
