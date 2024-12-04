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
        frame = new JFrame("Make Reservation");
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblTitle = new JLabel("Make a Reservation");
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setFont(new Font("Lucida Grande", Font.BOLD, 17));
        lblTitle.setBounds(245, 20, 310, 30);
        frame.getContentPane().add(lblTitle);

        JLabel lblDate = new JLabel("Select Date:");
        lblDate.setHorizontalAlignment(SwingConstants.CENTER);
        lblDate.setBounds(268, 113, 86, 25);
        frame.getContentPane().add(lblDate);

        reservationDateSpinner = new JSpinner(new SpinnerDateModel());
        reservationDateSpinner.setBounds(355, 112, 200, 25);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(reservationDateSpinner, "MM/dd/yyyy");
        reservationDateSpinner.setEditor(dateEditor);
        frame.getContentPane().add(reservationDateSpinner);

        JButton loadAvailableTimesButton = new JButton("Load Available Times");
        loadAvailableTimesButton.setBounds(308, 151, 200, 30);
        frame.getContentPane().add(loadAvailableTimesButton);
        loadAvailableTimesButton.addActionListener(e -> loadAvailableTimeSlots());

        JLabel lblTimeSlots = new JLabel("Available Time Slots:");
        lblTimeSlots.setBounds(186, 193, 150, 25);
        frame.getContentPane().add(lblTimeSlots);

        timeSlotsPanel = new JPanel();
        timeSlotsPanel.setBounds(186, 222, 460, 150);
        timeSlotsPanel.setLayout(new GridLayout(3, 4, 10, 10)); 
        frame.getContentPane().add(timeSlotsPanel);

        JLabel lblPartySize = new JLabel("Party Size:");
        lblPartySize.setHorizontalAlignment(SwingConstants.RIGHT);
        lblPartySize.setBounds(236, 384, 100, 25);
        frame.getContentPane().add(lblPartySize);

        partySizeField = new JTextField();
        partySizeField.setBounds(355, 384, 200, 25);
        frame.getContentPane().add(partySizeField);
        partySizeField.setColumns(10);

        makeReservationButton = new JButton("Make Reservation");
        makeReservationButton.setBounds(308, 421, 200, 30);
        frame.getContentPane().add(makeReservationButton);
        makeReservationButton.addActionListener(e -> makeReservation());
        
        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setBounds(50, 513, 100, 30);
        frame.getContentPane().add(backButton);

        backButton.addActionListener(e -> {
            frame.dispose(); 
            CustomerSignedIn customerSignedInPage = new CustomerSignedIn(); 
            customerSignedInPage.setVisible(true); 
        });
    }

    private void loadAvailableTimeSlots() {
        timeSlotsPanel.removeAll(); 

        Date selectedDate = (Date) reservationDateSpinner.getValue();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String selectedDateString = dateFormat.format(selectedDate);

        String[] allTimeSlots = {
            "12:00:00", "13:00:00", "14:00:00", "15:00:00", "16:00:00", "17:00:00", 
            "18:00:00", "19:00:00", "20:00:00", "21:00:00", "22:00:00", "23:00:00"
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
                unavailableTimes.add(resultSet.getString("reservation_time"));
            }

            for (String timeSlot : allTimeSlots) {
                JButton timeSlotButton = new JButton(formatTimeSlot(timeSlot));
                if (unavailableTimes.contains(timeSlot)) {
                    timeSlotButton.setEnabled(false);
                    timeSlotButton.setBackground(Color.GRAY);
                } else {
                    timeSlotButton.setEnabled(true);
                    timeSlotButton.setBackground(null);
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
            if (button.isEnabled()) {
                button.setBackground(null); 
            }
        }
        selectedButton.setBackground(Color.CYAN); 
    }

    private void makeReservation() {
        int customerId = UserSession.getInstance().getCustomerId();
        Date selectedDate = (Date) reservationDateSpinner.getValue();
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String selectedDateString = dateFormat.format(selectedDate);

        String selectedTime = null;
        JButton selectedButton = null;

        for (JButton button : timeSlotButtons) {
            if (button.getBackground() == Color.CYAN) {
                selectedTime = button.getText();
                selectedButton = button;
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

            String query = "INSERT INTO Reservations (customer_id, reservation_date, reservation_time, party_size, reservation_status) VALUES (?, ?, ?, ?, 'Upcoming')";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, customerId);
            statement.setString(2, selectedDateString); 
            statement.setString(3, parseTimeTo24Hour(selectedTime));
            statement.setInt(4, partySize);

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(frame, "Reservation made successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                if (selectedButton != null) {
                    selectedButton.setEnabled(false);
                    selectedButton.setBackground(Color.GRAY);
                }
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
