package com.restaurant.Customer;

import javax.swing.*;
import com.restaurant.Database;
import com.restaurant.Encryption;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class PurchaseGiftCard {

    private JFrame frame;
    private JTextField amountField, recipientField, CardNumber, FirstName, LastName, CVV, BillingAddress, City, ZipCode;
    private JComboBox<String> CardType, StateComboBox, ExpirationMonthComboBox;
    private JComboBox<Integer> ExpirationYearComboBox;
    private JButton purchaseButton, StoreButton, loadSavedCardButton;

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
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel titleLabel = new JLabel("Purchase a Gift Card");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Lucida Grande", Font.BOLD, 17));
        titleLabel.setBounds(318, 6, 200, 30);
        frame.getContentPane().add(titleLabel);

        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        amountLabel.setBounds(216, 60, 100, 25);
        frame.getContentPane().add(amountLabel);

        amountField = new JTextField();
        amountField.setBounds(328, 59, 180, 25);
        frame.getContentPane().add(amountField);
        amountField.setColumns(10);

        JLabel recipientLabel = new JLabel("Recipient Username:");
        recipientLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        recipientLabel.setBounds(191, 100, 130, 25);
        frame.getContentPane().add(recipientLabel);

        recipientField = new JTextField();
        recipientField.setBounds(328, 99, 180, 25);
        frame.getContentPane().add(recipientField);
        recipientField.setColumns(10);

        // Payment Information Section (copied from MakePayment)
        JLabel paymentInfoLabel = new JLabel("Payment Information:");
        paymentInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        paymentInfoLabel.setFont(new Font("Lucida Grande", Font.BOLD, 17));
        paymentInfoLabel.setBounds(318, 150, 200, 25);
        frame.getContentPane().add(paymentInfoLabel);

        // Card Type ComboBox
        String[] cardTypes = {"Card Type", "Discover", "MasterCard", "Visa"};
        CardType = new JComboBox<>(cardTypes);
        CardType.setBounds(160, 190, 480, 25);
        frame.getContentPane().add(CardType);

        // Card Number Field
        CardNumber = new JTextField("Card Number");
        CardNumber.setHorizontalAlignment(SwingConstants.LEFT);
        CardNumber.setBounds(160, 230, 480, 25);
        addPlaceholder(CardNumber, "Card Number");
        frame.getContentPane().add(CardNumber);

        // Expiration Month ComboBox
        String[] months = {"Month", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        ExpirationMonthComboBox = new JComboBox<>(months);
        ExpirationMonthComboBox.setBounds(237, 270, 130, 25);
        frame.getContentPane().add(ExpirationMonthComboBox);

        // Expiration Year ComboBox
        Integer[] years = new Integer[22];
        int startYear = 2024;
        for (int i = 0; i < years.length; i++) {
            years[i] = startYear + i;
        }
        ExpirationYearComboBox = new JComboBox<>(years);
        ExpirationYearComboBox.setBounds(379, 270, 109, 25);
        frame.getContentPane().add(ExpirationYearComboBox);

        FirstName = new JTextField("First Name");
        FirstName.setBounds(191, 307, 190, 25);
        addPlaceholder(FirstName, "First Name");
        frame.getContentPane().add(FirstName);

        LastName = new JTextField("Last Name");
        LastName.setBounds(430, 307, 210, 25);
        addPlaceholder(LastName, "Last Name");
        frame.getContentPane().add(LastName);

        CVV = new JTextField("CVV");
        CVV.setBounds(500, 270, 140, 25);
        addPlaceholder(CVV, "CVV");
        frame.getContentPane().add(CVV);

        BillingAddress = new JTextField("Billing Address");
        BillingAddress.setBounds(191, 350, 190, 25);
        addPlaceholder(BillingAddress, "Billing Address");
        frame.getContentPane().add(BillingAddress);

        City = new JTextField("City");
        City.setBounds(430, 350, 210, 25);
        addPlaceholder(City, "City");
        frame.getContentPane().add(City);

        ZipCode = new JTextField("Zip Code");
        ZipCode.setBounds(430, 390, 210, 25);
        addPlaceholder(ZipCode, "Zip Code");
        frame.getContentPane().add(ZipCode);

        // State ComboBox
        String[] states = {"State", "Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware",
                "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana",
                "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana",
                "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina",
                "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina",
                "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia",
                "Wisconsin", "Wyoming"};
        StateComboBox = new JComboBox<>(states);
        StateComboBox.setBounds(191, 392, 190, 25);
        frame.getContentPane().add(StateComboBox);

        // Save Card Button
        StoreButton = new JButton("Save Card");
        StoreButton.setBounds(196, 430, 120, 30);
        frame.getContentPane().add(StoreButton);
        StoreButton.addActionListener(e -> savePaymentInfo());

        // Load Saved Card Button
        loadSavedCardButton = new JButton("Load Saved Card");
        loadSavedCardButton.setBounds(328, 430, 140, 30);
        frame.getContentPane().add(loadSavedCardButton);
        loadSavedCardButton.addActionListener(e -> loadSavedPaymentInfo());

        // Purchase Button
        purchaseButton = new JButton("Purchase Gift Card");
        purchaseButton.setBounds(495, 430, 145, 30);
        frame.getContentPane().add(purchaseButton);
        purchaseButton.addActionListener(e -> purchaseGiftCard());

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setBounds(30, 530, 100, 30);
        frame.getContentPane().add(backButton);
        
        JLabel ExpirationLabel = new JLabel("Expiration:");
        ExpirationLabel.setBounds(162, 273, 85, 16);
        frame.getContentPane().add(ExpirationLabel);
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

        if (recipientUsername.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter the recipient's username.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
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

            String checkUserQuery = "SELECT COUNT(*) FROM Customers WHERE customer_username = ?";
            PreparedStatement checkUserStatement = connection.prepareStatement(checkUserQuery);
            checkUserStatement.setString(1, recipientUsername);
            ResultSet userResult = checkUserStatement.executeQuery();
            userResult.next();
            int userCount = userResult.getInt(1);

            if (userCount == 0) {
                JOptionPane.showMessageDialog(frame, "Recipient username does not exist. Please enter a valid username.", "Invalid Recipient", JOptionPane.ERROR_MESSAGE);
                return;
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

    private void savePaymentInfo() {
        // The implementation is the same as in the MakePayment class
        try {
            Connection connection = Database.getConnection();
            if (connection == null) {
                JOptionPane.showMessageDialog(frame, "Database connection failed.", "Database Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String cardType = (String) CardType.getSelectedItem();
            String cardNumber = CardNumber.getText();
            String firstName = FirstName.getText();
            String lastName = LastName.getText();
            String cvv = CVV.getText();
            String billingAddress = BillingAddress.getText();
            String city = City.getText();
            String state = (String) StateComboBox.getSelectedItem();
            String zipCode = ZipCode.getText();
            String expirationMonth = (String) ExpirationMonthComboBox.getSelectedItem();
            String expirationYear = ExpirationYearComboBox.getSelectedItem() != null ? ExpirationYearComboBox.getSelectedItem().toString() : "";
            int customerId = UserSession.getInstance().getCustomerId();

            // Validate input
            if (cardType.equals("Card Type") || state.equals("State") || expirationMonth.equals("Month") || expirationYear.isEmpty()
                    || cardNumber.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || cvv.isEmpty() || billingAddress.isEmpty() || city.isEmpty() || zipCode.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill in all card details.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Encrypt sensitive fields
            cardNumber = Encryption.encrypt(cardNumber);
            cvv = Encryption.encrypt(cvv);
            expirationMonth = Encryption.encrypt(expirationMonth);
            expirationYear = !expirationYear.isEmpty() ? Encryption.encrypt(expirationYear) : "";

            // Insert statement should use VARCHAR types for encrypted fields in your database schema
            String insertPaymentInfoQuery = "INSERT INTO PaymentInfo (customer_id, card_type, card_number, expiration_month, expiration_year, first_name, last_name, cvv, billing_address, city, state, zip_code) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement paymentStatement = connection.prepareStatement(insertPaymentInfoQuery);
            paymentStatement.setInt(1, customerId);
            paymentStatement.setString(2, cardType);
            paymentStatement.setString(3, cardNumber);  // Encrypted card number
            paymentStatement.setString(4, expirationMonth);  // Encrypted expiration month
            paymentStatement.setString(5, expirationYear);  // Encrypted expiration year
            paymentStatement.setString(6, firstName);
            paymentStatement.setString(7, lastName);
            paymentStatement.setString(8, cvv);  // Encrypted CVV
            paymentStatement.setString(9, billingAddress);
            paymentStatement.setString(10, city);
            paymentStatement.setString(11, state);
            paymentStatement.setString(12, zipCode);

            int rowsInserted = paymentStatement.executeUpdate();

            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(frame, "Payment information saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to save payment information. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "An error occurred while saving payment information. Please try again.", "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid number format. Please check your inputs.", "Input Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void loadSavedPaymentInfo() {
        // The implementation is the same as in the MakePayment class
        try {
            Connection connection = Database.getConnection();
            if (connection == null) {
                JOptionPane.showMessageDialog(frame, "Database connection failed.", "Database Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int customerId = UserSession.getInstance().getCustomerId();
            String selectPaymentInfoQuery = "SELECT card_type, card_number, expiration_month, expiration_year, first_name, last_name, cvv, billing_address, city, state, zip_code FROM PaymentInfo WHERE customer_id = ?";
            PreparedStatement paymentStatement = connection.prepareStatement(selectPaymentInfoQuery);
            paymentStatement.setInt(1, customerId);
            ResultSet resultSet = paymentStatement.executeQuery();

            if (resultSet.next()) {
                // Set the UI elements with values from the database and decrypt where necessary
                CardType.setSelectedItem(resultSet.getString("card_type"));
                CardNumber.setText(Encryption.decrypt(resultSet.getString("card_number")));
                ExpirationMonthComboBox.setSelectedItem(Encryption.decrypt(resultSet.getString("expiration_month")));
                ExpirationYearComboBox.setSelectedItem(Integer.parseInt(Encryption.decrypt(resultSet.getString("expiration_year"))));
                FirstName.setText(resultSet.getString("first_name"));
                LastName.setText(resultSet.getString("last_name"));
                CVV.setText(Encryption.decrypt(resultSet.getString("cvv")));
                BillingAddress.setText(resultSet.getString("billing_address"));
                City.setText(resultSet.getString("city"));
                StateComboBox.setSelectedItem(resultSet.getString("state"));
                ZipCode.setText(resultSet.getString("zip_code"));

                JOptionPane.showMessageDialog(frame, "Payment information loaded successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "No saved card found for this user.", "No Card Found", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "An error occurred while loading payment information. Please try again.", "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
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

    private void addPlaceholder(JTextField textField, String placeholder) {
        textField.setForeground(Color.GRAY);
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.GRAY);
                    textField.setText(placeholder);
                }
            }
        });
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
}
