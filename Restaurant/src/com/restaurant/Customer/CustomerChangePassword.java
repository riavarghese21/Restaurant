package com.restaurant.Customer;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.JFrame;
import javax.swing.UIManager;

import com.restaurant.Database;
import com.restaurant.Encryption;
import com.restaurant.SignInPage;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

public class CustomerChangePassword {

	public JFrame frame;
	private static JPasswordField newPasswordTF;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CustomerChangePassword window = new CustomerChangePassword();
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
	public CustomerChangePassword() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame("Change Password");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		confirmButton();
		
		newPasswordTF = new JPasswordField();
		newPasswordTF.setBounds(65, 110, 310, 29);
		frame.getContentPane().add(newPasswordTF);
		newPasswordTF.setColumns(10);
		
		
		JLabel newPasswordLBL = new JLabel("Enter New Password");
		newPasswordLBL.setBounds(65, 84, 310, 14);
		frame.getContentPane().add(newPasswordLBL);
				
		JLabel lblNewLabel = new JLabel("Change Password");
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
				setNewPassword();
			}
		});
	}
	public void setNewPassword() {
	    try {
	        // Get the entered password
	        String password = new String(newPasswordTF.getPassword());

	        // Check if the password field is empty
	        if (password.isEmpty()) {
	            JOptionPane.showMessageDialog(null, "Please enter a new password.", "Input Error", JOptionPane.WARNING_MESSAGE);
	            return;
	        }

	        // Proceed with updating the password if not empty
	        Connection connection = Database.connection;
	        String query = "UPDATE Customers SET customer_password = ? WHERE customer_id = ?";
	        String encryptedPswd = Encryption.encrypt(password);
	        PreparedStatement stm = connection.prepareStatement(query);
	        int customerId = getCurrentCustomerId();

	        stm.setString(1, encryptedPswd);
	        stm.setInt(2, customerId);

	        stm.executeUpdate();

	        JOptionPane.showMessageDialog(null, "Password Updated Successfully!", "", JOptionPane.DEFAULT_OPTION);
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
