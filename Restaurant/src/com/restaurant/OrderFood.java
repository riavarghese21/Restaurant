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
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.SwingConstants;

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
        OrderOnlineLabel.setHorizontalAlignment(SwingConstants.CENTER);
        OrderOnlineLabel.setFont(new Font("Lucida Grande", Font.BOLD, 17));
        OrderOnlineLabel.setBounds(350, 22, 124, 16);
        frame.getContentPane().add(OrderOnlineLabel);

        // Menu Table
        String[] columnNames = {"Name", "Price"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);

        JScrollPane Menu = new JScrollPane(table);
        Menu.setBounds(52, 94, 400, 383);
        frame.getContentPane().add(Menu);

        JLabel MenuLabel = new JLabel("Menu");
        MenuLabel.setHorizontalAlignment(SwingConstants.CENTER);
        MenuLabel.setBounds(239, 66, 34, 16);
        frame.getContentPane().add(MenuLabel);
       

        // Item Name Field
        JLabel NameLabel = new JLabel("Name");
        NameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        NameLabel.setBounds(586, 123, 48, 16);
        frame.getContentPane().add(NameLabel);

        ItemNameTextField = new JTextField();
        ItemNameTextField.setBounds(503, 151, 214, 26);
        frame.getContentPane().add(ItemNameTextField);
        ItemNameTextField.setColumns(10);

        // Price Field
        JLabel PriceLabel = new JLabel("Price");
        PriceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        PriceLabel.setBounds(586, 192, 48, 16);
        frame.getContentPane().add(PriceLabel);

        PriceTextField = new JTextField();
        PriceTextField.setBounds(504, 213, 213, 26);
        frame.getContentPane().add(PriceTextField);
        PriceTextField.setColumns(10);

        // Quantity
        JLabel QuantityLabel = new JLabel("Quantity");
        QuantityLabel.setHorizontalAlignment(SwingConstants.CENTER);
        QuantityLabel.setBounds(586, 258, 61, 16);
        frame.getContentPane().add(QuantityLabel);

        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1);
        JSpinner QuantitySpinner = new JSpinner(spinnerModel);
        QuantitySpinner.setBounds(504, 286, 213, 26);
        frame.getContentPane().add(QuantitySpinner);

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

        // Total
        JLabel TotalLabel = new JLabel("Total");
        TotalLabel.setHorizontalAlignment(SwingConstants.CENTER);
        TotalLabel.setBounds(586, 324, 48, 16);
        frame.getContentPane().add(TotalLabel);

        TotalTextField = new JTextField();
        TotalTextField.setBounds(504, 352, 213, 26);
        frame.getContentPane().add(TotalTextField);
        TotalTextField.setColumns(10);

        // Add to Cart
        JButton AddToCartButton = new JButton("Add to Cart");
        AddToCartButton.setBounds(559, 416, 117, 29);
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
        BackButton.setBounds(265, 515, 117, 29);
        frame.getContentPane().add(BackButton);
        
       
        //View Cart
        JButton ViewCartButton = new JButton("View Cart");
        ViewCartButton.setBounds(517, 515, 117, 29);
        frame.getContentPane().add(ViewCartButton);

        
        ViewCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cart cartPage = Cart.getInstance(); 
                cartPage.setVisible(true); 
                frame.setVisible(false);  
            }
        });

       
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
            if (connection == null) {
                System.out.println("Failed to connect to the database.");
                return;
            }
            String query = "SELECT item_name, item_price FROM Menu";
            PreparedStatement stm = connection.prepareStatement(query);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                String name = rs.getString("item_name");
                int price = rs.getInt("item_price");
                model.addRow(new Object[]{name, "$" + price});
            }
            System.out.println("Menu items loaded successfully.");
        } catch (SQLException e) {
            System.out.println("Error while loading menu items: " + e.getMessage());
        }
    }
}
