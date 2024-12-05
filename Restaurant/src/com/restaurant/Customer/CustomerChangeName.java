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

import com.restaurant.Database;

public class CustomerChangeName {

    public JFrame frame;
    private static JTextField newNameTF;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    CustomerChangeName window = new CustomerChangeName();
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
    public CustomerChangeName() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    public void initialize() {
        frame = new JFrame("Change Name");
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        confirmButton();

        newNameTF = new JTextField();
        newNameTF.setBounds(65, 120, 310, 29);
        frame.getContentPane().add(newNameTF);
        newNameTF.setColumns(10);

        JLabel newNameLBL = new JLabel("Enter New Name");
        newNameLBL.setBounds(65, 94, 310, 14);
        frame.getContentPane().add(newNameLBL);

        JLabel lblNewLabel = new JLabel("Change Name");
        lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 17));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(75, 10, 310, 29);
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
        confirmButton.setBounds(65, 161, 310, 29);
        frame.getContentPane().add(confirmButton);
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setNewName();
            }
        });
    }

    public void setNewName() {
        try {
            // Get the entered name
            String name = newNameTF.getText();

            // Check if the name field is empty
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a new name.", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Proceed with updating the name if not empty
            Connection connection = Database.connection;
            String query = "UPDATE Customers SET customer_name = ? WHERE customer_id = ?";
            PreparedStatement stm = connection.prepareStatement(query);
            int customerId = getCurrentCustomerId();

            stm.setString(1, name);
            stm.setInt(2, customerId);

            stm.executeUpdate();

            JOptionPane.showMessageDialog(null, "Name Updated Successfully!", "", JOptionPane.DEFAULT_OPTION);
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
