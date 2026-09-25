package ro.florinradu.concurrentjava.chapter08.section05;

// Book section: 8.5.10 Collecting All Results
// Adapted into a standalone runnable example.

import java.util.List;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.Subtask;

public class CollectResultsExample {
    static String service(String name, int delay) throws InterruptedException {
        Thread.sleep(delay);
        return "Response from " + name;
    }

    static List<String> loadAll() throws InterruptedException {
        try (var scope = StructuredTaskScope.open(StructuredTaskScope.Joiner.<String>allSuccessfulOrThrow())) {
            scope.fork(() -> service("service 1", 1000));
            scope.fork(() -> service("service 2", 1200));
            scope.fork(() -> service("service 3", 800));

            return scope.join()
                    .map(Subtask::get)
                    .toList();
        }
    }

    public static void main(String[] args) {
        try {
            List<String> results = loadAll();
            results.forEach(System.out::println);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Execution was interrupted.");
        } catch (StructuredTaskScope.FailedException e) {
            System.out.println("At least one task failed: " + e.getCause());
        }
    }
}
