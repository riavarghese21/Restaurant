package com.restaurant.Employee;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.restaurant.Database;
import java.awt.Font;

public class ViewReviews {

    private JFrame frame;
    private JList<String> reviewList;
    private JTextArea responseArea;
    private DefaultListModel<String> reviewListModel;
    private int selectedReviewId = -1; // To keep track of selected review's ID

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ViewReviews window = new ViewReviews();
                window.getFrame().setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the application.
     */
    public ViewReviews() {
        initialize();
    }

    /**
     * Initialize the contents of the frame using Absolute Layout.
     */
    private void initialize() {
        frame = new JFrame("Respond to Reviews");
        frame.setTitle("");
        frame.setBounds(100, 100, 600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Review List
        reviewListModel = new DefaultListModel<>();
        frame.getContentPane().setLayout(null);
        JScrollPane listScrollPane = new JScrollPane();
        listScrollPane.setBounds(35, 68, 530, 130);
        frame.getContentPane().add(listScrollPane);
        reviewList = new JList<>(reviewListModel);
        listScrollPane.setViewportView(reviewList);
        reviewList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        reviewList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
                // List selection listener to load selected review
                reviewList.addListSelectionListener(new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        if (!e.getValueIsAdjusting() && reviewList.getSelectedIndex() != -1) {
                            loadSelectedReviewResponse();
                        }
                    }
                });
        JScrollPane responseScrollPane = new JScrollPane();
        responseScrollPane.setBounds(35, 221, 530, 106);
        frame.getContentPane().add(responseScrollPane);
        
                // Response Text Area
                responseArea = new JTextArea();
                responseScrollPane.setColumnHeaderView(responseArea);
                responseArea.setWrapStyleWord(true);
                responseArea.setLineWrap(true);

        // Button to Save Response
        JButton saveResponseButton = new JButton("Save Response");
        saveResponseButton.setBounds(242, 370, 150, 30);
        frame.getContentPane().add(saveResponseButton);

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setBounds(19, 424, 100, 30);
        frame.getContentPane().add(backButton);
                
                JLabel viewReviewsLabel = new JLabel("View Reviews");
                viewReviewsLabel.setBounds(242, 11, 124, 15);
                viewReviewsLabel.setFont(new Font("Lucida Grande", Font.BOLD, 17));
                viewReviewsLabel.setHorizontalAlignment(SwingConstants.CENTER);
                frame.getContentPane().add(viewReviewsLabel);

        backButton.addActionListener(e -> {
            frame.dispose();
            EmployeeMenu employeeMenu = new EmployeeMenu();
            employeeMenu.setVisible(true);
        });

        // Load reviews into list
        loadReviews();

        // Save response button action listener
        saveResponseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveReviewResponse();
            }
        });
    }

    /**
     * Load reviews from the database into the review list.
     */
    private void loadReviews() {
        try {
            Connection connection = Database.getConnection();
            if (connection == null) {
                JOptionPane.showMessageDialog(frame, "Database connection failed.", "Database Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String query = "SELECT review_id, review_title FROM Reviews WHERE review_response IS NULL OR review_response = ''";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            reviewListModel.clear();
            while (resultSet.next()) {
                int reviewId = resultSet.getInt("review_id");
                String reviewTitle = resultSet.getString("review_title");
                reviewListModel.addElement("Review #" + reviewId + ": " + reviewTitle);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error loading reviews from the database.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Load the selected review response for editing.
     */
    private void loadSelectedReviewResponse() {
        try {
            String selectedValue = reviewList.getSelectedValue();
            if (selectedValue == null) return;

            // Extracting review ID from the selected value text
            String[] parts = selectedValue.split(":");
            selectedReviewId = Integer.parseInt(parts[0].replace("Review #", "").trim());

            Connection connection = Database.getConnection();
            if (connection == null) {
                JOptionPane.showMessageDialog(frame, "Database connection failed.", "Database Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String query = "SELECT review_response FROM Reviews WHERE review_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, selectedReviewId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String response = resultSet.getString("review_response");
                responseArea.setText(response != null ? response : "");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error loading the selected review response.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Save the response to the selected review.
     */
    private void saveReviewResponse() {
        if (selectedReviewId == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a review to respond to.", "No Review Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String responseText = responseArea.getText().trim();
        if (responseText.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Response text cannot be empty.", "Invalid Response", JOptionPane.WARNING_MESSAGE);
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
                JOptionPane.showMessageDialog(frame, "Response saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadReviews();
                responseArea.setText("");
                selectedReviewId = -1; // Reset selection
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to save response. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "An error occurred while saving the response.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Getter method for frame.
     */
    public JFrame getFrame() {
        return frame;
    }
}
