package com.restaurant;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.sql.*;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class createCustomerAccount {

	public JFrame frame;
	private JTextField usernameTF;
	private JTextField pswdTF;
	private JTextField fullNameTF;
	private JTextField addressTF;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					createCustomerAccount window = new createCustomerAccount();
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
	public createCustomerAccount() {
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 * @wbp.parser.entryPoint
	 */
	public void initialize() {
		frame = new JFrame("Create New Account");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		setLookAndFeel();
		Database.connect();
		setupClosingDBConnection();
		
		usernameTF = new JTextField();
		usernameTF.setBounds(72, 32, 310, 20);
		frame.getContentPane().add(usernameTF);
		usernameTF.setColumns(10);
		
		pswdTF = new JTextField();
		pswdTF.setBounds(72, 88, 310, 20);
		frame.getContentPane().add(pswdTF);
		pswdTF.setColumns(10);
		
		fullNameTF = new JTextField("");
		fullNameTF.setBounds(72, 144, 310, 20);
		frame.getContentPane().add(fullNameTF);
		fullNameTF.setColumns(10);
		
		addressTF = new JTextField();
		addressTF.setBounds(72, 196, 310, 20);
		frame.getContentPane().add(addressTF);
		addressTF.setColumns(10);
		
		String addressString = addressTF.getText();
		
		JLabel usernameLBL = new JLabel("Username");
		usernameLBL.setBounds(72, 11, 310, 14);
		frame.getContentPane().add(usernameLBL);
		
		JLabel pswdLBL = new JLabel("Password");
		pswdLBL.setBounds(72, 63, 310, 14);
		frame.getContentPane().add(pswdLBL);
		
		JLabel fullNameLBL = new JLabel("Full Name");
		fullNameLBL.setBounds(72, 119, 310, 14);
		frame.getContentPane().add(fullNameLBL);
		
		JLabel addressLBL = new JLabel("Address");
		addressLBL.setBounds(72, 171, 310, 14);
		frame.getContentPane().add(addressLBL);
		
		newCustomerButton();
	}
	
	public void newCustomerButton() {
		JButton newCustomerButton = new JButton("Create new Customer Account");
		newCustomerButton.setBounds(72, 221, 310, 29);
		frame.getContentPane().add(newCustomerButton);
		newCustomerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addNewCustomer();
				goToCustomerSignIn();
			}
		});
	}
	public void goToCustomerSignIn() {
		frame.dispose();
		CustomerSignIn CSI = new CustomerSignIn(); 
		CSI.initialize();
		CSI.frame.setVisible(true);
	}
	public void addNewCustomer() {
		try {
			Connection connection = Database.connection;
			String query = "INSERT INTO Customers VALUES (?, ?, ?, ?)";
			PreparedStatement stm = connection.prepareStatement(query);
			
			// 'Integer.parseInt(Insert String Here)' turns the 'String' between the parenthesis into an 'int' (unless there are letters inside of the String, then it will crash)
			stm.setString(1, usernameTF.getText()); // patientIDTF.getText() gets the text that is inside of the patient id text field
			stm.setString(2, pswdTF.getText()); // patientNameTF.getText() gets the text that is inside of the patient name text field
			stm.setString(3, fullNameTF.getText()); // dateOfBirthTF.getText() gets the text that is inside of the dateOfBirth text field
			stm.setString(4,  addressTF.getText());
			stm.executeUpdate();
			// The line below is ran if the query executes successfully. It shows a JOptionPane (an alert) telling the user that the patient has been added to the database.
			JOptionPane.showMessageDialog(null, "The new customer was added to the database!", "Customer Added!", JOptionPane.DEFAULT_OPTION);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
//Makes the UI look modern if the user is on Windows
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
