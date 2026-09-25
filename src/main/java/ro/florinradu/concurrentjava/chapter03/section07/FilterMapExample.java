package ro.florinradu.concurrentjava.chapter03.section07;

// Book section: 3.7 Practical Stream API Examples
// Adapted into a standalone runnable example.

import java.util.List;

public class FilterMapExample {
    public static void main(String[] args) {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6);

        List<Integer> evenSquares = numbers.stream()
                .filter(n -> n % 2 == 0)
                .map(n -> n * n)
                .toList();

        System.out.println(evenSquares); // [4, 16, 36]
    }
}
