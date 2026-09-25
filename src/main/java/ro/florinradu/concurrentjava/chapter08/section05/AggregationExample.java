package ro.florinradu.concurrentjava.chapter08.section05;

// Book section: 8.5.13 Complete Example: Aggregating Data from Several Sources
// Adapted into a standalone runnable example.

import java.util.concurrent.StructuredTaskScope;

public class AggregationExample {
    static String loadProfile() throws InterruptedException {
        Thread.sleep(1000);
        return "Profile";
    }

    static String loadOrders() throws InterruptedException {
        Thread.sleep(1500);
        return "Orders";
    }

    static String loadRecommendations() throws InterruptedException {
        Thread.sleep(1200);
        return "Recommendations";
    }

    public static void main(String[] args) {
        try (var scope = StructuredTaskScope.open(StructuredTaskScope.Joiner.awaitAllSuccessfulOrThrow())) {
            var profile =
                    scope.fork(
                            AggregationExample::loadProfile);
            var orders =
                    scope.fork(
                            AggregationExample::loadOrders);
            var recommendations =
                    scope.fork(
                            AggregationExample
                                    ::loadRecommendations);

            scope.join();

            System.out.println("=== User dashboard ===");
            System.out.println(profile.get());
            System.out.println(orders.get());
            System.out.println(recommendations.get());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Execution was interrupted.");
        } catch (StructuredTaskScope.FailedException e) {
            System.out.println("The dashboard could not be built: " + e.getCause());
        }
    }
}
