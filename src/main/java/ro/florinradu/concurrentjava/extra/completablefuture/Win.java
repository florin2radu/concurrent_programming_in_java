package ro.florinradu.concurrentjava.extra.completablefuture;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class Win extends JFrame {
    public Win() {
        super("Current Temperatures");
        this.setSize(500, 300);
        this.setLayout(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        // columns and data
        String[] columns = {"City", "Temperature", "Unit"};
        Object[][] data = {};

        // create a table model
        DefaultTableModel model = new DefaultTableModel(data, columns);

        // create JTable with the model
        JTable table = new JTable(model);

        // create a JScrollPane to hold the table
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 10, 480, 200);

        // create JButton to refresh data
        JButton refreshButton = new JButton("Refresh data");
        refreshButton.setBounds(10, 230, 480, 20);
        refreshButton.addActionListener(e -> refreshData(model, refreshButton));

        this.add(scrollPane);
        this.add(refreshButton);
        this.setVisible(true);
    }

    private void refreshData(DefaultTableModel model, JButton refreshButton) {
        refreshButton.setEnabled(false);
        refreshButton.setText("Loading...");

        // get temperatures and update table
        WeatherApiUtils.getTemperaturesAsync()
                .thenAccept(results -> SwingUtilities.invokeLater(() -> {
                    updateTable(model, results);
                    refreshButton.setEnabled(true);
                    refreshButton.setText("Refresh data");
                }))
                .exceptionally(error -> {
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(
                                this,
                                error.getMessage(),
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        refreshButton.setEnabled(true);
                        refreshButton.setText("Refresh data");
                    });
                    return null;
                });
    }

    private void updateTable(DefaultTableModel model, List<TemperatureResult> temperatures) {
        model.setRowCount(0);
        temperatures.forEach(result -> model.addRow(new Object[]{
                result.city().getDisplayName(),
                result.temperature(),
                result.unit()
        }));
    }
}
