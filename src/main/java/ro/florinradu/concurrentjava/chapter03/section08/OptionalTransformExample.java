package ro.florinradu.concurrentjava.chapter03.section08;

// Book section: 3.8.3 Combining and Transforming Optional Values
// Adapted into a standalone runnable example.

import java.util.Optional;

public class OptionalTransformExample {
    public static void main(String[] args) {
        Optional<String> name = Optional.of("iris");

        int length = name
                .map(String::toUpperCase)
                .filter(n -> n.length() > 3)
                .map(String::length)
                .orElse(0);

        System.out.println("Length: " + length); // 4
    }
}
