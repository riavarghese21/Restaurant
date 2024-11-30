package com.restaurant.Admin;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.DefaultComboBoxModel;
import com.restaurant.Database;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.*;
import java.awt.event.ActionEvent;


public class AdminEditEmployee {

	public JFrame frame;
	private static JComboBox<String> employeeCB;
	static DefaultComboBoxModel<String> employeeCBModel = new DefaultComboBoxModel<String>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminEditEmployee window = new AdminEditEmployee();
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
	public AdminEditEmployee() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame("Edit Employee");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel selectEmployeeLBL = new JLabel("Select Employee");
		selectEmployeeLBL.setBounds(82, 44, 134, 14);
		frame.getContentPane().add(selectEmployeeLBL);
		
		employeeCB = new JComboBox<String>();
		employeeCB.setBounds(82, 83, 192, 22);
		frame.getContentPane().add(employeeCB);
		
		JButton deleteButton = new JButton("Delete Account");
		deleteButton.setBounds(10, 203, 180, 23);
		frame.getContentPane().add(deleteButton);
		
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteEmployee();
			}
		});
		populateComboBox();
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
	public void deleteEmployee() {
		try {
			Connection connection = Database.connection;
			String query = "DELETE FROM Employees WHERE employee_username = ?";
			PreparedStatement stm = connection.prepareStatement(query);
			String value = employeeCB.getSelectedItem().toString();
			stm.setString(1, value); // doctor_id (only question mark)
			stm.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "Account Deleted Successfully!", "", JOptionPane.DEFAULT_OPTION);
			populateComboBox();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
