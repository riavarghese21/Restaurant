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
import com.restaurant.Encryption;

import javax.swing.JPasswordField;
import java.awt.Font;
import javax.swing.SwingConstants;

public class AdminEditCustomerPassword {

	public JFrame frame;
	private static JComboBox<String> customerCB;
	static DefaultComboBoxModel<String> customerCBModel = new DefaultComboBoxModel<String>();
	private JLabel newPasswordLBL;
	private static JPasswordField newPassword;
	private JLabel lblModifyCustomerPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminEditCustomerPassword window = new AdminEditCustomerPassword();
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
	public AdminEditCustomerPassword() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame("Edit Password");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		lblModifyCustomerPassword = new JLabel("Modify Customer Password");
		lblModifyCustomerPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblModifyCustomerPassword.setFont(new Font("Lucida Grande", Font.BOLD, 17));
		lblModifyCustomerPassword.setBounds(75, 10, 310, 20);
		frame.getContentPane().add(lblModifyCustomerPassword);
		
		JButton modifyButton = new JButton("Modify Info");
		modifyButton.setBounds(125, 191, 210, 23);
		frame.getContentPane().add(modifyButton);
		
		modifyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editCustomerPassword();
			}
		});
		
		JLabel usernameLBL = new JLabel("Username");
		usernameLBL.setBounds(125, 65, 88, 14);
		frame.getContentPane().add(usernameLBL);
		
		newPasswordLBL = new JLabel("New Password");
		newPasswordLBL.setBounds(125, 128, 101, 14);
		frame.getContentPane().add(newPasswordLBL);
		
		customerCB = new JComboBox<String>();
		customerCB.setBounds(125, 94, 210, 22);
		frame.getContentPane().add(customerCB);
		
		newPassword = new JPasswordField();
		newPassword.setBounds(125, 153, 210, 20);
		frame.getContentPane().add(newPassword);
		newPassword.setColumns(10);
		
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
	public void editCustomerPassword() {
		try {
			Connection connection = Database.connection;
			String query = "UPDATE Customers SET customer_password = ? WHERE customer_username = ?";
			String password = new String(newPassword.getPassword());
			String encryptedPswd = Encryption.encrypt(password);
			PreparedStatement stm = connection.prepareStatement(query);
			String user = customerCB.getSelectedItem().toString();
			stm.setString(1, encryptedPswd);
			stm.setString(2, user);
			stm.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "Password Updated Successfully!", "", JOptionPane.DEFAULT_OPTION);
			populateComboBox();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
