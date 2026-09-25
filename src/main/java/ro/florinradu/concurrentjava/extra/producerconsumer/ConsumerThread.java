package ro.florinradu.concurrentjava.extra.producerconsumer;

import javax.swing.*;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ArrayBlockingQueue;

public class ConsumerThread extends Thread {
    private final JTextArea logArea;
    private ArrayBlockingQueue<File> queue;

    public ConsumerThread(ArrayBlockingQueue<File> queue, String name, JTextArea logArea) {
        this.queue = queue;
        this.setName(name);
        this.logArea = logArea;
    }

    @Override
    public void run() {
        while (true) {
            try {
                File txtFile = queue.take();
                Path pdfPath = Paths.get(txtFile.getParentFile().getAbsolutePath(), txtFile.getName().replace(".txt", ".pdf"));
                PdfUtils.createPdf(txtFile, pdfPath);
                SwingUtilities.invokeLater(() ->
                        logArea.append(Thread.currentThread().getName() + " printed to pdf file: " + txtFile.getName() + "\n"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
