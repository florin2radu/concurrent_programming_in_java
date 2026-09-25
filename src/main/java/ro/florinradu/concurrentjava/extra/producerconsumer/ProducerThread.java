package ro.florinradu.concurrentjava.extra.producerconsumer;

import javax.swing.*;
import java.io.File;
import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;

public class ProducerThread extends Thread {
    private ArrayBlockingQueue<File> queue;
    private File txtDirectory;
    private JTextArea logArea;

    public ProducerThread(ArrayBlockingQueue<File> queue, File txtDirectory, JTextArea logArea) {
        this.setName("Producer-1");
        this.queue = queue;
        this.txtDirectory = txtDirectory;
        this.logArea = logArea;
    }

    @Override
    public void run() {
        File[] files = txtDirectory.listFiles();
        Arrays.asList(files).stream()
                .filter(f -> f.getName().toLowerCase().endsWith(".txt"))
                .forEach(f -> {
                    try {
                        queue.put(f);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    SwingUtilities.invokeLater(() ->
                            logArea.append(Thread.currentThread().getName() + "added file for processing: " + f.getName() + "\n"));
                });
    }
}
