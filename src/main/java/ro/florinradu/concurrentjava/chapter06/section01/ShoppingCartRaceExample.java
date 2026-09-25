package ro.florinradu.concurrentjava.chapter06.section01;

// Book section: 6.1.2 Example - A Shopping Cart Updated by Two Threads
// Adapted into a standalone runnable example.

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartRaceExample {
    static final class ShoppingCart {
        private List<String> items = new ArrayList<>();

        public void addItem(String item) {
            List<String> copy = new ArrayList<>(items);
            copy.add(item);
            items = copy; // another thread may overwrite this update
        }

        public List<String> getItems() {
            return items;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ShoppingCart cart = new ShoppingCart();
        int additionsPerThread = 5_000;

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < additionsPerThread; i++) {
                cart.addItem("Laptop-" + i);
            }
        }, "cart-1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < additionsPerThread; i++) {
                cart.addItem("Mouse-" + i);
            }
        }, "cart-2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        int expected = additionsPerThread * 2;
        System.out.println("Expected item count: " + expected);
        System.out.println("Actual item count:   " + cart.getItems().size());
    }
}
