package com.restaurant;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewReviews {

    private JFrame frame;
    private JTable reviewsTable;
    private DefaultTableModel reviewsModel;
    private JLabel avgRatingLabel;

    /**
     * Launch the application.
     */
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

    /**
     * Create the application.
     */
    public ViewReviews() {
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

        JLabel lblAllReviews = new JLabel("All Reviews");
        lblAllReviews.setFont(new Font("Lucida Grande", Font.BOLD, 17));
        lblAllReviews.setBounds(350, 10, 110, 20);
        frame.getContentPane().add(lblAllReviews);

        reviewsModel = new DefaultTableModel(new String[]{"Rating", "Title", "Description", "Date"}, 0);
        reviewsTable = new JTable(reviewsModel);
        JScrollPane reviewsScrollPane = new JScrollPane(reviewsTable);
        reviewsScrollPane.setBounds(50, 50, 700, 400);
        frame.getContentPane().add(reviewsScrollPane);

        avgRatingLabel = new JLabel("Average Rating: ");
        avgRatingLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
        avgRatingLabel.setBounds(50, 470, 300, 20);
        frame.getContentPane().add(avgRatingLabel);
    }

    private void loadReviews() {
        try {
            Connection connection = Database.getConnection(); 
            String query = "SELECT rating, title, description, date FROM Reviews";
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int rating = rs.getInt("rating");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String date = rs.getString("date");
                reviewsModel.addRow(new Object[]{rating + " stars", title, description, date});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void calculateAverageRating() {
        try {
            Connection connection = Database.getConnection();
            String query = "SELECT AVG(rating) AS average_rating FROM Reviews";
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

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
}
