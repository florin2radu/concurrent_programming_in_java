package ro.florinradu.concurrentjava.extra.future;

import javax.swing.*;

/**
 * Real-world example using ExecutorService and Future.
 * The application retrieves cryptocurrency market data concurrently
 * from a public REST API and displays the results in a Swing interface.
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Win::new);
    }
}
