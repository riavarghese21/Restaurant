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

public class CustomerPaymentInfo {

	public JFrame frame;
	private JTextField usernameTF;
	private JTextField cardNumberTF;
	private JTextField securityCodeTF;
	private JTextField expirationTF;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CustomerPaymentInfo window = new CustomerPaymentInfo();
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
	public CustomerPaymentInfo() {
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 * @wbp.parser.entryPoint
	 */
	public void initialize() {
		frame = new JFrame("Input Payment Info");
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
		
		cardNumberTF = new JTextField();
		cardNumberTF.setBounds(72, 88, 310, 20);
		frame.getContentPane().add(cardNumberTF);
		cardNumberTF.setColumns(10);
		
		securityCodeTF = new JTextField("");
		securityCodeTF.setBounds(72, 144, 310, 20);
		frame.getContentPane().add(securityCodeTF);
		securityCodeTF.setColumns(10);
		
		expirationTF = new JTextField();
		expirationTF.setBounds(72, 196, 310, 20);
		frame.getContentPane().add(expirationTF);
		expirationTF.setColumns(10);
		
		JLabel usernameLBL = new JLabel("Username");
		usernameLBL.setBounds(72, 11, 310, 14);
		frame.getContentPane().add(usernameLBL);
		
		JLabel cardNumberLBL = new JLabel("Card Number");
		cardNumberLBL.setBounds(72, 63, 310, 14);
		frame.getContentPane().add(cardNumberLBL);
		
		JLabel securityCodeLBL = new JLabel("Security Code");
		securityCodeLBL.setBounds(72, 119, 310, 14);
		frame.getContentPane().add(securityCodeLBL);
		
		JLabel expirationLBL = new JLabel("Expiration MM/YY");
		expirationLBL.setBounds(72, 171, 310, 14);
		frame.getContentPane().add(expirationLBL);
		
		newPaymentButton();
	}
	
	public void newPaymentButton() {
		JButton newCustomerButton = new JButton("Add card");
		newCustomerButton.setBounds(72, 221, 310, 29);
		frame.getContentPane().add(newCustomerButton);
		newCustomerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addPaymentInfo();
			}
		});
	}
	public void addPaymentInfo() {
		try {
			Connection connection = Database.connection;
			String query = "INSERT INTO PaymentInfo VALUES (?, ?, ?, ?)";
			PreparedStatement stm = connection.prepareStatement(query);
			String encryptedCardNumber = Encryption.encrypt(cardNumberTF.getText());
			String encryptedSecurityCode = Encryption.encrypt(securityCodeTF.getText());
			String encryptedExpiration = Encryption.encrypt(expirationTF.getText());
			stm.setString(1, encryptedCardNumber);
			stm.setString(2, encryptedSecurityCode);
			stm.setString(3, encryptedExpiration);
			stm.setString(4,  usernameTF.getText());
			
			stm.executeUpdate();

			JOptionPane.showMessageDialog(null, "Payment Information Added!", "", JOptionPane.DEFAULT_OPTION);
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
	
	// Lets say a user is signing up. This code would add them to the database as a new user.
	public static void insertUserIntoDatabase(int userID, String username, String encryptedPassword) {
		try {
			Connection connection = Database.connection;
			// Let's say that there is a table in our 'doctors_office' database called 'Users' and it has the following columns: 'user_id' (int), 'username' (varchar/String), and 'password' (varchar/String)
			String query = "INSERT INTO Users VALUES (?, ?, ?)";
			PreparedStatement stm = connection.prepareStatement(query);
			stm.setInt(1, userID); // <- This will be the new user's userID (not sensitive data - not encrypted)
			stm.setString(2, username); // <- This will be the new user's username (not sensitive data - not encrypted)
			stm.setString(3, encryptedPassword); // <- This will be the new user's password (sensitive data - encrypted)
			stm.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	// This method gets the encrypted password from the database for the newly created user and returns it back to where the method was called
	public static String retrievePasswordFromDatabase(int userID) {
		try {
			Connection connection = Database.connection; // Connect to database
			String query = "SELECT * FROM Users WHERE user_id = " + userID; // Enter the query
			Statement stm = connection.createStatement(); // Create statement
			ResultSet result = stm.executeQuery(query); // Execute the query
			
			result.first();
			return result.getString("password");
		} catch (Exception e) {
			System.out.println(e);
			return "";
		}
	}
}
