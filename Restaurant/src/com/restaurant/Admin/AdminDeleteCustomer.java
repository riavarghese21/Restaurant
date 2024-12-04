package com.restaurant.Admin;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.DefaultComboBoxModel;
import com.restaurant.Database;
import com.restaurant.Customer.CustomerSignIn;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.SwingConstants;


public class AdminDeleteCustomer {

	public JFrame frame;
	private static JComboBox<String> customerCB;
	static DefaultComboBoxModel<String> customerCBModel = new DefaultComboBoxModel<String>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminDeleteCustomer window = new AdminDeleteCustomer();
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
	public AdminDeleteCustomer() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame("Delete Customer");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel selectCustomerLBL = new JLabel("Select Customer");
		selectCustomerLBL.setBounds(135, 94, 134, 14);
		frame.getContentPane().add(selectCustomerLBL);
		
		customerCB = new JComboBox<String>();
		customerCB.setBounds(135, 120, 192, 22);
		frame.getContentPane().add(customerCB);
		
		JLabel lblDeleteCustomer = new JLabel("Delete Customer");
		lblDeleteCustomer.setHorizontalAlignment(SwingConstants.CENTER);
		lblDeleteCustomer.setFont(new Font("Lucida Grande", Font.BOLD, 17));
		lblDeleteCustomer.setBounds(75, 10, 310, 16);
		frame.getContentPane().add(lblDeleteCustomer);
		
		JButton deleteButton = new JButton("Delete Account");
		deleteButton.setBounds(135, 186, 192, 23);
		frame.getContentPane().add(deleteButton);
		
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteCustomer();
			}
		});

		populateComboBox();
        JButton backButton = new JButton("Back");
        backButton.setBounds(25, 230, 80, 25);
        frame.getContentPane().add(backButton);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	goToAdminManageCustomersPage();
            }
        });
	}
    private void goToAdminManageCustomersPage() {
        frame.dispose();
        AdminManageCustomers AdminManageCustomers = new AdminManageCustomers();
        AdminManageCustomers.setVisible(true);
    }
	public void populateComboBox() {
		try {
			Connection connection = Database.connection; // Connect to database
			Statement stm = connection.createStatement(); // Create statement
			String query = "SELECT * FROM Customers"; // Enter the query
			
			customerCBModel = new DefaultComboBoxModel<String>();
			
			ResultSet result = stm.executeQuery(query); // Execute the query
			while (result.next()) {
				String customerName = result.getString("customer_username");
				customerCBModel.addElement(customerName);
			}
			
			customerCB.setModel(customerCBModel);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	public void deleteCustomer() {
		try {
			Connection connection = Database.connection;
			String query = "DELETE FROM Customers WHERE customer_username = ?";
			PreparedStatement stm = connection.prepareStatement(query);
			String value = customerCB.getSelectedItem().toString();
			stm.setString(1, value);
			stm.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "Account Deleted Successfully!", "", JOptionPane.DEFAULT_OPTION);
			populateComboBox();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
