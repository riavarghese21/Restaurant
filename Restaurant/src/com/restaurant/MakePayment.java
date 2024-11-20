package com.restaurant;

import javax.swing.*;
import javax.swing.JSpinner.DefaultEditor;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.NumberFormat;

public class MakePayment {

    private JFrame frame;
    private JTextField CardNumber;
    private JTextField FirstName;
    private JTextField LastName;
    private JTextField CVV;
    private JTextField BillingAddress;
    private JTextField City;
    private JTextField ZipCode;
    private JComboBox<String> CardType;
    private JComboBox<String> StateComboBox;
    private JComboBox<String> ExpirationMonthComboBox;
    private JComboBox<Integer> ExpirationYearComboBox;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                MakePayment window = new MakePayment();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public MakePayment() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        // Make Payment Title
        JLabel MakePaymentLabel = new JLabel("Make Payment");
        MakePaymentLabel.setFont(new Font("Lucida Grande", Font.BOLD, 17));
        MakePaymentLabel.setBounds(368, 6, 130, 29);
        frame.getContentPane().add(MakePaymentLabel);
        
        // Back Button
        JButton BackButton = new JButton("Back");
        BackButton.setBounds(231, 507, 117, 29);
        frame.getContentPane().add(BackButton);

        BackButton.addActionListener(e -> {
            frame.dispose();
            Cart cartPage = new Cart();
            cartPage.main(null);
        });
        
        // Place Order Button
        JButton PlaceOrderButton = new JButton("Place Order");
        PlaceOrderButton.setBounds(495, 507, 117, 29);
        frame.getContentPane().add(PlaceOrderButton);
        
        // Card Type ComboBox
        String[] cardTypes = {"Card Type", "Discover", "MasterCard", "Visa"};
        CardType = new JComboBox<>(cardTypes);
        CardType.setBounds(186, 75, 475, 26);
        frame.getContentPane().add(CardType);

        // Card Number Field
        CardNumber = new JTextField("Card Number");
        CardNumber.setHorizontalAlignment(SwingConstants.LEFT);
        CardNumber.setBounds(186, 128, 475, 26);
        addPlaceholder(CardNumber, "Card Number");
        frame.getContentPane().add(CardNumber);
        CardNumber.setColumns(10);
        
        // Expiration Month ComboBox
        String[] months = {
                "Month", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"
        };
        ExpirationMonthComboBox = new JComboBox<>(months);
        ExpirationMonthComboBox.setBounds(231, 166, 130, 26);
        frame.getContentPane().add(ExpirationMonthComboBox);

        // Expiration Year ComboBox
        Integer[] years = new Integer[22];
        int startYear = 2024;
        for (int i = 0; i < years.length; i++) {
            years[i] = startYear + i;
        }
        ExpirationYearComboBox = new JComboBox<>(years);
        ExpirationYearComboBox.setBounds(495, 166, 130, 26);
        frame.getContentPane().add(ExpirationYearComboBox);

        // First Name Field
        FirstName = new JTextField("First Name");
        FirstName.setBounds(186, 210, 190, 26);
        addPlaceholder(FirstName, "First Name");
        frame.getContentPane().add(FirstName);
        FirstName.setColumns(10);

        // Last Name Field
        LastName = new JTextField("Last Name");
        LastName.setBounds(437, 210, 224, 26);
        addPlaceholder(LastName, "Last Name");
        frame.getContentPane().add(LastName);
        LastName.setColumns(10);

        // CVV Field
        CVV = new JTextField("CVV");
        CVV.setBounds(205, 265, 130, 26);
        addPlaceholder(CVV, "CVV");
        frame.getContentPane().add(CVV);
        CVV.setColumns(10);

        // Billing Address Field
        BillingAddress = new JTextField("Billing Address");
        BillingAddress.setBounds(205, 326, 190, 26);
        addPlaceholder(BillingAddress, "Billing Address");
        frame.getContentPane().add(BillingAddress);
        BillingAddress.setColumns(10);

        // City Field
        City = new JTextField("City");
        City.setBounds(481, 326, 199, 26);
        addPlaceholder(City, "City");
        frame.getContentPane().add(City);
        City.setColumns(10);

        // ZipCode Field
        ZipCode = new JTextField("Zip Code");
        ZipCode.setBounds(454, 385, 171, 26);
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
        StateComboBox.setBounds(205, 385, 164, 26);
        frame.getContentPane().add(StateComboBox);

        // Place Order Button
        PlaceOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCardType = (String) CardType.getSelectedItem();
                String selectedState = (String) StateComboBox.getSelectedItem();
                String selectedYear = ExpirationYearComboBox.getSelectedItem().toString();
                String selectedMonth = (String) ExpirationMonthComboBox.getSelectedItem();

                if ("Card Type".equals(selectedCardType)) {
                    JOptionPane.showMessageDialog(frame, "Please select a valid card type.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if ("State".equals(selectedState)) {
                    JOptionPane.showMessageDialog(frame, "Please select a valid state.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if ("Month".equals(selectedMonth)) {
                    JOptionPane.showMessageDialog(frame, "Please select a valid expiration month.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    String cardNumber = CardNumber.getText();
                    String firstName = FirstName.getText();
                    String lastName = LastName.getText();
                    String cvv = CVV.getText();
                    String billingAddress = BillingAddress.getText();
                    String city = City.getText();
                    String zipCode = ZipCode.getText();

                    Connection connection = Database.getConnection();
                    if (connection == null) {
                        JOptionPane.showMessageDialog(frame, "Database connection failed.", "Database Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    String insertQuery = "INSERT INTO PaymentInfo (card_type, card_number, expiration_month, expiration_year, first_name, last_name, cvv, billing_address, city, state, zip_code) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                    preparedStatement.setString(1, selectedCardType);
                    preparedStatement.setString(2, cardNumber);
                    preparedStatement.setInt(3, Integer.parseInt(selectedMonth));
                    preparedStatement.setInt(4, Integer.parseInt(selectedYear));
                    preparedStatement.setString(5, firstName);
                    preparedStatement.setString(6, lastName);
                    preparedStatement.setString(7, cvv);
                    preparedStatement.setString(8, billingAddress);
                    preparedStatement.setString(9, city);
                    preparedStatement.setString(10, selectedState);
                    preparedStatement.setString(11, zipCode);

                    int rowsInserted = preparedStatement.executeUpdate();
                    if (rowsInserted > 0) {
                        JOptionPane.showMessageDialog(frame, "Order placed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Failed to place order. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "An error occurred while processing your payment. Please try again.", "Database Error", JOptionPane.ERROR_MESSAGE);
                }
                
//                try {
//                    Connection connection = Database.getConnection();
//                    connection.setAutoCommit(false); 
//
//                    String insertOrder = "INSERT INTO Orders (customer_id, date_ordered, time_ready, total_amount) VALUES (?, ?, ?, ?)";
//                    PreparedStatement orderStmt = connection.prepareStatement(insertOrder, Statement.RETURN_GENERATED_KEYS);
//                    orderStmt.setInt(1, customerId); 
//                    orderStmt.setDate(2, new java.sql.Date(System.currentTimeMillis()));
//                    orderStmt.setTime(3, new java.sql.Time(System.currentTimeMillis() + estimatedTimeOffset));
//                    orderStmt.setBigDecimal(4, totalAmount);
//                    orderStmt.executeUpdate();
//
//                    ResultSet generatedKeys = orderStmt.getGeneratedKeys();
//                    if (generatedKeys.next()) {
//                        int orderId = generatedKeys.getInt(1);
//                        
//                        
//                        String insertOrderItem = "INSERT INTO OrderItems (order_id, item_id, quantity, price) VALUES (?, ?, ?, ?)";
//                        PreparedStatement itemStmt = connection.prepareStatement(insertOrderItem);
//                        
//                        for (CartItem item : cartItems) {
//                            itemStmt.setInt(1, orderId);
//                            itemStmt.setInt(2, item.getItemId());
//                            itemStmt.setInt(3, item.getQuantity());
//                            itemStmt.setBigDecimal(4, item.getPrice());
//                            itemStmt.addBatch();
//                        }
//                        
//                        itemStmt.executeBatch();
//                    }
//
//                    connection.commit();
//                    JOptionPane.showMessageDialog(frame, "Order placed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
//                    frame.dispose();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                    try {
//                        connection.rollback(); 
//                    } catch (SQLException rollbackEx) {
//                        rollbackEx.printStackTrace();
//                    }
//                    JOptionPane.showMessageDialog(frame, "An error occurred while processing your order. Please try again.", "Database Error", JOptionPane.ERROR_MESSAGE);
//                } finally {
//                    try {
//                        if (connection != null) connection.setAutoCommit(true); 
//                    } catch (SQLException ex) {
//                        ex.printStackTrace();
//                    }
//                }
            }
        });
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
