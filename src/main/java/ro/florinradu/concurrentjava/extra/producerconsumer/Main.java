package ro.florinradu.concurrentjava.extra.producerconsumer;

import javax.swing.*;
import java.io.File;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Real-world producer-consumer example.
 * <p>
 * The application discovers text files in the {@code samplefiles} directory and converts them
 * to PDF files using a producer-consumer architecture.
 * Generated PDFs are stored in the same directory.
 */
public class Main {
    public static final int WORKERS = 2;

    public static void main(String[] args) {
        ArrayBlockingQueue<File> arrayBlockingQueue = new ArrayBlockingQueue<>(5000);

        SwingUtilities.invokeLater(() -> {
            Win win = new Win(arrayBlockingQueue);

            for (int i = 1; i <= WORKERS; i++) {
                new ConsumerThread(arrayBlockingQueue, "Consumer-" + i, win.getLogArea()).start();
            }
        });
    }
}
