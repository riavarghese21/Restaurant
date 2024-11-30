package com.restaurant.Employee;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.restaurant.Database;
import com.restaurant.Customer.CustomerSignedIn;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ViewReservations {

    public JFrame frame;
    private JTable reservationsTable;
    private DefaultTableModel tableModel;
    private JButton markPresentButton, cancelReservationButton;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ViewReservations window = new ViewReservations();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public ViewReservations() {
        initialize();
        loadTodayReservations();
    }

    public void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblTitle = new JLabel("Today's Reservations");
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 17));
        lblTitle.setBounds(370, 20, 200, 30);
        frame.getContentPane().add(lblTitle);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(50, 70, 800, 350);
        frame.getContentPane().add(scrollPane);

        reservationsTable = new JTable();
        tableModel = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Reservation ID", "Customer ID", "Date", "Time", "Party Size", "Status"}
        );
        reservationsTable.setModel(tableModel);
        scrollPane.setViewportView(reservationsTable);

        markPresentButton = new JButton("Mark as Present");
        markPresentButton.setBounds(228, 450, 150, 30);
        frame.getContentPane().add(markPresentButton);
        markPresentButton.addActionListener(e -> markReservationAsPresent());

        cancelReservationButton = new JButton("Cancel Reservation");
        cancelReservationButton.setBounds(390, 450, 180, 30);
        frame.getContentPane().add(cancelReservationButton);
        cancelReservationButton.addActionListener(e -> cancelReservation());
        
        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setBounds(52, 515, 100, 30);
        frame.getContentPane().add(backButton);

        backButton.addActionListener(e -> {
            frame.dispose(); 
            EmployeeMenu employeeMenu = new EmployeeMenu(); 
            employeeMenu.setVisible(true); 
        });
    }

    private void loadTodayReservations() {
        try {
            Connection connection = Database.getConnection();
            if (connection == null) {
                JOptionPane.showMessageDialog(frame, "Database connection failed.", "Database Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String todayDate = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
            String query = "SELECT * FROM Reservations WHERE reservation_date = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, todayDate);
            ResultSet resultSet = statement.executeQuery();

            tableModel.setRowCount(0); // Clear existing rows
            while (resultSet.next()) {
                int reservationId = resultSet.getInt("reservation_id");
                int customerId = resultSet.getInt("customer_id");
                String date = resultSet.getString("reservation_date");
                String time = resultSet.getString("reservation_time");
                int partySize = resultSet.getInt("party_size");
                String status = resultSet.getString("reservation_status");
                tableModel.addRow(new Object[]{reservationId, customerId, date, time, partySize, status});
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "An error occurred while loading reservations.", "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void markReservationAsPresent() {
        int selectedRow = reservationsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a reservation to mark as present.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int reservationId = (int) tableModel.getValueAt(selectedRow, 0);

        try {
            Connection connection = Database.getConnection();
            if (connection == null) {
                JOptionPane.showMessageDialog(frame, "Database connection failed.", "Database Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String updateQuery = "UPDATE Reservations SET reservation_status = 'Present' WHERE reservation_id = ?";
            PreparedStatement statement = connection.prepareStatement(updateQuery);
            statement.setInt(1, reservationId);
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(frame, "Reservation marked as present.", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadTodayReservations();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "An error occurred while updating the reservation status.", "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void cancelReservation() {
        int selectedRow = reservationsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a reservation to cancel.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int reservationId = (int) tableModel.getValueAt(selectedRow, 0);

        try {
            Connection connection = Database.getConnection();
            if (connection == null) {
                JOptionPane.showMessageDialog(frame, "Database connection failed.", "Database Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String deleteQuery = "DELETE FROM Reservations WHERE reservation_id = ?";
            PreparedStatement statement = connection.prepareStatement(deleteQuery);
            statement.setInt(1, reservationId);
            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(frame, "Reservation canceled.", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadTodayReservations();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "An error occurred while canceling the reservation.", "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void cancelOrder() {
        int selectedRow = reservationsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a reservation to cancel the order.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int customerId = (int) tableModel.getValueAt(selectedRow, 1);

        try {
            Connection connection = Database.getConnection();
            if (connection == null) {
                JOptionPane.showMessageDialog(frame, "Database connection failed.", "Database Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String updateOrderQuery = "UPDATE Orders SET order_status = 'Canceled' WHERE customer_id = ?";
            PreparedStatement statement = connection.prepareStatement(updateOrderQuery);
            statement.setInt(1, customerId);
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(frame, "Order canceled.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "No active orders found for this customer.", "No Orders", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "An error occurred while canceling the order.", "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
