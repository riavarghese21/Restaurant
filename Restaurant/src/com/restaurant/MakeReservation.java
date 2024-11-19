package com.restaurant;

import javax.swing.*;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Date;
import java.awt.Font;

public class MakeReservation {

    private JFrame frame;
    private JSpinner dateSpinner;
    private JComboBox<String> timeComboBox;
    private JSpinner partySizeSpinner;
    private JButton reserveButton;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                MakeReservation window = new MakeReservation();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the application.
     */
    public MakeReservation() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Make a Reservation");
        lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 17));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(340, 6, 179, 16);
        frame.getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Select a Date");
        lblNewLabel_1.setBounds(196, 98, 81, 16);
        frame.getContentPane().add(lblNewLabel_1);

        dateSpinner = new JSpinner(new SpinnerDateModel());
        dateSpinner.setBounds(306, 93, 317, 26);
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "MM/dd/yyyy"));
        frame.getContentPane().add(dateSpinner);

        JLabel lblTime = new JLabel("Select a Time");
        lblTime.setBounds(183, 150, 94, 16);
        frame.getContentPane().add(lblTime);

        String[] times = {"Select a Time", "12:00 PM", "1:00 PM", "2:00 PM", "3:00 PM", "4:00 PM", "5:00 PM", "6:00 PM", "7:00 PM", "8:00 PM", "9:00 PM", "10:00 PM"};
        timeComboBox = new JComboBox<>(times);
        timeComboBox.setBounds(306, 146, 317, 26);
        frame.getContentPane().add(timeComboBox);

        JLabel lblPartySize = new JLabel("Party Size");
        lblPartySize.setBounds(196, 200, 81, 16);
        frame.getContentPane().add(lblPartySize);

        partySizeSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 20, 1));
        partySizeSpinner.setBounds(306, 195, 317, 26);
        frame.getContentPane().add(partySizeSpinner);

        reserveButton = new JButton("Reserve");
        reserveButton.setBounds(379, 448, 117, 29);
        frame.getContentPane().add(reserveButton);

        reserveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeReservation();
            }
        });
    }

    private void makeReservation() {
        Date selectedDate = (Date) dateSpinner.getValue();
        String timeString = (String) timeComboBox.getSelectedItem();
        int partySize = (int) partySizeSpinner.getValue();

        java.sql.Date reservationDate = new java.sql.Date(selectedDate.getTime());
        java.sql.Time reservationTime = java.sql.Time.valueOf(convertTimeTo24HourFormat(timeString));

        try {
            Connection connection = Database.getConnection();
            if (connection == null) {
                JOptionPane.showMessageDialog(frame, "Database connection failed.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String checkAvailabilityQuery = "SELECT COUNT(*) FROM Reservations WHERE reservation_date = ? AND reservation_time = ?";
            PreparedStatement checkStmt = connection.prepareStatement(checkAvailabilityQuery);
            checkStmt.setDate(1, reservationDate);
            checkStmt.setTime(2, reservationTime);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            if (count > 0) {
                JOptionPane.showMessageDialog(frame, "The selected time slot is already booked. Please choose another time.", "Slot Unavailable", JOptionPane.WARNING_MESSAGE);
            } else {
                String insertReservationQuery = "INSERT INTO Reservations (customer_id, reservation_date, reservation_time, party_size) VALUES (?, ?, ?, ?)";
                PreparedStatement insertStmt = connection.prepareStatement(insertReservationQuery);
                insertStmt.setInt(1, 1); 
                insertStmt.setDate(2, reservationDate);
                insertStmt.setTime(3, reservationTime);
                insertStmt.setInt(4, partySize);
                insertStmt.executeUpdate();

                JOptionPane.showMessageDialog(frame, "Reservation made successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose(); 
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "An error occurred while processing your reservation. Please try again.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String convertTimeTo24HourFormat(String timeString) {
        try {
            java.text.SimpleDateFormat inputFormat = new java.text.SimpleDateFormat("h:mm a");
            java.text.SimpleDateFormat outputFormat = new java.text.SimpleDateFormat("HH:mm:ss");
            java.util.Date date = inputFormat.parse(timeString);
            return outputFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "00:00:00";
        }
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
}
