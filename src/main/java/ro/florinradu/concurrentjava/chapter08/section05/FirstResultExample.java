package ro.florinradu.concurrentjava.chapter08.section05;

// Book section: 8.5.9 First Successful Result
// Adapted into a standalone runnable example.

import java.util.concurrent.StructuredTaskScope;

public class FirstResultExample {
    static String server1() throws InterruptedException {
        Thread.sleep(2000);
        return "Response from server 1";
    }

    static String server2() throws InterruptedException {
        Thread.sleep(1000);
        return "Response from server 2";
    }

    static String server3() throws InterruptedException {
        Thread.sleep(1500);
        return "Response from server 3";
    }

    public static void main(String[] args) {
        try (var scope = StructuredTaskScope.open(StructuredTaskScope.Joiner.<String>anySuccessfulResultOrThrow())) {
            scope.fork(FirstResultExample::server1);
            scope.fork(FirstResultExample::server2);
            scope.fork(FirstResultExample::server3);

            String result = scope.join();
            System.out.println("First available result: " + result);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Execution was interrupted.");
        } catch (StructuredTaskScope.FailedException e) {
            System.out.println("All tasks failed: " + e.getCause());
        }
    }
}
