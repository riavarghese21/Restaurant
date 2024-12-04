package com.restaurant.Employee;

import javax.swing.*;
import com.restaurant.Database;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ViewStatistics {

    public JFrame frame;
    private JSpinner dateSpinner;
    private JLabel totalMoneySpentLabel;
    private JLabel averageMoneySpentLabel;
    private JLabel totalReservationsLabel;
    private JButton showStatisticsButton;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ViewStatistics window = new ViewStatistics();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public ViewStatistics() {
        initialize();
    }

    public void initialize() {
        frame = new JFrame("View Statistics");
        frame.setBounds(100, 100, 600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null); 

        // Title Label
        JLabel titleLabel = new JLabel("Daily Statistics");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 17));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(200, 20, 200, 30);
        frame.getContentPane().add(titleLabel);

        // Date Selection Spinner
        JLabel selectDateLabel = new JLabel("Select Date:");
        selectDateLabel.setBounds(50, 80, 100, 20);
        frame.getContentPane().add(selectDateLabel);

        dateSpinner = new JSpinner(new SpinnerDateModel());
        dateSpinner.setBounds(150, 80, 200, 25);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "MM/dd/yyyy");
        dateSpinner.setEditor(dateEditor);
        frame.getContentPane().add(dateSpinner);

        // Show Statistics Button
        showStatisticsButton = new JButton("Show Statistics");
        showStatisticsButton.setBounds(370, 80, 150, 25);
        showStatisticsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showStatistics();
            }
        });
        frame.getContentPane().add(showStatisticsButton);

        // Labels to display the statistics
        totalMoneySpentLabel = new JLabel("Total Money Spent: $0.00");
        totalMoneySpentLabel.setBounds(50, 140, 500, 20);
        totalMoneySpentLabel.setHorizontalAlignment(SwingConstants.LEFT);
        frame.getContentPane().add(totalMoneySpentLabel);

        averageMoneySpentLabel = new JLabel("Average Money Spent: $0.00");
        averageMoneySpentLabel.setBounds(50, 180, 500, 20);
        averageMoneySpentLabel.setHorizontalAlignment(SwingConstants.LEFT);
        frame.getContentPane().add(averageMoneySpentLabel);

        totalReservationsLabel = new JLabel("Total Number of Reservations: 0");
        totalReservationsLabel.setBounds(50, 220, 500, 20);
        totalReservationsLabel.setHorizontalAlignment(SwingConstants.LEFT);
        frame.getContentPane().add(totalReservationsLabel);

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setBounds(50, 300, 100, 25);
        frame.getContentPane().add(backButton);
        backButton.addActionListener(e -> {
            frame.dispose();
            EmployeeMenu employeeMenu = new EmployeeMenu();
            employeeMenu.setVisible(true);
        });
    }

    private void showStatistics() {
        Date selectedDate = (Date) dateSpinner.getValue();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String selectedDateString = dateFormat.format(selectedDate);  // Format selected date to MM/dd/yyyy

        try {
            // Get a connection to the database
            Connection connection = Database.getConnection();
            if (connection == null) {
                JOptionPane.showMessageDialog(frame, "Database connection failed.", "Database Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Total Money Spent Query
            String totalMoneyQuery = "SELECT SUM(total_amount) AS total_money FROM Orders WHERE order_date = ?";
            PreparedStatement totalMoneyStatement = connection.prepareStatement(totalMoneyQuery);
            totalMoneyStatement.setString(1, selectedDateString);
            ResultSet totalMoneyResult = totalMoneyStatement.executeQuery();

            double totalMoneySpent = 0.0;
            if (totalMoneyResult.next() && totalMoneyResult.getString("total_money") != null) {
                totalMoneySpent = totalMoneyResult.getDouble("total_money");
            }
            totalMoneySpentLabel.setText("Total Money Spent: $" + String.format("%.2f", totalMoneySpent));

            // Average Money Spent Query
            String averageMoneyQuery = "SELECT AVG(total_amount) AS average_money FROM Orders WHERE order_date = ?";
            PreparedStatement averageMoneyStatement = connection.prepareStatement(averageMoneyQuery);
            averageMoneyStatement.setString(1, selectedDateString);
            ResultSet averageMoneyResult = averageMoneyStatement.executeQuery();

            double averageMoneySpent = 0.0;
            if (averageMoneyResult.next() && averageMoneyResult.getString("average_money") != null) {
                averageMoneySpent = averageMoneyResult.getDouble("average_money");
            }
            averageMoneySpentLabel.setText("Average Money Spent: $" + String.format("%.2f", averageMoneySpent));

            // Total Number of Reservations Query
            String totalReservationsQuery = "SELECT COUNT(*) AS total_reservations FROM Reservations WHERE reservation_date = ?";
            PreparedStatement totalReservationsStatement = connection.prepareStatement(totalReservationsQuery);
            totalReservationsStatement.setString(1, selectedDateString);
            ResultSet totalReservationsResult = totalReservationsStatement.executeQuery();

            int totalReservations = 0;
            if (totalReservationsResult.next()) {
                totalReservations = totalReservationsResult.getInt("total_reservations");
            }
            totalReservationsLabel.setText("Total Number of Reservations: " + totalReservations);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "An error occurred while fetching the statistics.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    public JFrame getFrame() {
        return frame;
    }
}
