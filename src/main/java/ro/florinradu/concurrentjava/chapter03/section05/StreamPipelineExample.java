package ro.florinradu.concurrentjava.chapter03.section05;

// Book section: 3.5 Introduction to the Stream API
// Adapted into a standalone runnable example.

import java.util.List;

public class StreamPipelineExample {
    public static void main(String[] args) {
        List<String> names = List.of("Diana", "Ioan", "Mara", "Iris");

        names.stream()
                .filter(name -> name.startsWith("I"))
                .map(String::toUpperCase)
                .forEach(System.out::println);
    }
}
