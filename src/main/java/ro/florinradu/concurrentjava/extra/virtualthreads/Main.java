package ro.florinradu.concurrentjava.extra.virtualthreads;

import javax.swing.*;

/**
 * Real-world example using virtual threads.
 * <p>
 * The application checks multiple websites concurrently while keeping
 * the Swing user interface responsive.
 */
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Win::new);
    }
}
