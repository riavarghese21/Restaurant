package com.restaurant.Customer;

import java.awt.EventQueue;
import javax.swing.*;
import com.restaurant.Database;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class PurchaseGiftCard {

    public JFrame frame;
    private JTextField giftCardAmountField;
    private JTextField recipientEmailField;
    private JRadioButton forSelfRadioButton, forOthersRadioButton;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    PurchaseGiftCard window = new PurchaseGiftCard();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public PurchaseGiftCard() {
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

        JLabel lblPurchaseGiftCard = new JLabel("Purchase a Gift Card");
        lblPurchaseGiftCard.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblPurchaseGiftCard.setBounds(300, 30, 200, 30);
        frame.getContentPane().add(lblPurchaseGiftCard);

        // Amount Label and Field
        JLabel lblAmount = new JLabel("Gift Card Amount ($):");
        lblAmount.setBounds(100, 100, 150, 25);
        frame.getContentPane().add(lblAmount);

        giftCardAmountField = new JTextField();
        giftCardAmountField.setBounds(260, 100, 200, 25);
        frame.getContentPane().add(giftCardAmountField);
        giftCardAmountField.setColumns(10);

        // Radio Buttons for Gift Card Usage
        forSelfRadioButton = new JRadioButton("For Myself");
        forSelfRadioButton.setBounds(100, 150, 200, 25);
        frame.getContentPane().add(forSelfRadioButton);

        forOthersRadioButton = new JRadioButton("For Someone Else");
        forOthersRadioButton.setBounds(300, 150, 200, 25);
        frame.getContentPane().add(forOthersRadioButton);

        ButtonGroup giftCardGroup = new ButtonGroup();
        giftCardGroup.add(forSelfRadioButton);
        giftCardGroup.add(forOthersRadioButton);

        // Recipient Email Label and Field (only used if gifting to someone else)
        JLabel lblRecipientEmail = new JLabel("Recipient Email:");
        lblRecipientEmail.setBounds(100, 200, 150, 25);
        frame.getContentPane().add(lblRecipientEmail);

        recipientEmailField = new JTextField();
        recipientEmailField.setBounds(260, 200, 200, 25);
        frame.getContentPane().add(recipientEmailField);
        recipientEmailField.setColumns(10);
        recipientEmailField.setEnabled(false); // Initially disabled

        forOthersRadioButton.addActionListener(e -> recipientEmailField.setEnabled(true));
        forSelfRadioButton.addActionListener(e -> recipientEmailField.setEnabled(false));

        // Purchase Button
        JButton purchaseButton = new JButton("Purchase Gift Card");
        purchaseButton.setBounds(300, 300, 200, 30);
        frame.getContentPane().add(purchaseButton);

        purchaseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                purchaseGiftCard();
            }
        });

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setBounds(100, 500, 100, 30);
        frame.getContentPane().add(backButton);
        backButton.addActionListener(e -> {
            frame.dispose();
            CustomerSignedIn customerSignedInPage = new CustomerSignedIn();
            customerSignedInPage.setVisible(true);
        });
    }

    /**
     * Method to handle purchasing the gift card
     */
    private void purchaseGiftCard() {
        try {
            // Get the gift card amount
            String amountText = giftCardAmountField.getText();
            double amount = Double.parseDouble(amountText);

            // Get recipient information
            String recipientEmail = null;
            if (forSelfRadioButton.isSelected()) {
                recipientEmail = getCurrentUserEmail(); // Use current user's email for personal use
            } else if (forOthersRadioButton.isSelected()) {
                recipientEmail = recipientEmailField.getText();
                if (recipientEmail.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter recipient's email.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please select who the gift card is for.", "Invalid Selection", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Generate a unique gift card code
            String giftCardCode = UUID.randomUUID().toString().substring(0, 8).toUpperCase();

            // Save the gift card details in the database
            Connection connection = Database.getConnection();
            if (connection == null) {
                JOptionPane.showMessageDialog(frame, "Database connection failed.", "Database Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String insertQuery = "INSERT INTO GiftCards (code, amount, recipient_email, status) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, giftCardCode);
            preparedStatement.setDouble(2, amount);
            preparedStatement.setString(3, recipientEmail);
            preparedStatement.setString(4, "Active");

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(frame, "Gift card purchased successfully! Gift Card Code: " + giftCardCode, "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to purchase gift card. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Please enter a valid amount.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "An error occurred while processing your request. Please try again.", "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * Placeholder method to get the current user's email address
     * Replace with actual implementation to get the current logged-in user's email
     */
    private String getCurrentUserEmail() {
        // Assuming the current user's email is available after signing in
        return "user@example.com";
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
}
