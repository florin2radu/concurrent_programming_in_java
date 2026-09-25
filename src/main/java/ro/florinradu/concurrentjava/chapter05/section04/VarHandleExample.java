package ro.florinradu.concurrentjava.chapter05.section04;

// Book section: 5.4.2 VarHandle - Modern Atomic Access to Fields
// Adapted into a standalone runnable example.

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

public class VarHandleExample {
    static final class Counter {
        volatile int value;
    }

    public static void main(String[] args) throws ReflectiveOperationException {
        Counter counter = new Counter();

        VarHandle handle = MethodHandles.lookup()
                .findVarHandle(Counter.class, "value", int.class);

        handle.getAndAdd(counter, 1);
        handle.getAndAdd(counter, 1);
        System.out.println("Counter value: " + counter.value);
    }
}
