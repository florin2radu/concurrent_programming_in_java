package ro.florinradu.concurrentjava.chapter06.section03;

// Book section: 6.3.2 Livelock Example
// Adapted into a standalone runnable example.

import java.util.concurrent.locks.ReentrantLock;

class CautiousWorker implements Runnable {
    private final ReentrantLock first;
    private final ReentrantLock second;
    private final String name;

    CautiousWorker(ReentrantLock first,
                   ReentrantLock second,
                   String name) {
        this.first = first;
        this.second = second;
        this.name = name;
    }

    @Override
    public void run() {
        while (true) {
            if (!first.tryLock()) {
                Thread.yield();
                continue;
            }

            try {
                if (second.tryLock()) {
                    try {
                        System.out.println(name + " acquired both locks.");
                        return;
                    } finally {
                        second.unlock();
                    }
                }

                System.out.println(name + " yielded and will retry.");
            } finally {
                first.unlock();
            }

            Thread.yield();
        }
    }
}

public class LivelockDemo {
    public static void main(String[] args) {
        ReentrantLock lockA = new ReentrantLock();
        ReentrantLock lockB = new ReentrantLock();

        Thread t1 = new Thread(new CautiousWorker(lockA, lockB, "Worker-1"));
        Thread t2 = new Thread(new CautiousWorker(lockB, lockA, "Worker-2"));

        t1.start();
        t2.start();
    }
}