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

public class AdminEditEmployeePassword {

	public JFrame frame;
	private static JComboBox<String> employeeCB;
	static DefaultComboBoxModel<String> employeeCBModel = new DefaultComboBoxModel<String>();
	private JLabel newPasswordLBL;
	private static JPasswordField newPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminEditEmployeePassword window = new AdminEditEmployeePassword();
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
	public AdminEditEmployeePassword() {
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
		
		JButton modifyButton = new JButton("Modify Info");
		modifyButton.setBounds(165, 204, 89, 23);
		frame.getContentPane().add(modifyButton);
		
		modifyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editEmployeePassword();
			}
		});
		
		JLabel usernameLBL = new JLabel("Username");
		usernameLBL.setBounds(165, 24, 88, 14);
		frame.getContentPane().add(usernameLBL);
		
		newPasswordLBL = new JLabel("New Password");
		newPasswordLBL.setBounds(165, 86, 88, 14);
		frame.getContentPane().add(newPasswordLBL);
		
		employeeCB = new JComboBox<String>();
		employeeCB.setBounds(165, 49, 88, 22);
		frame.getContentPane().add(employeeCB);
		
		newPassword = new JPasswordField();
		newPassword.setBounds(165, 111, 89, 20);
		frame.getContentPane().add(newPassword);
		newPassword.setColumns(10);
		
		populateComboBox();
        JButton backButton = new JButton("Back");
        backButton.setBounds(50, 220, 80, 25);
        frame.getContentPane().add(backButton);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	goToAdminManageEmployeesPage();
            }
        });
	}
    private void goToAdminManageEmployeesPage() {
        frame.dispose();
        AdminManageEmployees AdminManageEmployees = new AdminManageEmployees();
        AdminManageEmployees.setVisible(true);
    }
	public void populateComboBox() {
		try {
			Connection connection = Database.connection; // Connect to database
			Statement stm = connection.createStatement(); // Create statement
			String query = "SELECT * FROM Employees"; // Enter the query
			
			employeeCBModel = new DefaultComboBoxModel<String>();
			
			ResultSet result = stm.executeQuery(query); // Execute the query
			while (result.next()) {
				String employeeName = result.getString("employee_username");
				employeeCBModel.addElement(employeeName);
			}
			
			employeeCB.setModel(employeeCBModel);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	public void editEmployeePassword() {
		try {
			Connection connection = Database.connection;
			String query = "UPDATE Employees SET employee_password = ? WHERE employee_username = ?";
			String password = new String(newPassword.getPassword());
			String encryptedPswd = Encryption.encrypt(password);
			PreparedStatement stm = connection.prepareStatement(query);
			String user = employeeCB.getSelectedItem().toString();
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
