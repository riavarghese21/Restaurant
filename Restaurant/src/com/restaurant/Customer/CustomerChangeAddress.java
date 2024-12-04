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

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class CustomerChangeAddress {

	public JFrame frame;
	private JTextField newAddressTF;
	private JTextField usernameTF;

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
		frame.setBounds(100, 100, 500, 350);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		setLookAndFeel();
		confirmButton();
		
		newAddressTF = new JTextField();
		newAddressTF.setBounds(95, 178, 310, 29);
		frame.getContentPane().add(newAddressTF);
		newAddressTF.setColumns(10);
		
		
		JLabel newAddressLBL = new JLabel("Enter New Address");
		newAddressLBL.setBounds(95, 152, 310, 14);
		frame.getContentPane().add(newAddressLBL);
		
		JLabel usernameLBL = new JLabel("Enter Username");
		usernameLBL.setBounds(95, 72, 310, 14);
		frame.getContentPane().add(usernameLBL);
		
		JLabel lblNewLabel = new JLabel("Change Address");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 17));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(95, 17, 310, 29);
		frame.getContentPane().add(lblNewLabel);

		
		usernameTF = new JTextField();
		usernameTF.setBounds(95, 98, 310, 29);
		frame.getContentPane().add(usernameTF);
		usernameTF.setColumns(10);
		
		JButton backButton = new JButton("Back");
        backButton.setBounds(25, 278, 80, 25);
        frame.getContentPane().add(backButton);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                goToCustomerSignIn();
            }
        });
		
	}
	
	private void goToCustomerSignIn() {
        frame.dispose();
        CustomerSignedIn customerSignedInPage = new CustomerSignedIn();
        customerSignedInPage.setVisible(true);
    }
        
        
	public void confirmButton() {
		JButton confirmButton = new JButton("Confirm");
		confirmButton.setBounds(95, 219, 310, 29);
		frame.getContentPane().add(confirmButton);
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setNewAddress();
			}
		});
	}
	public void setNewAddress() {
		try {
			Connection connection = Database.connection;
			String query = "UPDATE Customers SET customer_address = ? WHERE customer_username = ?";
			PreparedStatement stm = connection.prepareStatement(query);
			
			stm.setString(1, newAddressTF.getText());
			stm.setString(2,  usernameTF.getText());

			stm.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "Address Updated Successfully!", "", JOptionPane.DEFAULT_OPTION);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	public void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) { }
	}
	public static void setupClosingDBConnection() {
	    Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
	        public void run() {
	            Database.closeConnection(); // Use the closeConnection() method to close the connection
	        }
	    }, "Shutdown-thread"));
	}
        }