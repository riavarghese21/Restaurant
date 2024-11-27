package com.restaurant.Customer;

import javax.swing.*;
import com.restaurant.Database;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MakeReservation {

    private JFrame frame;
    private JSpinner reservationDateSpinner;
    private JTextField partySizeField;
    private JButton makeReservationButton;
    private JPanel timeSlotsPanel;
    private ArrayList<JButton> timeSlotButtons;

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

    public MakeReservation() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblTitle = new JLabel("Make a Reservation");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblTitle.setBounds(150, 20, 200, 30);
        frame.getContentPane().add(lblTitle);

        JLabel lblDate = new JLabel("Select Date:");
        lblDate.setBounds(50, 70, 100, 25);
        frame.getContentPane().add(lblDate);

        reservationDateSpinner = new JSpinner(new SpinnerDateModel());
        reservationDateSpinner.setBounds(150, 70, 200, 25);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(reservationDateSpinner, "MM-dd-yyyy");
        reservationDateSpinner.setEditor(dateEditor);
        frame.getContentPane().add(reservationDateSpinner);

        JButton loadAvailableTimesButton = new JButton("Load Available Times");
        loadAvailableTimesButton.setBounds(150, 110, 200, 30);
        frame.getContentPane().add(loadAvailableTimesButton);
        loadAvailableTimesButton.addActionListener(e -> loadAvailableTimeSlots());

        JLabel lblTimeSlots = new JLabel("Available Time Slots:");
        lblTimeSlots.setBounds(50, 160, 150, 25);
        frame.getContentPane().add(lblTimeSlots);

        timeSlotsPanel = new JPanel();
        timeSlotsPanel.setBounds(50, 190, 380, 150);
        timeSlotsPanel.setLayout(new GridLayout(3, 4, 10, 10)); 
        frame.getContentPane().add(timeSlotsPanel);

        JLabel lblPartySize = new JLabel("Party Size:");
        lblPartySize.setBounds(50, 360, 100, 25);
        frame.getContentPane().add(lblPartySize);

        partySizeField = new JTextField();
        partySizeField.setBounds(150, 360, 200, 25);
        frame.getContentPane().add(partySizeField);
        partySizeField.setColumns(10);

        makeReservationButton = new JButton("Make Reservation");
        makeReservationButton.setBounds(150, 410, 200, 30);
        frame.getContentPane().add(makeReservationButton);
        makeReservationButton.addActionListener(e -> makeReservation());
    }

    private void loadAvailableTimeSlots() {
        timeSlotsPanel.removeAll(); // Clear previous buttons

        Date selectedDate = (Date) reservationDateSpinner.getValue();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        String selectedDateString = dateFormat.format(selectedDate);

        // List of time slots in 12-hour format
        String[] allTimeSlots = {
            "12:00 PM", "1:00 PM", "2:00 PM", "3:00 PM", "4:00 PM", "5:00 PM", 
            "6:00 PM", "7:00 PM", "8:00 PM", "9:00 PM", "10:00 PM", "11:00 PM"
        };
        timeSlotButtons = new ArrayList<>();

        try {
            Connection connection = Database.getConnection();
            if (connection == null) {
                throw new SQLException("Database connection failed.");
            }

            String query = "SELECT reservation_time FROM Reservations WHERE reservation_date = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, selectedDateString);

            ResultSet resultSet = statement.executeQuery();
            ArrayList<String> unavailableTimes = new ArrayList<>();

            while (resultSet.next()) {
                // Convert time to 12-hour format to match the display
                String time = formatTimeSlot(resultSet.getString("reservation_time"));
                unavailableTimes.add(time);
            }

            for (String timeSlot : allTimeSlots) {
                JButton timeSlotButton = new JButton(timeSlot);
                if (unavailableTimes.contains(timeSlot)) {
                    timeSlotButton.setEnabled(false);
                    timeSlotButton.setBackground(Color.GRAY);
                } else {
                    timeSlotButton.setEnabled(true);
                    timeSlotButton.addActionListener(e -> selectTimeSlot(timeSlotButton));
                }
                timeSlotsPanel.add(timeSlotButton);
                timeSlotButtons.add(timeSlotButton);
            }

            timeSlotsPanel.revalidate();
            timeSlotsPanel.repaint();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error loading available time slots.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void selectTimeSlot(JButton selectedButton) {
        for (JButton button : timeSlotButtons) {
            button.setBackground(null);
        }
        selectedButton.setBackground(Color.CYAN);
    }

    private void makeReservation() {
        int customerId = UserSession.getInstance().getCustomerId();
        Date selectedDate = (Date) reservationDateSpinner.getValue();
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        String selectedDateString = dateFormat.format(selectedDate);

        String selectedTime = null;
        for (JButton button : timeSlotButtons) {
            if (button.getBackground() == Color.CYAN) {
                selectedTime = button.getText();
                break;
            }
        }

        if (selectedTime == null) {
            JOptionPane.showMessageDialog(frame, "Please select a valid time slot.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String partySizeText = partySizeField.getText();
        if (partySizeText.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter a party size.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int partySize = Integer.parseInt(partySizeText);

            Connection connection = Database.getConnection();
            if (connection == null) {
                throw new SQLException("Database connection failed.");
            }

            String query = "INSERT INTO Reservations (customer_id, reservation_date, reservation_time, party_size) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, customerId);
            statement.setString(2, selectedDateString); 
            statement.setString(3, parseTimeTo24Hour(selectedTime));
            statement.setInt(4, partySize);

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(frame, "Reservation made successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadAvailableTimeSlots();
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to make a reservation. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "An error occurred while making the reservation. Please try again.", "Database Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid party size. Please enter a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private String formatTimeSlot(String timeSlot) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat outputFormat = new SimpleDateFormat("hh:mm a");
            Date date = inputFormat.parse(timeSlot);
            return outputFormat.format(date);
        } catch (Exception e) {
            return timeSlot;
        }
    }

    private String parseTimeTo24Hour(String timeSlot) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("hh:mm a");
            SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm:ss");
            Date date = inputFormat.parse(timeSlot);
            return outputFormat.format(date);
        } catch (Exception e) {
            return timeSlot;
        }
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
}
