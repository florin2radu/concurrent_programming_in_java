package ro.florinradu.concurrentjava.extra.ratelimiter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Win extends JFrame {

    private final DefaultTableModel model = new DefaultTableModel(new String[]{"Request", "Status", "Time (ms)"}, 0);
    private final JButton startButton = new JButton("Run requests");
    private final JProgressBar progressBar = new JProgressBar(0, RateLimitedService.getRequestCount());
    private final JLabel statusLabel = new JLabel("Ready");

    public Win() {
        super("Semaphore - API Rate Limiter");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(520, 380);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("API Request Limiter", SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(15, 10, 5, 10));

        JTable table = new JTable(model);
        table.setRowHeight(25);

        progressBar.setStringPainted(true);
        startButton.addActionListener(e -> runRequests());

        JPanel bottom = new JPanel(new BorderLayout(10, 10));
        bottom.setBorder(BorderFactory.createEmptyBorder(5, 15, 15, 15));
        bottom.add(startButton, BorderLayout.WEST);
        bottom.add(progressBar, BorderLayout.CENTER);
        bottom.add(statusLabel, BorderLayout.EAST);

        add(title, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void runRequests() {
        model.setRowCount(0);
        progressBar.setValue(0);
        startButton.setEnabled(false);
        statusLabel.setText("Running...");

        RateLimitedService.execute(this::addResult, this::finished);
    }

    private void addResult(RequestResult result) {
        SwingUtilities.invokeLater(() -> {
            model.addRow(new Object[]{result.request(), result.status(), result.duration()});
            progressBar.setValue(progressBar.getValue() + 1);
        });
    }

    private void finished() {
        SwingUtilities.invokeLater(() -> {
            progressBar.setValue(RateLimitedService.getRequestCount());
            statusLabel.setText("Completed");
            startButton.setEnabled(true);
        });
    }
}
