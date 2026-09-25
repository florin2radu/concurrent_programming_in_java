package ro.florinradu.concurrentjava.chapter03.section08;

// Book section: 3.8.1 Creating an Optional
// Adapted into a standalone runnable example.

import java.util.Optional;

public class OptionalCreationExample {
    public static void main(String[] args) {
        Optional<String> known = Optional.of("Java");
        Optional<String> maybeNull = Optional.ofNullable(null);
        Optional<String> empty = Optional.empty();

        System.out.println(known);
        System.out.println(maybeNull);
        System.out.println(empty);
    }
}
