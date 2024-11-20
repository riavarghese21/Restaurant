package com.restaurant;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustomerReview {

    private JFrame frame;
    private JTextField reviewTitle;
    private JTextArea reviewDescription;
    private JComboBox<Integer> starRatingComboBox;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                CustomerReview window = new CustomerReview();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the application.
     */
    public CustomerReview() {
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

        JLabel lblLeaveAReview = new JLabel("Leave a Review");
        lblLeaveAReview.setBounds(230, 10, 150, 20);
        lblLeaveAReview.setFont(new Font("Lucida Grande", Font.BOLD, 17));
        frame.getContentPane().add(lblLeaveAReview);

        JLabel lblRating = new JLabel("Star Rating:");
        lblRating.setBounds(50, 60, 80, 20);
        frame.getContentPane().add(lblRating);

        Integer[] ratings = {1, 2, 3, 4, 5};
        starRatingComboBox = new JComboBox<>(ratings);
        starRatingComboBox.setBounds(140, 60, 100, 25);
        frame.getContentPane().add(starRatingComboBox);

        JLabel lblTitle = new JLabel("Review Title:");
        lblTitle.setBounds(50, 100, 80, 20);
        frame.getContentPane().add(lblTitle);

        reviewTitle = new JTextField();
        reviewTitle.setBounds(140, 100, 400, 25);
        frame.getContentPane().add(reviewTitle);
        reviewTitle.setColumns(10);

        JLabel lblDescription = new JLabel("Description:");
        lblDescription.setBounds(50, 140, 80, 20);
        frame.getContentPane().add(lblDescription);

        reviewDescription = new JTextArea();
        reviewDescription.setWrapStyleWord(true);
        reviewDescription.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(reviewDescription);
        scrollPane.setBounds(140, 140, 400, 150);
        frame.getContentPane().add(scrollPane);

        JButton btnSubmitReview = new JButton("Submit Review");
        btnSubmitReview.setBounds(240, 320, 150, 30);
        frame.getContentPane().add(btnSubmitReview);
        
        JButton BackButton = new JButton("Back");
        BackButton.setBounds(263, 362, 117, 29);
        frame.getContentPane().add(BackButton);

        btnSubmitReview.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitReview();
            }
        });
    }

    private void submitReview() {
        int rating = (int) starRatingComboBox.getSelectedItem();
        String title = reviewTitle.getText();
        String description = reviewDescription.getText();

        try {
            Connection connection = Database.getConnection(); 
            String query = "INSERT INTO Reviews (customer_id, rating, title, description, date) VALUES (?, ?, ?, ?, CURDATE())";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, getCurrentCustomerId()); 
            statement.setString(3, title);
            statement.setString(4, description);
            statement.executeUpdate();

            JOptionPane.showMessageDialog(frame, "Review submitted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error submitting review.");
        }
    }

    private int getCurrentCustomerId() {
        return 1; 
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
}
