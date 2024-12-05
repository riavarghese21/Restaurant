package com.restaurant.Customer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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

public class MakePayment {

    private JFrame frame;
    private JTextField CardNumber, FirstName, LastName, CVV, BillingAddress, City, ZipCode, GiftCardCodeField;
    private JComboBox<String> CardType, StateComboBox, ExpirationMonthComboBox;
    private JComboBox<Integer> ExpirationYearComboBox;
    private JButton StoreButton, ApplyGiftCardButton;
    private JLabel TotalAmountLabel;
    private double orderTotalAmount = 0.00;
    private double appliedGiftCardValue = 0;
    private boolean isPaymentMade = false;
    private JLabel ExpirationLabel;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                MakePayment window = new MakePayment(0.0); // Default value for demonstration
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    public MakePayment(double orderTotalAmount) {
        this.orderTotalAmount = orderTotalAmount;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Make Payment");
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel MakePaymentLabel = new JLabel("Make Payment");
        MakePaymentLabel.setFont(new Font("Lucida Grande", Font.BOLD, 17));
        MakePaymentLabel.setBounds(347, 6, 130, 29);
        frame.getContentPane().add(MakePaymentLabel);
        
        TotalAmountLabel = new JLabel();
        TotalAmountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        TotalAmountLabel.setBounds(254, 113, 300, 26);
        frame.getContentPane().add(TotalAmountLabel);

        TotalAmountLabel.setText("Total Amount: $" + String.format("%.2f", orderTotalAmount));
        
        // Back Button
        JButton BackButton = new JButton("Back");
        BackButton.setBounds(30, 526, 80, 25);
        frame.getContentPane().add(BackButton);
        BackButton.addActionListener(e -> {
            frame.dispose();
            Cart cartPage = new Cart();
            cartPage.main(null);
        });

        // Place Order Button
        JButton PlaceOrderButton = new JButton("Place Order");
        PlaceOrderButton.setBounds(510, 466, 117, 29);
        frame.getContentPane().add(PlaceOrderButton);

        // Gift Card Code Field
        JLabel GiftCardLabel = new JLabel("Gift Card Code:");
        GiftCardLabel.setBounds(172, 75, 100, 26);
        frame.getContentPane().add(GiftCardLabel);

        GiftCardCodeField = new JTextField();
        GiftCardCodeField.setBounds(273, 75, 245, 26);
        frame.getContentPane().add(GiftCardCodeField);
        GiftCardCodeField.setColumns(10);

        // Apply Gift Card Button
        ApplyGiftCardButton = new JButton("Apply Gift Card");
        ApplyGiftCardButton.setBounds(520, 75, 140, 26);
        frame.getContentPane().add(ApplyGiftCardButton);
        ApplyGiftCardButton.addActionListener(e -> applyGiftCard());

        // Total Amount Label
        TotalAmountLabel = new JLabel("Total Amount: $" + String.format("%.2f", orderTotalAmount));
        TotalAmountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        TotalAmountLabel.setBounds(254, 113, 300, 26);
        frame.getContentPane().add(TotalAmountLabel);

        // Card Type ComboBox
        String[] cardTypes = {"Card Type", "Discover", "MasterCard", "Visa"};
        CardType = new JComboBox<>(cardTypes);
        CardType.setBounds(172, 151, 488, 29);
        frame.getContentPane().add(CardType);

        // Card Number Field
        CardNumber = new JTextField("Card Number");
        CardNumber.setHorizontalAlignment(SwingConstants.LEFT);
        CardNumber.setBounds(172, 199, 488, 29);
        addPlaceholder(CardNumber, "Card Number");
        frame.getContentPane().add(CardNumber);
        CardNumber.setColumns(10);

        // Expiration Month ComboBox
        String[] months = {"Month", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        ExpirationMonthComboBox = new JComboBox<>(months);
        ExpirationMonthComboBox.setBounds(226, 240, 140, 29);
        frame.getContentPane().add(ExpirationMonthComboBox);

        // Expiration Year ComboBox
        Integer[] years = new Integer[22];
        int startYear = 2024;
        for (int i = 0; i < years.length; i++) {
            years[i] = startYear + i;
        }
        ExpirationYearComboBox = new JComboBox<>(years);
        ExpirationYearComboBox.setBounds(378, 240, 140, 29);
        frame.getContentPane().add(ExpirationYearComboBox);

        // First Name Field
        FirstName = new JTextField("First Name");
        FirstName.setBounds(172, 292, 190, 26);
        addPlaceholder(FirstName, "First Name");
        frame.getContentPane().add(FirstName);
        FirstName.setColumns(10);

        // Last Name Field
        LastName = new JTextField("Last Name");
        LastName.setBounds(436, 292, 224, 26);
        addPlaceholder(LastName, "Last Name");
        frame.getContentPane().add(LastName);
        LastName.setColumns(10);

        // CVV Field
        CVV = new JTextField("CVV");
        CVV.setBounds(530, 240, 130, 26);
        addPlaceholder(CVV, "CVV");
        frame.getContentPane().add(CVV);
        CVV.setColumns(10);

        // Billing Address Field
        BillingAddress = new JTextField("Billing Address");
        BillingAddress.setBounds(172, 342, 190, 26);
        addPlaceholder(BillingAddress, "Billing Address");
        frame.getContentPane().add(BillingAddress);
        BillingAddress.setColumns(10);

        // City Field
        City = new JTextField("City");
        City.setBounds(436, 342, 224, 26);
        addPlaceholder(City, "City");
        frame.getContentPane().add(City);
        City.setColumns(10);

        // ZipCode Field
        ZipCode = new JTextField("Zip Code");
        ZipCode.setBounds(436, 399, 224, 29);
        addPlaceholder(ZipCode, "Zip Code");
        frame.getContentPane().add(ZipCode);
        ZipCode.setColumns(10);

        // State ComboBox
        String[] states = {"State", "Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware",
                "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana",
                "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana",
                "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina",
                "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina",
                "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia",
                "Wisconsin", "Wyoming"};
        StateComboBox = new JComboBox<>(states);
        StateComboBox.setBounds(172, 401, 194, 26);
        frame.getContentPane().add(StateComboBox);

        StoreButton = new JButton("Save Card");
        StoreButton.setBounds(213, 466, 117, 29);
        frame.getContentPane().add(StoreButton);

        ExpirationLabel = new JLabel("Expiration:");
        ExpirationLabel.setBounds(139, 245, 85, 16);
        frame.getContentPane().add(ExpirationLabel);

        StoreButton.addActionListener(e -> savePaymentInfo());

        // Load Saved Card Button
        JButton loadSavedCardButton = new JButton("Load Saved Card");
        loadSavedCardButton.setBounds(344, 466, 133, 29);
        frame.getContentPane().add(loadSavedCardButton);
        loadSavedCardButton.addActionListener(e -> loadSavedPaymentInfo());

        // Place Order Button
        PlaceOrderButton.addActionListener(e -> {
            double finalAmount = orderTotalAmount - appliedGiftCardValue;

            // Ensure the final amount is properly validated
            if (finalAmount < 0) {
                finalAmount = 0; // Ensures total cannot be negative
            }

            if (finalAmount > 0 && !isPaymentMade) {
                // If there's a remaining amount and no saved payment information, show an error
                JOptionPane.showMessageDialog(frame, "Please save credit card information before placing the order. Gift card does not cover the full amount.", "Payment Required", JOptionPane.ERROR_MESSAGE);
                return;
            }

            processOrder(finalAmount);
        });
    }

    private void savePaymentInfo() {
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
            int customerId = getCurrentCustomerId();

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
                isPaymentMade = true;
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
        try {
            Connection connection = Database.getConnection();
            if (connection == null) {
                JOptionPane.showMessageDialog(frame, "Database connection failed.", "Database Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int customerId = getCurrentCustomerId();
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
                isPaymentMade = true;  // Mark payment as made when card details are loaded successfully
            } else {
                JOptionPane.showMessageDialog(frame, "No saved card found for this user.", "No Card Found", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "An error occurred while loading payment information. Please try again.", "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void applyGiftCard() {
        String giftCardCode = GiftCardCodeField.getText().trim();
        if (giftCardCode.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter a gift card code.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Connection connection = Database.getConnection();
            int customerId = getCurrentCustomerId();
            String query = "SELECT amount FROM GiftCards WHERE card_code = ? AND status = 'Active' AND recipient_username = (SELECT customer_username FROM Customers WHERE customer_id = ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, giftCardCode);
            preparedStatement.setInt(2, customerId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                appliedGiftCardValue = resultSet.getDouble("amount");
                double remainingAmount = orderTotalAmount - appliedGiftCardValue;

                if (remainingAmount < 0) {
                    remainingAmount = 0;
                }

                TotalAmountLabel.setText("Total Amount Remaining: $" + String.format("%.2f", remainingAmount));
                JOptionPane.showMessageDialog(frame, "Gift card applied successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                isPaymentMade = true;

                double newGiftCardBalance = appliedGiftCardValue - orderTotalAmount;

                // Update the gift card status if the balance is fully used
                if (newGiftCardBalance <= 0) {
                    String updateStatusQuery = "UPDATE GiftCards SET amount = 0, status = 'Redeemed' WHERE card_code = ?";
                    PreparedStatement updateStatement = connection.prepareStatement(updateStatusQuery);
                    updateStatement.setString(1, giftCardCode);
                    updateStatement.executeUpdate();
                } else {
                    // Update the remaining amount on the gift card
                    String updateAmountQuery = "UPDATE GiftCards SET amount = ? WHERE card_code = ?";
                    PreparedStatement updateAmountStatement = connection.prepareStatement(updateAmountQuery);
                    updateAmountStatement.setDouble(1, newGiftCardBalance);
                    updateAmountStatement.setString(2, giftCardCode);
                    updateAmountStatement.executeUpdate();
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid or inactive gift card code, or this gift card does not belong to you.", "Invalid Gift Card", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "An error occurred while applying the gift card. Please try again.", "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private double calculateTotalFromCart() {
        double totalCost = 0.0;
        Cart cartPage = Cart.getInstance();
        DefaultTableModel cartModel = cartPage.model;

        for (int i = 0; i < cartModel.getRowCount(); i++) {
            String totalText = cartModel.getValueAt(i, 3).toString().replace("$", "");
            totalCost += Double.parseDouble(totalText);
        }

        return totalCost;
    }

    private int getCurrentCustomerId() {
        return UserSession.getInstance().getCustomerId();
    }

    private void processOrder(double finalAmount) {
        try {
            Connection connection = Database.getConnection();
            if (connection == null) {
                JOptionPane.showMessageDialog(frame, "Database connection failed.", "Database Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int customerId = getCurrentCustomerId();

            // Validate customer exists
            String checkCustomerQuery = "SELECT COUNT(*) FROM Customers WHERE customer_id = ?";
            PreparedStatement checkCustomerStatement = connection.prepareStatement(checkCustomerQuery);
            checkCustomerStatement.setInt(1, customerId);
            ResultSet customerResult = checkCustomerStatement.executeQuery();
            if (customerResult.next() && customerResult.getInt(1) == 0) {
                JOptionPane.showMessageDialog(frame, "Customer does not exist. Please log in again.", "Customer Not Found", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Insert into Orders table
            String insertOrderQuery = "INSERT INTO Orders (customer_id, order_date, total_amount, order_status) VALUES (?, ?, ?, ?)";
            PreparedStatement orderStatement = connection.prepareStatement(insertOrderQuery, PreparedStatement.RETURN_GENERATED_KEYS);

            String orderDate = new SimpleDateFormat("MM/d/yyyy HH:mm:ss").format(new Date());
            orderStatement.setInt(1, customerId);
            orderStatement.setString(2, orderDate);
            orderStatement.setDouble(3, finalAmount);
            orderStatement.setString(4, "Processing");

            int rowsInserted = orderStatement.executeUpdate();

            if (rowsInserted > 0) {
                ResultSet generatedKeys = orderStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int orderId = generatedKeys.getInt(1);

                    // Get the cart items and save them to OrderItems table
                    Cart cartPage = Cart.getInstance();
                    DefaultTableModel cartModel = cartPage.model;

                    for (int i = 0; i < cartModel.getRowCount(); i++) {
                        int itemId = Integer.parseInt(cartModel.getValueAt(i, 0).toString()); // Get item ID from first column
                        int quantity = Integer.parseInt(cartModel.getValueAt(i, 3).toString()); // Get quantity from third column
                        double unitPrice = Double.parseDouble(cartModel.getValueAt(i, 2).toString().replace("$", "").trim()); // Get price from second column, remove '$'

                        // Insert into OrderItems table
                        String insertItemQuery = "INSERT INTO OrderItems (order_id, item_id, quantity, price) VALUES (?, ?, ?, ?)";
                        PreparedStatement itemStatement = connection.prepareStatement(insertItemQuery);
                        itemStatement.setInt(1, orderId);
                        itemStatement.setInt(2, itemId);
                        itemStatement.setInt(3, quantity);
                        itemStatement.setDouble(4, unitPrice); // Store the unit price for the item

                        itemStatement.executeUpdate();
                    }

                    JOptionPane.showMessageDialog(frame, "Order placed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                    CustomerSignedIn customerSignedInPage = new CustomerSignedIn();
                    customerSignedInPage.setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to place order. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frame, "An error occurred while processing your order. Please try again.", "Database Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Invalid number format in cart items. Please check your inputs.", "Input Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
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
