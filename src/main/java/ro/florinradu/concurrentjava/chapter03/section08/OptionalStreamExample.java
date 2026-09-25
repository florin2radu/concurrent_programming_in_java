package ro.florinradu.concurrentjava.chapter03.section08;

// Book section: 3.8.4 Relationship Between Optional and the Stream API
// Adapted into a standalone runnable example.

import java.util.List;
import java.util.Optional;

public class OptionalStreamExample {
    public static void main(String[] args) {
        List<String> names = List.of("Ana", "Mara", "Iris");

        Optional<String> first = names.stream()
                .filter(name -> name.startsWith("Z"))
                .findFirst();

        System.out.println(first.orElse("No name found"));
    }
}
