package com.restaurant.Employee;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.restaurant.Database;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewOrders {

    public JFrame frame;
    private JTable ordersTable;
    private DefaultTableModel tableModel;
    private JCheckBox showAllCheckBox;
    private JButton readyForPickupButton, pickedUpButton, cancelOrderButton, backButton;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ViewOrders window = new ViewOrders();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public ViewOrders() {
        initialize();
        loadOrders(false);
    }

    public void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblTitle = new JLabel("Manage Orders");
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 17));
        lblTitle.setBounds(370, 20, 200, 30);
        frame.getContentPane().add(lblTitle);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(50, 70, 800, 350);
        frame.getContentPane().add(scrollPane);

        ordersTable = new JTable();
        tableModel = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Order ID", "Customer ID", "Order Date", "Total Amount", "Status"}
        );
        ordersTable.setModel(tableModel);
        scrollPane.setViewportView(ordersTable);

        showAllCheckBox = new JCheckBox("Show All Orders");
        showAllCheckBox.setBounds(50, 430, 150, 30);
        frame.getContentPane().add(showAllCheckBox);
        showAllCheckBox.addActionListener(e -> loadOrders(showAllCheckBox.isSelected()));

        readyForPickupButton = new JButton("Ready for Pickup");
        readyForPickupButton.setBounds(220, 470, 150, 30);
        frame.getContentPane().add(readyForPickupButton);
        readyForPickupButton.addActionListener(e -> updateOrderStatus("Ready for Pickup"));

        pickedUpButton = new JButton("Picked Up");
        pickedUpButton.setBounds(380, 470, 150, 30);
        frame.getContentPane().add(pickedUpButton);
        pickedUpButton.addActionListener(e -> updateOrderStatus("Picked Up"));

        cancelOrderButton = new JButton("Cancel Order");
        cancelOrderButton.setBounds(540, 470, 150, 30);
        frame.getContentPane().add(cancelOrderButton);
        cancelOrderButton.addActionListener(e -> cancelOrder());

        // Back Button
        backButton = new JButton("Back");
        backButton.setBounds(52, 515, 100, 30);
        frame.getContentPane().add(backButton);
        backButton.addActionListener(e -> {
            frame.dispose();
            EmployeeMenu employeeMenu = new EmployeeMenu();
            employeeMenu.setVisible(true);
        });
    }

    private void loadOrders(boolean showAll) {
        try {
            Connection connection = Database.getConnection();
            if (connection == null) {
                JOptionPane.showMessageDialog(frame, "Database connection failed.", "Database Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String query = "SELECT * FROM Orders";
            if (!showAll) {
                query += " WHERE order_status != 'Picked Up'";
            }
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            tableModel.setRowCount(0); // Clear existing rows
            while (resultSet.next()) {
                int orderId = resultSet.getInt("order_id");
                int customerId = resultSet.getInt("customer_id");
                String orderDate = resultSet.getString("order_date");
                double totalAmount = resultSet.getDouble("total_amount");
                String status = resultSet.getString("order_status");
                tableModel.addRow(new Object[]{orderId, customerId, orderDate, totalAmount, status});
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "An error occurred while loading orders.", "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void updateOrderStatus(String newStatus) {
        int selectedRow = ordersTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select an order to update.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int orderId = (int) tableModel.getValueAt(selectedRow, 0);

        try {
            Connection connection = Database.getConnection();
            if (connection == null) {
                JOptionPane.showMessageDialog(frame, "Database connection failed.", "Database Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String updateQuery = "UPDATE Orders SET order_status = ? WHERE order_id = ?";
            PreparedStatement statement = connection.prepareStatement(updateQuery);
            statement.setString(1, newStatus);
            statement.setInt(2, orderId);
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(frame, "Order status updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadOrders(showAllCheckBox.isSelected());
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "An error occurred while updating the order status.", "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void cancelOrder() {
        int selectedRow = ordersTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select an order to cancel.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int orderId = (int) tableModel.getValueAt(selectedRow, 0);

        try {
            Connection connection = Database.getConnection();
            if (connection == null) {
                JOptionPane.showMessageDialog(frame, "Database connection failed.", "Database Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String deleteQuery = "DELETE FROM Orders WHERE order_id = ?";
            PreparedStatement statement = connection.prepareStatement(deleteQuery);
            statement.setInt(1, orderId);
            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(frame, "Order canceled.", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadOrders(showAllCheckBox.isSelected());
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "An error occurred while canceling the order.", "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public JFrame getFrame() {
        return frame;
    }


}
