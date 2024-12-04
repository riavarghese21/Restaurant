package com.restaurant.Admin;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.restaurant.Database;
import java.awt.Font;
import javax.swing.SwingConstants;

public class AdminEditCustomerFullName {

	public JFrame frame;
	private static JComboBox<String> customerCB;
	static DefaultComboBoxModel<String> customerCBModel = new DefaultComboBoxModel<String>();
	private JLabel newNameLBL;
	private JTextField newName;
	private JLabel lblModifyCustomerName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminEditCustomerFullName window = new AdminEditCustomerFullName();
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
	public AdminEditCustomerFullName() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame("Edit Name");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		lblModifyCustomerName = new JLabel("Modify Customer Name");
		lblModifyCustomerName.setHorizontalAlignment(SwingConstants.CENTER);
		lblModifyCustomerName.setFont(new Font("Lucida Grande", Font.BOLD, 17));
		lblModifyCustomerName.setBounds(75, 10, 310, 20);
		frame.getContentPane().add(lblModifyCustomerName);
		
		JButton modifyButton = new JButton("Modify Info");
		modifyButton.setBounds(125, 204, 210, 23);
		frame.getContentPane().add(modifyButton);
		
		modifyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editCustomerName();
			}
		});
		
		JLabel usernameLBL = new JLabel("Username");
		usernameLBL.setBounds(125, 58, 88, 14);
		frame.getContentPane().add(usernameLBL);
		
		newNameLBL = new JLabel("New Name");
		newNameLBL.setBounds(125, 128, 88, 14);
		frame.getContentPane().add(newNameLBL);
		
		customerCB = new JComboBox<String>();
		customerCB.setBounds(125, 84, 210, 22);
		frame.getContentPane().add(customerCB);
		
		newName = new JTextField();
		newName.setBounds(125, 154, 210, 20);
		frame.getContentPane().add(newName);
		newName.setColumns(10);
		
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
	public void editCustomerName() {
		try {
			Connection connection = Database.connection;
			String query = "UPDATE Customers SET customer_name = ? WHERE customer_username = ?";
			PreparedStatement stm = connection.prepareStatement(query);
			String user = customerCB.getSelectedItem().toString();
			stm.setString(1, newName.getText());
			stm.setString(2, user);
			stm.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "Name Changed Successfully!", "", JOptionPane.DEFAULT_OPTION);
			populateComboBox();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
