package ro.florinradu.concurrentjava.extra.structuredconcurrency;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Win extends JFrame {

    private final JTextField usernameField = new JTextField("florin2radu", 18);
    private final JButton loadButton = new JButton("Load");
    private final JLabel nameLabel = new JLabel("-");
    private final JLabel statsLabel = new JLabel("-");
    private final JLabel statusLabel = new JLabel("Ready");
    private final DefaultTableModel repositoriesModel = new DefaultTableModel(new String[]{"Repository", "Language", "Stars", "Updated"}, 0);
    private final DefaultTableModel eventsModel = new DefaultTableModel(new String[]{"Event", "Repository", "Created"}, 0);

    public Win() {
        super("Structured Concurrency - GitHub Dashboard");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(760, 520);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("GitHub Developer Dashboard", SwingConstants.CENTER);

        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("Username:"));
        searchPanel.add(usernameField);
        searchPanel.add(loadButton);

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setBorder(BorderFactory.createEmptyBorder(12, 15, 5, 15));
        northPanel.add(title, BorderLayout.NORTH);
        northPanel.add(searchPanel, BorderLayout.SOUTH);

        JPanel profilePanel = new JPanel(new GridLayout(2, 1));
        profilePanel.setBorder(BorderFactory.createTitledBorder("Profile"));
        profilePanel.add(nameLabel);
        profilePanel.add(statsLabel);

        JTable repositoriesTable = new JTable(repositoriesModel);
        JTable eventsTable = new JTable(eventsModel);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Recent repositories", new JScrollPane(repositoriesTable));
        tabs.addTab("Recent activity", new JScrollPane(eventsTable));

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        centerPanel.add(profilePanel, BorderLayout.NORTH);
        centerPanel.add(tabs, BorderLayout.CENTER);

        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 15, 12, 15));

        loadButton.addActionListener(e -> loadDashboard());
        usernameField.addActionListener(e -> loadDashboard());

        add(northPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadDashboard() {
        String username = usernameField.getText().trim();

        if (username.isEmpty()) {
            return;
        }

        clearData();
        loadButton.setEnabled(false);
        statusLabel.setText("Loading profile, repositories and activity...");

        Thread.startVirtualThread(() -> {
            try {
                GithubDashboard dashboard = GithubService.loadDashboard(username);
                SwingUtilities.invokeLater(() -> showDashboard(dashboard));
            } catch (Exception e) {
                SwingUtilities.invokeLater(() -> showError(e));
            }
        });
    }

    private void showDashboard(GithubDashboard dashboard) {
        Profile profile = dashboard.profile();

        nameLabel.setText(profile.name() == null ? profile.login() : profile.name() + "  (@" + profile.login() + ")");
        statsLabel.setText("Public repositories: " + profile.publicRepos() + "    Followers: " + profile.followers() + "    Following: " + profile.following());

        dashboard.repositories().forEach(repo -> repositoriesModel.addRow(new Object[]{repo.name(), repo.language() == null ? "-" : repo.language(), repo.stars(), formatDate(repo.updatedAt())}));
        dashboard.events().forEach(event -> eventsModel.addRow(new Object[]{event.type(), event.repo().name(), formatDate(event.createdAt())}));

        statusLabel.setText("Completed");
        loadButton.setEnabled(true);
    }

    private void clearData() {
        nameLabel.setText("-");
        statsLabel.setText("-");
        repositoriesModel.setRowCount(0);
        eventsModel.setRowCount(0);
    }

    private void showError(Exception e) {
        Throwable cause = e.getCause() == null ? e : e.getCause();
        statusLabel.setText("Failed");
        loadButton.setEnabled(true);
        JOptionPane.showMessageDialog(this, cause.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

    private String formatDate(String value) {
        return value == null ? "-" : value.replace("T", " ").replace("Z", "");
    }
}
