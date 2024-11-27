package com.restaurant;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.sql.*;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class ButtonLabelTextFieldDemo {

	public JFrame frame;
	private JTextField customerIDTF;
	private JTextField customerNameTF;
	private JTextField dateOfBirthTF;

	public ButtonLabelTextFieldDemo() { initialize(); }

	public void initialize() {
		frame = new JFrame();
		frame.setTitle("Customer Adder");
		frame.setBounds(100, 100, 267, 333);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		createAddCustomerButton(); // Creates the Add Patient button
		createCustomerIDLBL(); // Creates the Patient ID Label
		createCustomerIDTF(); // Creates the Patient ID Text Field
		createCustomerNameLBL(); // Creates the Patient Name Label
		createCustomerNameTF(); // Creates the Patient Name Text Field
		createDateOfBirthLBL(); // Creates the Date of Birth Label
		createDateOfBirthTF(); // Creates the Date of Birth Text Field
	}
	
	// Creates the Add Patient button
	public void createAddCustomerButton() {
		JButton addCustomerButton = new JButton("Add Customer");
		addCustomerButton.setBounds(83, 238, 117, 29);
		frame.getContentPane().add(addCustomerButton);
		addCustomerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Put code here that you want to run when this button is clicked
				addCustomCustomer(); // Adds the new customer to the database when the button is clicked
			}
		});
	}
	
	// Creates Customer ID label
	public void createCustomerIDLBL() {
		JLabel CustomerIDLBL = new JLabel("CustomerID");
		CustomerIDLBL.setBounds(108, 36, 61, 16);
		frame.getContentPane().add(CustomerIDLBL);
	}
	
	// Creates Customer ID text field
	public void createCustomerIDTF() {
		customerIDTF = new JTextField();
		customerIDTF.setBounds(53, 58, 174, 38);
		frame.getContentPane().add(customerIDTF);
		customerIDTF.setColumns(10);
	}
	
	// Creates Customer Name label
	public void createCustomerNameLBL() {
		JLabel customerNameLBL = new JLabel("Customer Name");
		customerNameLBL.setBounds(95, 98, 92, 16);
		frame.getContentPane().add(customerNameLBL);
	}
	
	// Creates Customer Name text field
	public void createCustomerNameTF() {
		customerNameTF = new JTextField();
		customerNameTF.setColumns(10);
		customerNameTF.setBounds(54, 116, 174, 38);
		frame.getContentPane().add(customerNameTF);
	}
	
	// Creates Date of Birth label
	public void createDateOfBirthLBL() {
		JLabel dateOfBirthLBL = new JLabel("Date of Birth");
		dateOfBirthLBL.setBounds(95, 166, 92, 16);
		frame.getContentPane().add(dateOfBirthLBL);
	}
	
	// Creates Date of Birth text field
	public void createDateOfBirthTF() {
		dateOfBirthTF = new JTextField();
		dateOfBirthTF.setColumns(10);
		dateOfBirthTF.setBounds(54, 179, 174, 38);
		frame.getContentPane().add(dateOfBirthTF);
	}
	
	// Adds a new customer to the 'Customers' table that has values specified by the user (using the text fields)
	public void addCustomCustomer() {
		try {
			Connection connection = Database.getConnection();
			String query = "INSERT INTO Customers VALUES (?, ?, ?)";
			PreparedStatement stm = connection.prepareStatement(query);
			
			// 'Integer.parseInt(Insert String Here)' turns the 'String' between the parenthesis into an 'int' (unless there are letters inside of the String, then it will crash)
			stm.setInt(1, Integer.parseInt(customerIDTF.getText())); // patientIDTF.getText() gets the text that is inside of the patient id text field
			stm.setString(2, customerNameTF.getText()); // patientNameTF.getText() gets the text that is inside of the patient name text field
			stm.setString(3, dateOfBirthTF.getText()); // dateOfBirthTF.getText() gets the text that is inside of the dateOfBirth text field
			stm.executeUpdate();
			// The line below is ran if the query executes successfully. It shows a JOptionPane (an alert) telling the user that the patient has been added to the database.
			JOptionPane.showMessageDialog(null, "The new customer was added to the database!", "Customer Added!", JOptionPane.DEFAULT_OPTION);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
}