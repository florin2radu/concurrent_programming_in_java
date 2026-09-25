package ro.florinradu.concurrentjava.chapter04.section05;

// Book section: 4.5.3 Static Synchronized Methods
// Adapted into a standalone runnable example.

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

public class StaticSynchronizedLoggerDemo {
    static final class MyLogger {
        private static final String FILE_NAME = "log.txt";

        private MyLogger() {
        }

        public static synchronized void log(String message) {
            try (FileWriter fw = new FileWriter(FILE_NAME, true);
                 PrintWriter pw = new PrintWriter(fw)) {
                pw.println(Thread.currentThread().getName() + ": " + message);
            } catch (IOException e) {
                throw new IllegalStateException("Could not write the log", e);
            }
        }
    }

    static final class Worker extends Thread {
        Worker(String name) {
            super(name);
        }

        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                MyLogger.log("Message " + i);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Files.deleteIfExists(Path.of("log.txt"));

        Thread w1 = new Worker("Thread-1");
        Thread w2 = new Worker("Thread-2");
        Thread w3 = new Worker("Thread-3");

        w1.start();
        w2.start();
        w3.start();

        w1.join();
        w2.join();
        w3.join();

        System.out.println(Files.readString(Path.of("log.txt")));
    }
}
