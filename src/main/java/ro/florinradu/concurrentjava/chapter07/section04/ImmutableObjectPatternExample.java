package ro.florinradu.concurrentjava.chapter07.section04;

// Book section: 7.4.1 Immutable Object Pattern
// Adapted into a standalone runnable example.

public class ImmutableObjectPatternExample {
    static final class ImmutablePoint {
        private final int x;
        private final int y;

        ImmutablePoint(int x, int y) {
            this.x = x;
            this.y = y;
        }

        int x() {
            return x;
        }

        int y() {
            return y;
        }

        @Override
        public String toString() {
            return "ImmutablePoint[x=" + x + ", y=" + y + "]";
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ImmutablePoint point = new ImmutablePoint(10, 20); // this object cannot be changed after creation

        Runnable reader = () -> System.out.println(Thread.currentThread().getName() + " -> " + point);

        Thread t1 = new Thread(reader, "reader-1");
        Thread t2 = new Thread(reader, "reader-2");
        t1.start();
        t2.start();
    }
}
