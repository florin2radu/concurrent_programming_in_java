package ro.florinradu.concurrentjava.chapter03.section09;

// Book section: 3.9 Parallel Streams
// Adapted into a standalone runnable example.

import java.util.List;
import java.util.stream.IntStream;

public class ParallelStreamExample {
    public static void main(String[] args) {
        List<Integer> numbers = IntStream.rangeClosed(1, 10)
                .boxed()
                .toList();

        int sum = numbers.parallelStream()
                .mapToInt(Integer::intValue)
                .sum();

        System.out.println("Sum: " + sum);
    }
}
