package ro.florinradu.concurrentjava.chapter08.section05;

// Book section: 8.5.5 StructuredTaskScope in JDK 25
// Adapted into a standalone runnable example.
import java.util.concurrent.StructuredTaskScope;

public class StructuredTaskScopeBasicExample {
    public static void main(String[] args) throws Exception {
        try (var scope = StructuredTaskScope.open()) {
            var t1 = scope.fork(() -> {
                Thread.sleep(1000);
                return "Result 1";
            });

            var t2 = scope.fork(() -> {
                Thread.sleep(1500);
                return "Result 2";
            });

            scope.join();

            System.out.println(t1.get());
            System.out.println(t2.get());
        }
    }
}
