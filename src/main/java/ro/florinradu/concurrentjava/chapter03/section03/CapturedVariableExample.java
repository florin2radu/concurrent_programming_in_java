package ro.florinradu.concurrentjava.chapter03.section03;

// Book section: 3.3 Lambda Expressions and Captured Variables
// Adapted into a standalone runnable example.

public class CapturedVariableExample {
    public static void main(String[] args) {
        int x = 10; // effectively final

        Runnable task = () -> System.out.println("Captured x = " + x);
        task.run();

        // Reassigning x here would make the lambda fail to compile.
        // x = 20;  // compilation error if you uncomment this line
    }
}
