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

public class AdminEditEmployeePassword {

	public JFrame frame;
	private static JComboBox<String> employeeCB;
	static DefaultComboBoxModel<String> employeeCBModel = new DefaultComboBoxModel<String>();
	private JLabel newPasswordLBL;
	private static JPasswordField newPassword;
	private JLabel lblEditPassword;

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
		
		lblEditPassword = new JLabel("Edit Employee Password");
		lblEditPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditPassword.setFont(new Font("Lucida Grande", Font.BOLD, 17));
		lblEditPassword.setBounds(75, 10, 310, 20);
		frame.getContentPane().add(lblEditPassword);
		
		JButton modifyButton = new JButton("Modify Info");
		modifyButton.setBounds(125, 190, 210, 23);
		frame.getContentPane().add(modifyButton);
		
		modifyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editEmployeePassword();
			}
		});
		
		JLabel usernameLBL = new JLabel("Username");
		usernameLBL.setBounds(125, 54, 210, 14);
		frame.getContentPane().add(usernameLBL);
		
		newPasswordLBL = new JLabel("New Password");
		newPasswordLBL.setBounds(125, 114, 210, 14);
		frame.getContentPane().add(newPasswordLBL);
		
		employeeCB = new JComboBox<String>();
		employeeCB.setBounds(125, 80, 210, 22);
		frame.getContentPane().add(employeeCB);
		
		newPassword = new JPasswordField();
		newPassword.setBounds(125, 140, 210, 20);
		frame.getContentPane().add(newPassword);
		newPassword.setColumns(10);
		
		populateComboBox();
        JButton backButton = new JButton("Back");
        backButton.setBounds(25, 230, 80, 25);
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
