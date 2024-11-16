package com.restaurant;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JSpinner;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class OrderFood {

    private JFrame frame;
    private JTable table;
    private JTextField ItemNameTextField;
    private JTextField PriceTextField;
    private JTextField TotalTextField;
    private DefaultTableModel model;


    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                OrderFood window = new OrderFood();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public OrderFood() {
        initialize();
        loadMenuItems();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        // Title
        JLabel OrderOnlineLabel = new JLabel("Order Online");
        OrderOnlineLabel.setFont(new Font("Lucida Grande", Font.BOLD, 15));
        OrderOnlineLabel.setBounds(356, 6, 107, 16);
        frame.getContentPane().add(OrderOnlineLabel);

        // Menu Table
        String[] columnNames = {"Name", "Price"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);

        JScrollPane Menu = new JScrollPane(table);
        Menu.setBounds(52, 88, 400, 400);
        frame.getContentPane().add(Menu);

        JLabel MenuLabel = new JLabel("Menu");
        MenuLabel.setBounds(235, 65, 34, 16);
        frame.getContentPane().add(MenuLabel);
       

        // Item Name Field
        JLabel NameLabel = new JLabel("Name");
        NameLabel.setBounds(586, 76, 61, 16);
        frame.getContentPane().add(NameLabel);

        ItemNameTextField = new JTextField();
        ItemNameTextField.setBounds(503, 104, 214, 26);
        frame.getContentPane().add(ItemNameTextField);
        ItemNameTextField.setColumns(10);

        // Price Field
        JLabel PriceLabel = new JLabel("Price");
        PriceLabel.setBounds(586, 161, 61, 16);
        frame.getContentPane().add(PriceLabel);

        PriceTextField = new JTextField();
        PriceTextField.setBounds(503, 189, 213, 26);
        frame.getContentPane().add(PriceTextField);
        PriceTextField.setColumns(10);

        // Quantity Spinner
        JLabel QuantityLabel = new JLabel("Quantity");
        QuantityLabel.setBounds(586, 254, 61, 16);
        frame.getContentPane().add(QuantityLabel);

        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1);
        JSpinner QuantitySpinner = new JSpinner(spinnerModel);
        QuantitySpinner.setBounds(503, 282, 213, 26);
        frame.getContentPane().add(QuantitySpinner);

        // Update Total When Quantity Changes
        QuantitySpinner.addChangeListener(e -> {
            try {
                int quantity = (int) QuantitySpinner.getValue();
                String priceText = PriceTextField.getText();

                if (!priceText.isEmpty()) {
                    double price = Double.parseDouble(priceText.replace("$", ""));
                    double total = quantity * price;
                    TotalTextField.setText("$" + String.format("%.2f", total));
                }
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
                TotalTextField.setText("Error");
            }
        });

        // Total Field
        JLabel TotalLabel = new JLabel("Total");
        TotalLabel.setBounds(586, 358, 61, 16);
        frame.getContentPane().add(TotalLabel);

        TotalTextField = new JTextField();
        TotalTextField.setBounds(504, 386, 213, 26);
        frame.getContentPane().add(TotalTextField);
        TotalTextField.setColumns(10);

        // Add to Cart Button
        JButton AddToCartButton = new JButton("Add to Cart");
        AddToCartButton.setBounds(559, 449, 117, 29);
        frame.getContentPane().add(AddToCartButton);
        
        AddToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = ItemNameTextField.getText();
                String priceText = PriceTextField.getText();
                int quantity = (int) QuantitySpinner.getValue();
                String totalText = TotalTextField.getText();

                if (name.isEmpty() || priceText.isEmpty() || totalText.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please select an item and enter the quantity.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    double price = Double.parseDouble(priceText.replace("$", ""));
                    double total = Double.parseDouble(totalText.replace("$", ""));
                    
                    Cart cartPage = Cart.getInstance(); // Get the shared instance of Cart
                    cartPage.addToCart(name, price, quantity, total); // Add item to the cart

                    JOptionPane.showMessageDialog(frame, "You items have been added to the cart!", "Success", JOptionPane.INFORMATION_MESSAGE);

                    // Clear fields after adding to cart
                    ItemNameTextField.setText("");
                    PriceTextField.setText("");
                    QuantitySpinner.setValue(1);
                    TotalTextField.setText("");

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid price or total.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        // Back Button
        JButton BackButton = new JButton("Back");
        BackButton.setBounds(173, 514, 117, 29);
        frame.getContentPane().add(BackButton);
        
       
        //"View Cart"
        JButton ViewCartButton = new JButton("View Cart");
        ViewCartButton.setBounds(447, 514, 117, 29);
        frame.getContentPane().add(ViewCartButton);

        // ActionListener for "View Cart"
        ViewCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cart cartPage = Cart.getInstance(); // Assuming Cart is implemented as a singleton
                cartPage.setVisible(true); // Show the Cart JFrame
                frame.setVisible(false);  // Hide the current JFrame (OrderFood)
            }
        });

       
        // Table Selection Listener
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        String name = model.getValueAt(selectedRow, 0).toString();
                        String price = model.getValueAt(selectedRow, 1).toString();

                        ItemNameTextField.setText(name);
                        PriceTextField.setText(price);
                        QuantitySpinner.setValue(0);
                        TotalTextField.setText("");
                    }
                }
            }
        });
    }
    
    
    private void loadMenuItems() {
        try {
        	Connection connection = Database.getConnection();
            String query = "SELECT item_name, item_price FROM Menu";
            PreparedStatement stm = connection.prepareStatement(query);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                String name = rs.getString("item_name");
                int price = rs.getInt("item_price");
                model.addRow(new Object[]{name, "$" + price});
            }
            System.out.println("Menu items loaded successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
