package ro.florinradu.concurrentjava.chapter03.section04;

// Book section: 3.4 Method References and Equivalent Lambda Expressions
// Adapted into a standalone runnable example.

import java.util.List;

public class MethodReferencePipelineExample {
    public static void main(String[] args) {
        List<String> names = List.of("diana", "ioan", "mara", "iris");

        names.stream()
                .map(String::toUpperCase)
                .forEach(System.out::println);
    }
}
