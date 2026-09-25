package ro.florinradu.concurrentjava.chapter05.section05;

// Book section: 5.5.1 Synchronized Collections from Collections
// Adapted into a standalone runnable example.

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SynchronizedCollectionsExample {
    public static void main(String[] args) {
        List<String> list = Collections.synchronizedList(new ArrayList<>());
        list.add("A");
        list.add("B");
        list.add("C");

        // Iteration still requires external synchronization.
        synchronized (list) {
            for (String value : list) {
                System.out.println(value);
            }
        }
    }
}
