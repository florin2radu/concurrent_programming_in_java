package ro.florinradu.concurrentjava.chapter03.section07;

// Book section: 3.7 Practical Stream API Examples
// Adapted into a standalone runnable example.

import java.util.List;

public class ReduceExample {
    public static void main(String[] args) {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6);

        int sum = numbers.stream()
                .reduce(0, (a, b) -> a + b);

        System.out.println("Sum: " + sum); // 21
    }
}
