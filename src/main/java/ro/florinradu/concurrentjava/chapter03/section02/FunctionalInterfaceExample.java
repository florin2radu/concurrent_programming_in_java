package ro.florinradu.concurrentjava.chapter03.section02;

// Book section: 3.2 Functional Interfaces
// Adapted into a standalone runnable example.

public class FunctionalInterfaceExample {
    @FunctionalInterface
    interface Greeting {
        void sayHello(String name);
    }

    public static void main(String[] args) {
        Greeting greeting = name -> System.out.println("Hello, " + name + "!");

        greeting.sayHello("Iris");
    }
}
