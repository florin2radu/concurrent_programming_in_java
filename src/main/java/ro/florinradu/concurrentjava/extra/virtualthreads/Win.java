package ro.florinradu.concurrentjava.extra.virtualthreads;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Win extends JFrame {
    private final DefaultTableModel model;
    private final JButton refreshButton;
    private final JProgressBar progressBar;
    private final JLabel statusLabel;

    public Win() {
        super("Virtual Threads - Website Monitor");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(850, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("Website Status Monitor");
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
        title.setBorder(BorderFactory.createEmptyBorder(15, 15, 5, 15));

        model = new DefaultTableModel(new String[]{"URL", "Status", "Time (ms)", "Thread"}, 0);

        JTable table = new JTable(model);
        table.setRowHeight(26);
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));

        refreshButton = new JButton("Check websites");
        refreshButton.addActionListener(e -> checkWebsites());

        progressBar = new JProgressBar(0, WebsiteMonitor.getWebsiteCount());
        progressBar.setStringPainted(true);

        statusLabel = new JLabel("Ready");

        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(5, 15, 15, 15));
        bottomPanel.add(refreshButton, BorderLayout.WEST);
        bottomPanel.add(progressBar, BorderLayout.CENTER);
        bottomPanel.add(statusLabel, BorderLayout.EAST);

        add(title, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void checkWebsites() {
        model.setRowCount(0);
        progressBar.setValue(0);
        refreshButton.setEnabled(false);
        statusLabel.setText("Checking...");

        WebsiteMonitor.checkWebsites(this::addResult, this::finished);
    }

    private void addResult(WebsiteResult result) {
        SwingUtilities.invokeLater(() -> {
            model.addRow(new Object[]{result.url(), result.status(), result.duration(), result.thread()});
            progressBar.setValue(progressBar.getValue() + 1);
            statusLabel.setText(progressBar.getValue() + " / " + WebsiteMonitor.getWebsiteCount());
        });
    }

    private void finished() {
        SwingUtilities.invokeLater(() -> {
            progressBar.setValue(WebsiteMonitor.getWebsiteCount());
            statusLabel.setText("Completed");
            refreshButton.setEnabled(true);
        });
    }
}
