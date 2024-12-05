package com.restaurant.Customer;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import com.restaurant.Database;

public class CustomerChangeAddress {

    public JFrame frame;
    private JTextField newAddressTF;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    CustomerChangeAddress window = new CustomerChangeAddress();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public CustomerChangeAddress() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    public void initialize() {
        frame = new JFrame("Change Address");
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        confirmButton();

        newAddressTF = new JTextField();
        newAddressTF.setBounds(65, 101, 310, 29);
        frame.getContentPane().add(newAddressTF);
        newAddressTF.setColumns(10);

        JLabel newAddressLBL = new JLabel("Enter New Address");
        newAddressLBL.setBounds(65, 75, 310, 14);
        frame.getContentPane().add(newAddressLBL);

        JLabel lblNewLabel = new JLabel("Change Address");
        lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 17));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(75, 17, 310, 29);
        frame.getContentPane().add(lblNewLabel);

        JButton backButton = new JButton("Back");
        backButton.setBounds(25, 226, 80, 25);
        frame.getContentPane().add(backButton);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                goToCustomerAccountSettings();
            }
        });
    }

    private void goToCustomerAccountSettings() {
        frame.dispose();
        CustomerAccountSettings customerAccountSettingsPage = new CustomerAccountSettings();
        customerAccountSettingsPage.setVisible(true);
    }

    public void confirmButton() {
        JButton confirmButton = new JButton("Confirm");
        confirmButton.setBounds(65, 142, 310, 29);
        frame.getContentPane().add(confirmButton);
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setNewAddress();
            }
        });
    }

    public void setNewAddress() {
        try {
            // Get the entered address
            String address = newAddressTF.getText();

            // Check if the address field is empty
            if (address.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a new address.", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Proceed with updating the address if not empty
            Connection connection = Database.connection;
            String query = "UPDATE Customers SET customer_address = ? WHERE customer_id = ?";
            PreparedStatement stm = connection.prepareStatement(query);
            int customerId = getCurrentCustomerId();

            stm.setString(1, address);
            stm.setInt(2, customerId);

            stm.executeUpdate();

            JOptionPane.showMessageDialog(null, "Address Updated Successfully!", "", JOptionPane.DEFAULT_OPTION);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private int getCurrentCustomerId() {
        return UserSession.getInstance().getCustomerId();
    }


    public static void setupClosingDBConnection() {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                Database.closeConnection(); // Use the closeConnection() method to close the connection
            }
        }, "Shutdown-thread"));
    }
}
