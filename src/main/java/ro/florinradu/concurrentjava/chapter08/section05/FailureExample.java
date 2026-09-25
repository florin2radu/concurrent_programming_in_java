package ro.florinradu.concurrentjava.chapter08.section05;

// Book section: 8.5.8 Stop on Failure
// Adapted into a standalone runnable example.

import java.util.concurrent.StructuredTaskScope;

public class FailureExample {
    static String loadProfile() throws InterruptedException {
        Thread.sleep(1000);
        return "Profile";
    }

    static String loadPermissions() throws InterruptedException {
        Thread.sleep(500);
        throw new RuntimeException("Permission service is unavailable");
    }

    public static void main(String[] args) {
        try (var scope = StructuredTaskScope.open(StructuredTaskScope.Joiner.awaitAllSuccessfulOrThrow())) {
            var profile = scope.fork(FailureExample::loadProfile);
            var permissions = scope.fork(FailureExample::loadPermissions);

            scope.join();

            System.out.println(profile.get());
            System.out.println(permissions.get());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Execution was interrupted.");
        } catch (StructuredTaskScope.FailedException e) {
            System.out.println("Execution failed: " + e.getCause());
        }
    }
}
