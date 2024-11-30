package com.restaurant.Customer;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.JFrame;
import javax.swing.UIManager;

import com.restaurant.Database;

import javax.swing.JTextField;
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
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		setLookAndFeel();
		confirmButton();
		
		newAddressTF = new JTextField();
		newAddressTF.setBounds(67, 146, 310, 29);
		frame.getContentPane().add(newAddressTF);
		newAddressTF.setColumns(10);
		
		
		JLabel newAddressLBL = new JLabel("Enter New Address");
		newAddressLBL.setBounds(67, 121, 310, 14);
		frame.getContentPane().add(newAddressLBL);
		
		JLabel usernameLBL = new JLabel("Enter Username");
		usernameLBL.setBounds(67, 41, 310, 14);
		frame.getContentPane().add(usernameLBL);
		
		usernameTF = new JTextField();
		usernameTF.setBounds(67, 66, 310, 29);
		frame.getContentPane().add(usernameTF);
		usernameTF.setColumns(10);
		
	}
	public void confirmButton() {
		JButton confirmButton = new JButton("Confirm");
		confirmButton.setBounds(67, 196, 310, 29);
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
