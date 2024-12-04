package com.restaurant.Employee;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.restaurant.Database;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ViewReservations {

    public JFrame frame;
    private JTable reservationsTable;
    private DefaultTableModel tableModel;
    private JButton markPresentButton, cancelReservationButton, showReservationsButton;
    private JSpinner dateSpinner;

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
        loadReservationsForDate(new Date()); // Load today's reservations by default
    }

    public void initialize() {
        frame = new JFrame("View Reservations");
        frame.setBounds(100, 100, 900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblTitle = new JLabel("View Reservations");
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 17));
        lblTitle.setBounds(370, 20, 200, 30);
        frame.getContentPane().add(lblTitle);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(50, 110, 800, 310);
        frame.getContentPane().add(scrollPane);

        reservationsTable = new JTable();
        tableModel = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Reservation ID", "Customer ID", "Date", "Time", "Party Size", "Status"}
        );
        reservationsTable.setModel(tableModel);
        scrollPane.setViewportView(reservationsTable);

        // Date Spinner for selecting reservation date
        JLabel dateLabel = new JLabel("Select Date:");
        dateLabel.setBounds(309, 73, 100, 25);
        frame.getContentPane().add(dateLabel);

        // Date spinner that changes days specifically when arrows are pressed
        dateSpinner = new JSpinner(new SpinnerDateModel());
        dateSpinner.setBounds(387, 72, 150, 25);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "MM/dd/yyyy");
        dateSpinner.setEditor(dateEditor);

        // Set the calendar to increment/decrement by day
        ((SpinnerDateModel) dateSpinner.getModel()).setCalendarField(Calendar.DAY_OF_MONTH);

        frame.getContentPane().add(dateSpinner);

        // Show Reservations Button
        showReservationsButton = new JButton("Show Reservations");
        showReservationsButton.setBounds(542, 74, 150, 25);
        frame.getContentPane().add(showReservationsButton);
        showReservationsButton.addActionListener(e -> {
            Date selectedDate = (Date) dateSpinner.getValue();
            loadReservationsForDate(selectedDate);
        });

        markPresentButton = new JButton("Mark as Present");
        markPresentButton.setBounds(295, 450, 150, 30);
        frame.getContentPane().add(markPresentButton);
        markPresentButton.addActionListener(e -> markReservationAsPresent());

        cancelReservationButton = new JButton("Cancel Reservation");
        cancelReservationButton.setBounds(473, 450, 180, 30);
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

    private void loadReservationsForDate(Date date) {
        try {
            Connection connection = Database.getConnection();
            if (connection == null) {
                JOptionPane.showMessageDialog(frame, "Database connection failed.", "Database Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String formattedDate = new SimpleDateFormat("MM/dd/yyyy").format(date);
            String query = "SELECT * FROM Reservations WHERE reservation_date = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, formattedDate);
            ResultSet resultSet = statement.executeQuery();

            tableModel.setRowCount(0); // Clear existing rows
            while (resultSet.next()) {
                int reservationId = resultSet.getInt("reservation_id");
                int customerId = resultSet.getInt("customer_id");
                String reservationDate = resultSet.getString("reservation_date");
                String time = resultSet.getString("reservation_time");
                int partySize = resultSet.getInt("party_size");
                String status = resultSet.getString("reservation_status");
                tableModel.addRow(new Object[]{reservationId, customerId, reservationDate, time, partySize, status});
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
                loadReservationsForDate((Date) dateSpinner.getValue()); // Reload reservations for the selected date
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

            // Update the status of the reservation to "Canceled" instead of deleting it
            String updateQuery = "UPDATE Reservations SET reservation_status = 'Canceled' WHERE reservation_id = ?";
            PreparedStatement statement = connection.prepareStatement(updateQuery);
            statement.setInt(1, reservationId);
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(frame, "Reservation canceled successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadReservationsForDate((Date) dateSpinner.getValue()); // Reload reservations for the selected date
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "An error occurred while canceling the reservation.", "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

}
