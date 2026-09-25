package ro.florinradu.concurrentjava.chapter03.section07;

// Book section: 3.7 Practical Stream API Examples
// Adapted into a standalone runnable example.

import java.util.List;

public class UppercaseMapExample {
    public static void main(String[] args) {
        List<String> names = List.of("Maria", "Diana", "Luca");

        List<String> upper = names.stream()
                .map(String::toUpperCase)
                .toList();

        System.out.println(upper);
    }
}
