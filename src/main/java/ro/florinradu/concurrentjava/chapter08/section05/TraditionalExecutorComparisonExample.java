package ro.florinradu.concurrentjava.chapter08.section05;

// Book section: 8.5.4 The Problem with the Traditional Model
// Adapted into a standalone runnable example.

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TraditionalExecutorComparisonExample {
    static String loadProfile() {
        return "User profile";
    }

    static String loadOrders() {
        return "User orders";
    }

    public static void main(String[] args) throws Exception {
        try (ExecutorService pool = Executors.newFixedThreadPool(2)) {
            Future<String> profile = pool.submit(TraditionalExecutorComparisonExample::loadProfile);
            Future<String> orders = pool.submit(TraditionalExecutorComparisonExample::loadOrders);

            String p = profile.get();
            String o = orders.get();

            System.out.println(p);
            System.out.println(o);
        }
    }
}
