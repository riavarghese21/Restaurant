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

public class CreateCustomerAccount {

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
					CreateCustomerAccount window = new CreateCustomerAccount();
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
	public CreateCustomerAccount() {
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
			String encryptedPswd = Encryption.encrypt(pswdTF.getText());
			stm.setString(1, usernameTF.getText());
			stm.setString(2, encryptedPswd);
			stm.setString(3, fullNameTF.getText());
			stm.setString(4,  addressTF.getText());

			stm.executeUpdate();


			
			String query2 = "CREATE USER ?@'localhost' IDENTIFIED BY ?";
			PreparedStatement stm2 = connection.prepareStatement(query2);
			stm2.setString(1, usernameTF.getText());
			stm2.setString(2, pswdTF.getText());

			stm2.executeUpdate();
			
			String query3 = "GRANT SELECT, UPDATE ON restaurantdb.Customers TO ?@'localhost'";
			PreparedStatement stm3 = connection.prepareStatement(query3);
			stm3.setString(1, usernameTF.getText());
			
			stm3.executeUpdate();
			
			
			JOptionPane.showMessageDialog(null, "Account Created Successfully!", "Signed In!", JOptionPane.DEFAULT_OPTION);
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
