package com.restaurant.Employee;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.restaurant.Database;

public class ViewStatistics {

    public JFrame frame;
    private JSpinner dateSpinner;
    private JLabel totalMoneySpentLabel;
    private JLabel averageMoneySpentLabel;
    private JLabel totalReservationsLabel;

    /**
     * Launch the application.
     */
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

    /**
     * Create the application.
     */
    public ViewStatistics() {
        initialize();
    }

    /**
     * Getter method for frame.
     */
    public JFrame getFrame() {
        return frame;
    }

    /**
     * Initialize the contents of the frame using Absolute Layout.
     */
    private void initialize() {
        frame = new JFrame("View Daily Statistics");
        frame.setTitle("");
        frame.setBounds(100, 100, 700, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        // Date Selection Spinner
        JLabel selectDateLabel = new JLabel("Select Date:");
        selectDateLabel.setBounds(50, 20, 120, 30);
        selectDateLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        frame.getContentPane().add(selectDateLabel);

        dateSpinner = new JSpinner(new SpinnerDateModel());
        dateSpinner.setBounds(180, 20, 150, 30);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "MM/dd/yyyy");
        dateSpinner.setEditor(dateEditor);
        frame.getContentPane().add(dateSpinner);

        // Show Statistics Button
        JButton showStatisticsButton = new JButton("Show Statistics");
        showStatisticsButton.setBounds(350, 20, 150, 30);
        showStatisticsButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
        frame.getContentPane().add(showStatisticsButton);

        // Labels to display the statistics
        totalMoneySpentLabel = new JLabel("Total Money Spent: $0.00");
        totalMoneySpentLabel.setBounds(50, 80, 600, 40);
        totalMoneySpentLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        totalMoneySpentLabel.setHorizontalAlignment(SwingConstants.LEFT);
        frame.getContentPane().add(totalMoneySpentLabel);

        averageMoneySpentLabel = new JLabel("Average Money Spent: $0.00");
        averageMoneySpentLabel.setBounds(50, 140, 600, 40);
        averageMoneySpentLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        averageMoneySpentLabel.setHorizontalAlignment(SwingConstants.LEFT);
        frame.getContentPane().add(averageMoneySpentLabel);

        totalReservationsLabel = new JLabel("Total Number of Reservations: 0");
        totalReservationsLabel.setBounds(50, 200, 600, 40);
        totalReservationsLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        totalReservationsLabel.setHorizontalAlignment(SwingConstants.LEFT);
        frame.getContentPane().add(totalReservationsLabel);

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setBounds(50, 400, 100, 30);
        backButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
        frame.getContentPane().add(backButton);

        // Button Action Listeners
        showStatisticsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showStatistics();
            }
        });

        backButton.addActionListener(e -> {
            frame.dispose();
            EmployeeMenu employeeMenu = new EmployeeMenu();
            employeeMenu.setVisible(true);
        });
    }

    /**
     * Fetch and display statistics for the selected date.
     */
    private void showStatistics() {
        Date selectedDate = (Date) dateSpinner.getValue();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String selectedDateString = dateFormat.format(selectedDate);

        try {
            Connection connection = Database.getConnection();
            if (connection == null) {
                JOptionPane.showMessageDialog(frame, "Database connection failed.", "Database Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Fetch total money spent
            String totalMoneyQuery = "SELECT SUM(total_amount) AS total_money FROM Orders WHERE DATE(order_date) = ?";
            PreparedStatement totalMoneyStatement = connection.prepareStatement(totalMoneyQuery);
            totalMoneyStatement.setString(1, selectedDateString);
            ResultSet totalMoneyResult = totalMoneyStatement.executeQuery();

            double totalMoneySpent = 0.0;
            if (totalMoneyResult.next()) {
                totalMoneySpent = totalMoneyResult.getDouble("total_money");
            }
            totalMoneySpentLabel.setText("Total Money Spent: $" + String.format("%.2f", totalMoneySpent));

            // Fetch average money spent
            String averageMoneyQuery = "SELECT AVG(total_amount) AS average_money FROM Orders WHERE DATE(order_date) = ?";
            PreparedStatement averageMoneyStatement = connection.prepareStatement(averageMoneyQuery);
            averageMoneyStatement.setString(1, selectedDateString);
            ResultSet averageMoneyResult = averageMoneyStatement.executeQuery();

            double averageMoneySpent = 0.0;
            if (averageMoneyResult.next()) {
                averageMoneySpent = averageMoneyResult.getDouble("average_money");
            }
            averageMoneySpentLabel.setText("Average Money Spent: $" + String.format("%.2f", averageMoneySpent));

            // Fetch total number of reservations
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
}
