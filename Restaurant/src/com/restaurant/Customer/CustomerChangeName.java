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

public class CustomerChangeName {

	public JFrame frame;
	private JTextField newNameTF;
	private JTextField usernameTF;

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
		frame = new JFrame("Change Password");
		frame.setBounds(100, 100, 500, 350);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		setLookAndFeel();
		confirmButton();
		
		newNameTF = new JTextField();
		newNameTF.setBounds(95, 167, 310, 29);
		frame.getContentPane().add(newNameTF);
		newNameTF.setColumns(10);
		
		
		JLabel newNameLBL = new JLabel("Enter New Name");
		newNameLBL.setBounds(95, 141, 310, 14);
		frame.getContentPane().add(newNameLBL);
		
		JLabel usernameLBL = new JLabel("Enter Username");
		usernameLBL.setBounds(95, 75, 310, 14);
		frame.getContentPane().add(usernameLBL);
		
		usernameTF = new JTextField();
		usernameTF.setBounds(95, 98, 310, 29);
		frame.getContentPane().add(usernameTF);
		usernameTF.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Change Name");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 17));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(95, 17, 310, 29);
		frame.getContentPane().add(lblNewLabel);
		
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
		confirmButton.setBounds(95, 209, 310, 29);
		frame.getContentPane().add(confirmButton);
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setNewName();
			}
		});
	}
	public void setNewName() {
		try {
			Connection connection = Database.connection;
			String query = "UPDATE Customers SET customer_name = ? WHERE customer_username = ?";
			PreparedStatement stm = connection.prepareStatement(query);
			
			stm.setString(1, newNameTF.getText());
			stm.setString(2,  usernameTF.getText());

			stm.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "Name Updated Successfully!", "", JOptionPane.DEFAULT_OPTION);
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
