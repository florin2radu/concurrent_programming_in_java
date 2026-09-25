package ro.florinradu.concurrentjava.chapter08.section05;

// Book section: 8.5.6 Practical Example: Calling Two Services in Parallel
// Adapted into a standalone runnable example.

import java.util.concurrent.StructuredTaskScope;

public class ProductPageService {
    static String loadProduct() throws InterruptedException {
        Thread.sleep(1000);
        return "Product details";
    }

    static String loadInventory() throws InterruptedException {
        Thread.sleep(1200);
        return "In stock";
    }

    public static void main(String[] args) throws Exception {
        try (var scope = StructuredTaskScope.open()) {
            var productTask = scope.fork(ProductPageService::loadProduct);

            var inventoryTask = scope.fork(ProductPageService::loadInventory);

            scope.join();

            String product = productTask.get();
            String inventory = inventoryTask.get();

            System.out.println("=== Product page ===");
            System.out.println(product);
            System.out.println(inventory);
        }
    }
}
