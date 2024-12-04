package com.restaurant.Customer;

import javax.swing.*;
import com.restaurant.Database;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.Random;

public class PurchaseGiftCard {

    private JFrame frame;
    private JTextField amountField, recipientField;
    private JButton purchaseButton;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                PurchaseGiftCard window = new PurchaseGiftCard();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public PurchaseGiftCard() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Purchase Gift Cards");
        frame.setBounds(100, 100, 500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel titleLabel = new JLabel("Purchase a Gift Card");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        titleLabel.setBounds(157, 6, 200, 30);
        frame.getContentPane().add(titleLabel);

        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        amountLabel.setBounds(78, 131, 100, 25);
        frame.getContentPane().add(amountLabel);

        amountField = new JTextField();
        amountField.setBounds(167, 130, 180, 25);
        frame.getContentPane().add(amountField);
        amountField.setColumns(10);

        JLabel recipientLabel = new JLabel("Recipient Username:");
        recipientLabel.setHorizontalAlignment(SwingConstants.CENTER);
        recipientLabel.setBounds(18, 188, 150, 25);
        frame.getContentPane().add(recipientLabel);

        recipientField = new JTextField();
        recipientField.setBounds(167, 187, 180, 25);
        frame.getContentPane().add(recipientField);
        recipientField.setColumns(10);

        purchaseButton = new JButton("Purchase Gift Card");
        purchaseButton.setBounds(167, 273, 180, 30);
        frame.getContentPane().add(purchaseButton);
        purchaseButton.addActionListener(e -> purchaseGiftCard());
        
        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setBounds(28, 322, 100, 30);
        frame.getContentPane().add(backButton);

        backButton.addActionListener(e -> {
            frame.dispose(); 
            CustomerSignedIn customerSignedInPage = new CustomerSignedIn(); 
            customerSignedInPage.setVisible(true); 
        });
    }

    private void purchaseGiftCard() {
        String amountText = amountField.getText();
        String recipientUsername = recipientField.getText();

        if (amountText.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter the gift card amount.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double amount = Double.parseDouble(amountText);
            int customerId = UserSession.getInstance().getCustomerId(); 
            String cardCode = generateGiftCardCode(); 
            String purchasedDate = new SimpleDateFormat("MM/dd/yyyy").format(new Date());

            Connection connection = Database.getConnection();
            if (connection == null) {
                throw new SQLException("Database connection failed.");
            }

            String query = "INSERT INTO GiftCards (card_code, customer_id, amount, status, purchased_date, recipient_username) VALUES (?, ?, ?, 'Active', ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, cardCode);
            statement.setInt(2, customerId);
            statement.setDouble(3, amount);
            statement.setString(4, purchasedDate);
            statement.setString(5, recipientUsername);

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(frame, "Gift card purchased successfully! Code: " + cardCode, "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to purchase gift card. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Please enter a valid amount.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "An error occurred while purchasing the gift card. Please try again.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String generateGiftCardCode() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            code.append(chars.charAt(random.nextInt(chars.length())));
        }
        return code.toString();
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
}