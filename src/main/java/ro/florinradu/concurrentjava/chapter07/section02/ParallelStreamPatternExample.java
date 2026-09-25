package ro.florinradu.concurrentjava.chapter07.section02;

// Book section: 7.2.3 Parallel Processing Pattern - Parallel Streams
// Adapted into a standalone runnable example.

import java.util.List;
import java.util.stream.IntStream;

public class ParallelStreamPatternExample {
    public static void main(String[] args) {
        List<Integer> numbers = IntStream.rangeClosed(1, 1_000_000)
                .boxed()
                .toList();

        long sum = numbers.parallelStream()
                .mapToLong(Integer::longValue)
                .sum();

        System.out.println("Sum: " + sum);
    }
}
