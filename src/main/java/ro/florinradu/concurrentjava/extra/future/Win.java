package ro.florinradu.concurrentjava.extra.future;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.RoundingMode;
import java.util.List;

public class Win extends JFrame {
    public Win() {
        super("Crypto Market Prices");
        this.setSize(700, 400);
        this.setLayout(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        // columns and data
        String[] columns = {"Rank", "Name", "Price (usd)", "MarketCap (usd)"};
        Object[][] data = {};

        // create a table model
        DefaultTableModel model = new DefaultTableModel(data, columns);

        // create JTable with the model
        JTable table = new JTable(model);

        // create a JScrollPane to hold the table
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 10, 680, 200);

        // create JButton to refresh data
        JButton refreshButton = new JButton("Refresh data");
        refreshButton.setBounds(10, 230, 680, 20);
        refreshButton.addActionListener(e -> refreshData(model, refreshButton));

        // create disclaimer label
        JLabel disclaimerLabel = new JLabel("<html>" +
                "You should be prepared to lose all the money you invest in cryptoassets.<BR>" +
                "The cryptoasset market is largely unregulated.<BR>" +
                "There is a risk of losing money or any cryptoassets you purchase due to risks such as:<BR>" +
                "cyber-attacks, financial crime and firm failure.</html>"
        );
        disclaimerLabel.setBounds(10, 270, 680, 100);
        disclaimerLabel.setForeground(Color.RED);

        this.add(scrollPane);
        this.add(refreshButton);
        this.add(disclaimerLabel);
        this.setVisible(true);
    }

    private void refreshData(DefaultTableModel model, JButton refreshButton) {
        refreshButton.setEnabled(false);
        refreshButton.setText("Loading...");
        model.setRowCount(0);

        new Thread(() -> {
            try {
                List<CoinModel> coins = CoinsApiUtils.getCoins();

                SwingUtilities.invokeLater(() -> {
                    coins.forEach(c -> model.addRow(new Object[]{
                            c.rank(),
                            c.name(),
                            c.quotes().usd().price().setScale(4, RoundingMode.HALF_UP),
                            c.quotes().usd().marketCap()
                    }));

                    refreshButton.setEnabled(true);
                    refreshButton.setText("Refresh data");
                });
            } catch (Exception e) {
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    refreshButton.setEnabled(true);
                    refreshButton.setText("Refresh data");
                });
            }
        }, "coin-data-loader").start();
    }
}
