package ro.florinradu.concurrentjava.chapter05.section05;

// Book section: 5.5.3 ConcurrentHashMap
// Adapted into a standalone runnable example.

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapExample {
    public static void main(String[] args) {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.putIfAbsent("key", 1);
        map.computeIfAbsent("user", key -> 10);
        map.merge("errors", 1, Integer::sum);
        map.merge("errors", 1, Integer::sum);

        map.forEach(1, (key, value) ->
                System.out.println(key + " -> " + value));

        String key = map.search(1,
                (k, v) -> v > 1 ? k : null);
        System.out.println("First key with value > 1: " + key);
    }
}
