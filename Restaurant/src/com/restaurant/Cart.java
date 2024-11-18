package com.restaurant;

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

public class Cart {

    private static Cart instance;

    private JFrame frame;
    private JTable table;
    private DefaultTableModel model;
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
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Cart window = new Cart();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        // Cart Table
        String[] columnNames = {"Name", "Price", "Quantity", "Total"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        JScrollPane CartScrollPane = new JScrollPane(table);
        CartScrollPane.setBounds(55, 63, 400, 400);
        frame.getContentPane().add(CartScrollPane);

        JLabel CartLabel = new JLabel("Cart");
        CartLabel.setBounds(250, 35, 34, 16);
        frame.getContentPane().add(CartLabel);

        // Remove From Cart Button
        RemoveFromCartButton = new JButton("Remove From Cart");
        RemoveFromCartButton.setBounds(556, 202, 153, 29);
        frame.getContentPane().add(RemoveFromCartButton);

        RemoveFromCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();

                if (selectedRow != -1) {
                    model.removeRow(selectedRow);
                    updateTotalCost();
                } else {
                    System.out.println("Please select an item to remove from the cart.");
                }
            }
        });

        // Quantity Spinner
        JLabel QuantityLabel = new JLabel("Modify Quantity:");
        QuantityLabel.setBounds(579, 264, 105, 16);
        frame.getContentPane().add(QuantityLabel);

        ModifyQuantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
        ModifyQuantitySpinner.setBounds(556, 292, 153, 29);
        frame.getContentPane().add(ModifyQuantitySpinner);

        // Update Quantity Button
        UpdateQuantityButton = new JButton("Update Quantity");
        UpdateQuantityButton.setBounds(556, 333, 153, 29);
        frame.getContentPane().add(UpdateQuantityButton);

        UpdateQuantityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int newQuantity = (int) ModifyQuantitySpinner.getValue();
                    double price = Double.parseDouble(model.getValueAt(selectedRow, 1).toString().replace("$", ""));
                    double newTotal = newQuantity * price;
                    model.setValueAt(newQuantity, selectedRow, 2);
                    model.setValueAt("$" + String.format("%.2f", newTotal), selectedRow, 3);
                    updateTotalCost();
                } else {
                    System.out.println("Please select an item to update the quantity.");
                }
            }
        });

        // Add More Items Button
        AddMoreItemsButton = new JButton("Add More Items");
        AddMoreItemsButton.setBounds(556, 161, 153, 29);
        frame.getContentPane().add(AddMoreItemsButton);

        AddMoreItemsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                OrderFood orderFoodPage = new OrderFood();
                orderFoodPage.main(null);
            }
        });

        // Go to Payment Button
        GoToPaymentButton = new JButton("Go to Payment");
        GoToPaymentButton.setBounds(503, 501, 117, 29);
        frame.getContentPane().add(GoToPaymentButton);

        GoToPaymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                MakePayment makePaymentPage = new MakePayment();
                makePaymentPage.setVisible(true);
            }
        });

        // Title
        ViewCartLabel = new JLabel("View Cart");
        ViewCartLabel.setFont(new Font("Lucida Grande", Font.BOLD, 17));
        ViewCartLabel.setBounds(373, 6, 88, 16);
        frame.getContentPane().add(ViewCartLabel);

        // Back Button
        BackButton = new JButton("Back");
        BackButton.setBounds(198, 501, 117, 29);
        frame.getContentPane().add(BackButton);

        BackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                OrderFood orderFoodPage = new OrderFood();
                orderFoodPage.main(null);
            }
        });

        // Total Cost Label and Field
        JLabel TotalLabel = new JLabel("Your Total is: ");
        TotalLabel.setBounds(503, 424, 88, 16);
        frame.getContentPane().add(TotalLabel);

        TotalCost = new JTextField();
        TotalCost.setBounds(600, 419, 130, 26);
        TotalCost.setEditable(false);
        frame.getContentPane().add(TotalCost);

        // Add listener for row selection
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        int quantity = (int) model.getValueAt(selectedRow, 2);
                        ModifyQuantitySpinner.setValue(quantity);  // Update spinner value
                    }
                }
            }
        });

        updateTotalCost();
    }

    // Method to add items to the cart
    public void addToCart(String name, double price, int quantity, double total) {
        // Iterate through the rows to check if the item already exists
        for (int i = 0; i < model.getRowCount(); i++) {
            String existingName = model.getValueAt(i, 0).toString();
            if (existingName.equals(name)) {
                // Item already exists, update the quantity and total
                int existingQuantity = (int) model.getValueAt(i, 2);
                int newQuantity = existingQuantity + quantity;
                double newTotal = newQuantity * price;

                model.setValueAt(newQuantity, i, 2);
                model.setValueAt("$" + String.format("%.2f", newTotal), i, 3);
                updateTotalCost();
                return;
            }
        }

        // If the item does not exist, add it as a new row
        model.addRow(new Object[]{name, "$" + String.format("%.2f", price), quantity, "$" + String.format("%.2f", total)});
        updateTotalCost();
    }


    // Method to update the total cost of items in the cart
    private void updateTotalCost() {
        double totalCost = 0.0;
        for (int i = 0; i < model.getRowCount(); i++) {
            String totalText = model.getValueAt(i, 3).toString().replace("$", "");
            totalCost += Double.parseDouble(totalText);
        }
        TotalCost.setText("$" + String.format("%.2f", totalCost));
    }

    // Method to set Cart frame visibility
    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
}
