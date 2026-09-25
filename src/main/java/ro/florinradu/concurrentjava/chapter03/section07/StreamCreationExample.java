package ro.florinradu.concurrentjava.chapter03.section07;

// Book section: 3.7 Practical Stream API Examples
// Adapted into a standalone runnable example.

import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamCreationExample {
    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4};
        IntStream intStream = Arrays.stream(array);

        Stream<String> stringStream1 = Stream.of("abc", "efg", "hij");

        List<Character> list = List.of('a', 'b', 'c');
        Stream<Character> characterStream = list.stream();

        Stream<String> stringStream2 =
                List.of("abc", "efg", "hij").stream();

        Stream<LocalDate> localDateStream =
                Set.of(LocalDate.now(), LocalDate.of(2021, 1, 1)).stream();

        Queue<Boolean> queue = new ArrayDeque<>(List.of(true, false));
        Stream<Boolean> booleanStream = queue.stream();

        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("one", 1);
        map.put("two", 2);
        Stream<Map.Entry<String, Integer>> entryStream = map.entrySet().stream();

        // Terminal operations are added so every stream is actually consumed.
        System.out.println("int sum: " + intStream.sum());
        System.out.println("strings: " + stringStream1.count());
        System.out.println("characters: " + characterStream.count());
        System.out.println("list strings: " + stringStream2.count());
        System.out.println("dates: " + localDateStream.count());
        System.out.println("booleans: " + booleanStream.count());
        System.out.println("map entries: " + entryStream.count());
    }
}
