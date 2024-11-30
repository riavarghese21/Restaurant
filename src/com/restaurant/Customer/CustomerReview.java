package com.restaurant.Customer;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.restaurant.Database;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomerReview {

    public JFrame frame;
    private JTextField reviewTitle;
    private JTextArea reviewDescription;
    private JComboBox<Integer> starRatingComboBox;
    private JTable reviewsTable;
    private DefaultTableModel reviewsModel;
    private JLabel avgRatingLabel;

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
        loadReviews();
        calculateAverageRating();
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
        lblLeaveAReview.setBounds(346, 306, 150, 20);
        lblLeaveAReview.setFont(new Font("Lucida Grande", Font.BOLD, 17));
        frame.getContentPane().add(lblLeaveAReview);

        JLabel lblRating = new JLabel("Star Rating:");
        lblRating.setBounds(50, 351, 80, 20);
        frame.getContentPane().add(lblRating);

        Integer[] ratings = {1, 2, 3, 4, 5};
        starRatingComboBox = new JComboBox<>(ratings);
        starRatingComboBox.setBounds(142, 350, 89, 25);
        frame.getContentPane().add(starRatingComboBox);

        JLabel lblTitle = new JLabel("Review Title:");
        lblTitle.setBounds(50, 391, 80, 20);
        frame.getContentPane().add(lblTitle);

        reviewTitle = new JTextField();
        reviewTitle.setBounds(139, 388, 607, 25);
        frame.getContentPane().add(reviewTitle);
        reviewTitle.setColumns(10);

        JLabel lblDescription = new JLabel("Description:");
        lblDescription.setBounds(50, 431, 80, 20);
        frame.getContentPane().add(lblDescription);

        reviewDescription = new JTextArea();
        reviewDescription.setBounds(142, 433, 604, 40);
        frame.getContentPane().add(reviewDescription);
        reviewDescription.setWrapStyleWord(true);
        reviewDescription.setLineWrap(true);

        JButton btnSubmitReview = new JButton("Submit Review");
        btnSubmitReview.setBounds(618, 500, 150, 30);
        frame.getContentPane().add(btnSubmitReview);

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setBounds(50, 500, 100, 30);
        frame.getContentPane().add(backButton);

        backButton.addActionListener(e -> {
            frame.dispose();
            CustomerSignedIn customerSignedInPage = new CustomerSignedIn();
            customerSignedInPage.setVisible(true);
        });

        btnSubmitReview.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitReview();
                loadReviews();
                calculateAverageRating();
            }
        });

        JLabel lblAllReviews = new JLabel("Reviews");
        lblAllReviews.setHorizontalAlignment(SwingConstants.CENTER);
        lblAllReviews.setBounds(346, 6, 110, 20);
        lblAllReviews.setFont(new Font("Lucida Grande", Font.BOLD, 17));
        frame.getContentPane().add(lblAllReviews);

        reviewsModel = new DefaultTableModel(new String[]{"Rating", "Title", "Description", "Date", "Response"}, 0);
        reviewsTable = new JTable(reviewsModel);
        JScrollPane reviewsScrollPane = new JScrollPane(reviewsTable);
        reviewsScrollPane.setBounds(53, 38, 693, 226);
        frame.getContentPane().add(reviewsScrollPane);

        avgRatingLabel = new JLabel("Average Rating: ");
        avgRatingLabel.setBounds(50, 274, 300, 20);
        avgRatingLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
        frame.getContentPane().add(avgRatingLabel);
    }

    private void submitReview() {
        int rating = (int) starRatingComboBox.getSelectedItem();
        String title = reviewTitle.getText();
        String description = reviewDescription.getText();

        if (title.isEmpty() || description.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter both title and description.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Connection connection = Database.getConnection();
            String query = "INSERT INTO Reviews (customer_id, review_rating, review_title, review_description, review_date, review_response) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, getCurrentCustomerId());
            statement.setInt(2, rating);
            statement.setString(3, title);
            statement.setString(4, description);

            String formattedDate = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
            statement.setString(5, formattedDate);
            statement.setString(6, "");  // Initially, response is empty

            statement.executeUpdate();

            JOptionPane.showMessageDialog(frame, "Review submitted successfully!");

            starRatingComboBox.setSelectedIndex(0);
            reviewTitle.setText("");
            reviewDescription.setText("");

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error submitting review.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadReviews() {
        reviewsModel.setRowCount(0);
        try {
            Connection connection = Database.getConnection();
            String query = "SELECT review_rating, review_title, review_description, review_date, review_response FROM Reviews";
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet rs = statement.executeQuery();
            SimpleDateFormat displayDateFormat = new SimpleDateFormat("MM/dd/yyyy");
            while (rs.next()) {
                int rating = rs.getInt("review_rating");
                String title = rs.getString("review_title");
                String description = rs.getString("review_description");
                String date = rs.getString("review_date");
                String response = rs.getString("review_response");

                Date parsedDate = new SimpleDateFormat("MM/dd/yyyy").parse(date);
                String formattedDate = displayDateFormat.format(parsedDate);

                reviewsModel.addRow(new Object[]{rating + " stars", title, description, formattedDate, response});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void calculateAverageRating() {
        try {
            Connection connection = Database.getConnection();
            String query = "SELECT AVG(review_rating) AS average_rating FROM Reviews";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                double averageRating = rs.getDouble("average_rating");
                avgRatingLabel.setText("Average Rating: " + String.format("%.2f", averageRating) + " stars");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int getCurrentCustomerId() {
        return UserSession.getInstance().getCustomerId();
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
}
