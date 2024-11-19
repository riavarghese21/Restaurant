package com.restaurant;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CustomerHistory {

    private JFrame frame;
    private JTable reservationsTable;
    private JTable ordersTable;
    private DefaultTableModel reservationsModel;
    private DefaultTableModel ordersModel;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                CustomerHistory window = new CustomerHistory();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the application.
     */
    public CustomerHistory() {
        initialize();
        loadReservations();
        loadOrders();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblReservations = new JLabel("Reservation History");
        lblReservations.setBounds(50, 67, 200, 30);
        frame.getContentPane().add(lblReservations);

        reservationsModel = new DefaultTableModel(new String[]{"Reservation ID", "Date", "Time", "Party Size"}, 0);
        reservationsTable = new JTable(reservationsModel);
        JScrollPane reservationsScrollPane = new JScrollPane(reservationsTable);
        reservationsScrollPane.setBounds(50, 98, 600, 150);
        frame.getContentPane().add(reservationsScrollPane);

        JLabel lblOrders = new JLabel("Order History");
        lblOrders.setBounds(50, 282, 200, 30);
        frame.getContentPane().add(lblOrders);

        ordersModel = new DefaultTableModel(new String[]{"Order ID", "Order Date", "Item Name", "Quantity", "Total"}, 0);
        ordersTable = new JTable(ordersModel);
        JScrollPane ordersScrollPane = new JScrollPane(ordersTable);
        ordersScrollPane.setBounds(50, 312, 600, 150);
        frame.getContentPane().add(ordersScrollPane);

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setBounds(345, 525, 100, 30);
        frame.getContentPane().add(backButton);
        
        JLabel ViewHistoryLabel = new JLabel("View History");
        ViewHistoryLabel.setBounds(345, 6, 122, 30);
        frame.getContentPane().add(ViewHistoryLabel);
        backButton.addActionListener(e -> {
            frame.dispose();
        });
    }

   
    private void loadReservations() {
        try {
            Connection connection = Database.getConnection();
            String query = "SELECT reservation_id, date, time, party_size FROM Reservations WHERE customer_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, getCurrentCustomerId()); 

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int reservationId = rs.getInt("reservation_id");
                String date = rs.getString("date");
                String time = rs.getString("time");
                int partySize = rs.getInt("party_size");
                reservationsModel.addRow(new Object[]{reservationId, date, time, partySize});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void loadOrders() {
        try {
            Connection connection = Database.getConnection(); 
            String query = "SELECT order_id, order_date, item_name, quantity, total FROM Orders WHERE customer_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, getCurrentCustomerId()); 

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                String orderDate = rs.getString("order_date");
                String itemName = rs.getString("item_name");
                int quantity = rs.getInt("quantity");
                double total = rs.getDouble("total");
                ordersModel.addRow(new Object[]{orderId, orderDate, itemName, quantity, "$" + String.format("%.2f", total)});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    private int getCurrentCustomerId() {
        return 1; 
    }

  
    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
}
