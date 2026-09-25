package ro.florinradu.concurrentjava.chapter05.section04;

// Book section: 5.4.1 AtomicStampedReference
// Adapted into a standalone runnable example.

import java.util.concurrent.atomic.AtomicStampedReference;

public class AtomicStampedReferenceExample {
    public static void main(String[] args) {
        AtomicStampedReference<String> ref =
                new AtomicStampedReference<>("A", 0);

        int[] stampHolder = new int[1];
        String value = ref.get(stampHolder);
        int stamp = stampHolder[0];

        boolean updated = ref.compareAndSet(
                value, "B", stamp, stamp + 1);

        System.out.println("Updated: " + updated);
        System.out.println("Value: " + ref.getReference());
        System.out.println("Stamp: " + ref.getStamp());
    }
}
