package ro.florinradu.concurrentjava.extra.structuredconcurrency;

import javax.swing.*;

/**
 * Real-world structured concurrency example.
 * <p>
 * The application concurrently retrieves a GitHub user's profile,
 * recent repositories, and public activity, then combines the
 * results into a single dashboard.
 */
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Win::new);
    }
}
