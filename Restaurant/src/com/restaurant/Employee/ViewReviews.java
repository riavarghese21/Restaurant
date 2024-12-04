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
import java.util.HashMap;
import java.util.Map;

public class ViewReviews {

    public JFrame frame;
    private JTable reviewsTable;
    private DefaultTableModel tableModel;
    private JTextArea responseArea;
    private JButton respondButton;
    private int selectedReviewId = -1;

    private Map<Integer, Integer> reviewIdMap = new HashMap<>();

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ViewReviews window = new ViewReviews();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public ViewReviews() {
        initialize();
        loadReviews();
    }

    public void initialize() {
        frame = new JFrame("View Reviews");
        frame.setBounds(100, 100, 900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null); 

        JLabel lblTitle = new JLabel("Customer Reviews");
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 17));
        lblTitle.setBounds(370, 20, 200, 30);
        frame.getContentPane().add(lblTitle);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(50, 70, 800, 250);
        frame.getContentPane().add(scrollPane);

        tableModel = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Customer ID", "Rating", "Title", "Description", "Date", "Response"}
        );

        reviewsTable = new JTable(tableModel);  
        reviewsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPane.setViewportView(reviewsTable);

        JLabel lblResponse = new JLabel("Write Response:");
        lblResponse.setBounds(50, 350, 150, 20);
        frame.getContentPane().add(lblResponse);

        responseArea = new JTextArea();
        responseArea.setWrapStyleWord(true);
        responseArea.setLineWrap(true);
        JScrollPane responseScrollPane = new JScrollPane(responseArea);
        responseScrollPane.setBounds(50, 380, 800, 100);
        frame.getContentPane().add(responseScrollPane);

        respondButton = new JButton("Respond to Review");
        respondButton.setBounds(375, 500, 150, 30);
        frame.getContentPane().add(respondButton);
        respondButton.addActionListener(e -> respondToReview());

        JButton backButton = new JButton("Back");
        backButton.setBounds(50, 500, 100, 30);
        frame.getContentPane().add(backButton);
        backButton.addActionListener(e -> {
            frame.dispose();
            EmployeeMenu employeeMenu = new EmployeeMenu();
            employeeMenu.setVisible(true);
        });

        reviewsTable.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && reviewsTable.getSelectedRow() != -1) {
                selectedReviewId = reviewIdMap.get(reviewsTable.getSelectedRow());
                String response = (String) tableModel.getValueAt(reviewsTable.getSelectedRow(), 5); 
                responseArea.setText(response == null ? "" : response);
            }
        });
    }

    private void loadReviews() {
        try {
            Connection connection = Database.getConnection();
            if (connection == null) {
                JOptionPane.showMessageDialog(frame, "Database connection failed.", "Database Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String query = "SELECT * FROM Reviews";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            tableModel.setRowCount(0); 
            reviewIdMap.clear(); 

            int rowIndex = 0;
            while (resultSet.next()) {
                int reviewId = resultSet.getInt("review_id"); 
                int customerId = resultSet.getInt("customer_id");
                int rating = resultSet.getInt("review_rating");
                String title = resultSet.getString("review_title");
                String description = resultSet.getString("review_description");
                String date = resultSet.getString("review_date");
                String response = resultSet.getString("review_response");

                tableModel.addRow(new Object[]{customerId, rating, title, description, date, response});

                reviewIdMap.put(rowIndex, reviewId);
                rowIndex++;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "An error occurred while loading reviews.", "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void respondToReview() {
        if (selectedReviewId == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a review to respond to.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String responseText = responseArea.getText().trim();
        if (responseText.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter a response.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Connection connection = Database.getConnection();
            if (connection == null) {
                JOptionPane.showMessageDialog(frame, "Database connection failed.", "Database Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String updateQuery = "UPDATE Reviews SET review_response = ? WHERE review_id = ?";
            PreparedStatement statement = connection.prepareStatement(updateQuery);
            statement.setString(1, responseText);
            statement.setInt(2, selectedReviewId);
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(frame, "Response saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadReviews();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "An error occurred while saving the response.", "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public JFrame getFrame() {
        return frame;
    }
}
