package com.restaurant.Customer;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.JSpinner;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.Font;
import javax.swing.SwingConstants;

public class Cart {

    private static Cart instance;

    private JFrame frame;
    private JTable table;
    public DefaultTableModel model;
    private JButton RemoveFromCartButton;
    private JButton AddMoreItemsButton;
    private JButton GoToPaymentButton;
    private JLabel ViewCartLabel;
    private JButton BackButton;
    private JTextField TotalCost;
    private JSpinner ModifyQuantitySpinner;
    private JButton UpdateQuantityButton;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Cart window = new Cart();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static Cart getInstance() {
        if (instance == null) {
            instance = new Cart();
        }
        return instance;
    }

    /**
     * Create the application.
     */
    public Cart() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    public void initialize() {
        frame = new JFrame("View Cart");
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        // Cart Table
        String[] columnNames = {"Item ID", "Name", "Price", "Quantity", "Total"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        JScrollPane CartScrollPane = new JScrollPane(table);
        CartScrollPane.setBounds(55, 63, 400, 400);
        frame.getContentPane().add(CartScrollPane);

        JLabel CartLabel = new JLabel("Cart");
        CartLabel.setHorizontalAlignment(SwingConstants.CENTER);
        CartLabel.setBounds(247, 41, 34, 16);
        frame.getContentPane().add(CartLabel);

        // Remove From Cart Button
        RemoveFromCartButton = new JButton("Remove From Cart");
        RemoveFromCartButton.setBounds(556, 202, 153, 29);
        frame.getContentPane().add(RemoveFromCartButton);

        RemoveFromCartButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();

            if (selectedRow != -1) {
                model.removeRow(selectedRow);
                updateTotalCost();
            } else {
                System.out.println("Please select an item to remove from the cart.");
            }
        });

        // Quantity Spinner
        JLabel QuantityLabel = new JLabel("Modify Quantity:");
        QuantityLabel.setHorizontalAlignment(SwingConstants.CENTER);
        QuantityLabel.setBounds(579, 264, 105, 16);
        frame.getContentPane().add(QuantityLabel);

        ModifyQuantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
        ModifyQuantitySpinner.setBounds(556, 292, 153, 29);
        frame.getContentPane().add(ModifyQuantitySpinner);

        // Update Quantity Button
        UpdateQuantityButton = new JButton("Update Quantity");
        UpdateQuantityButton.setBounds(556, 333, 153, 29);
        frame.getContentPane().add(UpdateQuantityButton);

        UpdateQuantityButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int newQuantity = (int) ModifyQuantitySpinner.getValue();
                double price = Double.parseDouble(model.getValueAt(selectedRow, 2).toString().replace("$", ""));
                double newTotal = newQuantity * price;
                model.setValueAt(newQuantity, selectedRow, 3);
                model.setValueAt("$" + String.format("%.2f", newTotal), selectedRow, 4);
                updateTotalCost();
            } else {
                System.out.println("Please select an item to update the quantity.");
            }
        });

        // Add More Items Button
        AddMoreItemsButton = new JButton("Add More Items");
        AddMoreItemsButton.setBounds(556, 161, 153, 29);
        frame.getContentPane().add(AddMoreItemsButton);

        AddMoreItemsButton.addActionListener(e -> {
            frame.dispose();
            OrderFood orderFoodPage = new OrderFood();
            orderFoodPage.main(null);
        });

        // Go to Payment Button
        GoToPaymentButton = new JButton("Go to Payment");
        GoToPaymentButton.setBounds(367, 500, 117, 29);
        frame.getContentPane().add(GoToPaymentButton);

        GoToPaymentButton.addActionListener(e -> {
            double totalCost = calculateTotalFromCart();
            frame.dispose();
            MakePayment makePaymentPage = new MakePayment(totalCost);
            makePaymentPage.setVisible(true);
        });


        // Title
        ViewCartLabel = new JLabel("View Cart");
        ViewCartLabel.setFont(new Font("Lucida Grande", Font.BOLD, 17));
        ViewCartLabel.setBounds(367, 16, 88, 16);
        frame.getContentPane().add(ViewCartLabel);

        // Back Button
        BackButton = new JButton("Back");
        BackButton.setBounds(30, 523, 80, 25);
        frame.getContentPane().add(BackButton);

        BackButton.addActionListener(e -> {
            frame.dispose();
            OrderFood orderFoodPage = new OrderFood();
            orderFoodPage.main(null);
        });

        // Total Cost Label and Field
        JLabel TotalLabel = new JLabel("Your Total is: ");
        TotalLabel.setHorizontalAlignment(SwingConstants.CENTER);
        TotalLabel.setBounds(503, 424, 88, 16);
        frame.getContentPane().add(TotalLabel);

        TotalCost = new JTextField();
        TotalCost.setBounds(600, 419, 130, 26);
        TotalCost.setEditable(false);
        frame.getContentPane().add(TotalCost);

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        int quantity = (int) model.getValueAt(selectedRow, 3);
                        ModifyQuantitySpinner.setValue(quantity);  // Update spinner value
                    }
                }
            }
        });

        updateTotalCost();
    }

    private double calculateTotalFromCart() {
        double totalCost = 0.0;
        for (int i = 0; i < model.getRowCount(); i++) {
            String totalText = model.getValueAt(i, 4).toString().replace("$", "");
            totalCost += Double.parseDouble(totalText);
        }
        return totalCost;
    }

	public void addToCart(int itemId, String name, double price, int quantity, double total) {
        for (int i = 0; i < model.getRowCount(); i++) {
            int existingItemId = (int) model.getValueAt(i, 0);
            if (existingItemId == itemId) {
                int existingQuantity = (int) model.getValueAt(i, 3);
                int newQuantity = existingQuantity + quantity;
                double newTotal = newQuantity * price;

                model.setValueAt(newQuantity, i, 3);
                model.setValueAt("$" + String.format("%.2f", newTotal), i, 4);
                updateTotalCost();
                return;
            }
        }

        model.addRow(new Object[]{itemId, name, "$" + String.format("%.2f", price), quantity, "$" + String.format("%.2f", total)});
        updateTotalCost();
    }

    private void updateTotalCost() {
        double totalCost = 0.0;
        for (int i = 0; i < model.getRowCount(); i++) {
            String totalText = model.getValueAt(i, 4).toString().replace("$", "");
            totalCost += Double.parseDouble(totalText);
        }
        TotalCost.setText("$" + String.format("%.2f", totalCost));
    }
    
    public void clearCart() {
        model.setRowCount(0);  // Clear all rows in the cart
        updateTotalCost();  // Update the total cost after clearing
    }


    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
}
