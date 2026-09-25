package ro.florinradu.concurrentjava.chapter05.section06;

// Book section: 5.6.1 Exchanger
// Adapted into a standalone runnable example.

import java.util.concurrent.Exchanger;

public class ExchangerExample {
    public static void main(String[] args) throws InterruptedException {
        Exchanger<String> exchanger = new Exchanger<>();

        Thread party1 = new Thread(() -> {
            try {
                Thread.sleep(2000); // simulate production time
                String data = "Party1 data";
                String response = exchanger.exchange(data);
                System.out.println("Party1 received: " + response);
            } catch (InterruptedException ignored) {
            }
        });

        Thread party2 = new Thread(() -> {
            try {
                Thread.sleep(1000); // simulate production time
                String data = "Party2 data";
                String response = exchanger.exchange(data);
                System.out.println("Party2 received: " + response);
            } catch (InterruptedException ignored) {
            }
        });

        party1.start();
        party2.start();
    }
}
