package com.restaurant.Customer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.restaurant.Database;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerHistory {

    private JFrame frame;
    private JTable reservationsTable;
    private JTable ordersTable;
    private DefaultTableModel reservationsModel;
    private DefaultTableModel ordersModel;

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

    public CustomerHistory() {
        initialize();
        loadReservations();
        loadOrders();
    }

    private void initialize() {
        frame = new JFrame("View History");
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(6, 63, 788, 443);
        frame.getContentPane().add(tabbedPane);

        // Reservations Tab
        JPanel reservationsPanel = new JPanel();

        reservationsModel = new DefaultTableModel(new String[]{"Date", "Time", "Party Size"}, 0);
        reservationsPanel.setLayout(null);
        reservationsTable = new JTable(reservationsModel);
        JScrollPane reservationsScrollPane = new JScrollPane(reservationsTable);
        reservationsScrollPane.setBounds(0, 47, 779, 420);
        reservationsPanel.add(reservationsScrollPane);

        tabbedPane.addTab("Reservations History", reservationsPanel);

        // Orders Tab
        JPanel ordersPanel = new JPanel();
        ordersPanel.setLayout(new BorderLayout());

        ordersModel = new DefaultTableModel(new String[]{"Order Date", "Total Amount", "Status"}, 0);
        ordersTable = new JTable(ordersModel);
        JScrollPane ordersScrollPane = new JScrollPane(ordersTable);
        ordersPanel.add(ordersScrollPane, BorderLayout.CENTER);

        tabbedPane.addTab("Orders History", ordersPanel);

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setBounds(30, 526, 80, 25);
        backButton.addActionListener(e -> {
            frame.dispose();
            CustomerSignedIn customerSignedInPage = new CustomerSignedIn();
            customerSignedInPage.setVisible(true);
        });
        frame.getContentPane().add(backButton);
        
        JLabel ViewHistoryLabel = new JLabel("View History");
        ViewHistoryLabel.setFont(new Font("Lucida Grande", Font.BOLD, 17));
        ViewHistoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
        ViewHistoryLabel.setBounds(245, 17, 310, 25);
        frame.getContentPane().add(ViewHistoryLabel);
    }

    private void loadReservations() {
        reservationsModel.setRowCount(0); 
        try {
            Connection connection = Database.getConnection();
            if (connection == null) {
                JOptionPane.showMessageDialog(frame, "Database connection failed.", "Database Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int customerId = getCurrentCustomerId();
            String query = "SELECT reservation_date, reservation_time, party_size FROM Reservations WHERE customer_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, customerId);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String date = rs.getString("reservation_date");
                String time = rs.getString("reservation_time");
                int partySize = rs.getInt("party_size");
                reservationsModel.addRow(new Object[]{date, time, partySize});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error loading reservation history.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadOrders() {
        ordersModel.setRowCount(0); 
        try {
            Connection connection = Database.getConnection();
            if (connection == null) {
                JOptionPane.showMessageDialog(frame, "Database connection failed.", "Database Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int customerId = getCurrentCustomerId();
            String query = "SELECT order_date, total_amount, order_status FROM Orders WHERE customer_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, customerId);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String date = rs.getString("order_date");
                double totalAmount = rs.getDouble("total_amount");
                String status = rs.getString("order_status");
                ordersModel.addRow(new Object[]{date, String.format("$%.2f", totalAmount), status});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error loading order history.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int getCurrentCustomerId() {
        return UserSession.getInstance().getCustomerId();
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
}