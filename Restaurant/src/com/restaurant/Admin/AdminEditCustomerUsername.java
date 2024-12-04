package com.restaurant.Admin;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.restaurant.Database;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;

public class AdminEditCustomerUsername {

	public JFrame frame;
	private static JComboBox<String> customerCB;
	static DefaultComboBoxModel<String> customerCBModel = new DefaultComboBoxModel<String>();
	private JLabel newUserLBL;
	private JTextField newUser;
	private JLabel lblModifyCustomerUsername;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminEditCustomerUsername window = new AdminEditCustomerUsername();
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
	public AdminEditCustomerUsername() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame("Edit Username");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		lblModifyCustomerUsername = new JLabel("Modify Customer Username");
		lblModifyCustomerUsername.setHorizontalAlignment(SwingConstants.CENTER);
		lblModifyCustomerUsername.setFont(new Font("Lucida Grande", Font.BOLD, 17));
		lblModifyCustomerUsername.setBounds(75, 10, 310, 20);
		frame.getContentPane().add(lblModifyCustomerUsername);
		
		JButton modifyButton = new JButton("Modify Info");
		modifyButton.setBounds(125, 188, 210, 23);
		frame.getContentPane().add(modifyButton);
		
		modifyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editCustomerUsername();
			}
		});
		
		JLabel usernameLBL = new JLabel("Old Username");
		usernameLBL.setBounds(127, 59, 88, 14);
		frame.getContentPane().add(usernameLBL);
		
		newUserLBL = new JLabel("New Username");
		newUserLBL.setBounds(125, 119, 93, 14);
		frame.getContentPane().add(newUserLBL);
		
		customerCB = new JComboBox<String>();
		customerCB.setBounds(125, 85, 210, 22);
		frame.getContentPane().add(customerCB);
		
		newUser = new JTextField();
		newUser.setBounds(125, 145, 210, 20);
		frame.getContentPane().add(newUser);
		newUser.setColumns(10);
		
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
	
	public void editCustomerUsername() {
		try {
			Connection connection = Database.connection;
			String query = "UPDATE Customers SET customer_username = ? WHERE customer_username = ?";
			PreparedStatement stm = connection.prepareStatement(query);
			String oldUser = customerCB.getSelectedItem().toString();
			stm.setString(1, newUser.getText());
			stm.setString(2, oldUser);
			stm.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "Username Changed Successfully!", "", JOptionPane.DEFAULT_OPTION);
			populateComboBox();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
