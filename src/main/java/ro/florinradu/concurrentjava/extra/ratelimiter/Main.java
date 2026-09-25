package ro.florinradu.concurrentjava.extra.ratelimiter;

import javax.swing.SwingUtilities;

/**
 * Real-world concurrency-limiting example using Semaphore.
 *
 * The application launches multiple HTTP requests concurrently while limiting
 * the number of requests that may access the external service at the same time.
 */
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Win::new);
    }
}
