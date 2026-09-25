package ro.florinradu.concurrentjava.extra.completablefuture;

import javax.swing.*;

/**
 * Real-world example using CompletableFuture.
 * The application retrieves current temperatures for multiple cities
 * from a public REST API and displays the results in a Swing interface.
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Win::new);
    }
}
