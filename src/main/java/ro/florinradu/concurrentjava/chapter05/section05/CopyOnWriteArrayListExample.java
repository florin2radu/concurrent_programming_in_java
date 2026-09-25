package ro.florinradu.concurrentjava.chapter05.section05;

// Book section: 5.5.2 CopyOnWriteArrayList and CopyOnWriteArraySet
// Adapted into a standalone runnable example.

import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListExample {
    public static void main(String[] args) {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("A");
        list.add("B");

        for (String value : list) {
            System.out.println(value);

            if ("A".equals(value)) {
                list.add("C"); // safe; iterator still sees the old snapshot
            }
        }

        System.out.println("After iteration: " + list);
    }
}
