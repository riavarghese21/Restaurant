package com.restaurant.Customer;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.sql.*;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.restaurant.Database;
import com.restaurant.Encryption;
import com.restaurant.SignInPage;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import java.awt.Font;

public class CreateCustomerAccount {

	public JFrame frame;
	private JTextField usernameTF;
	private JPasswordField pswdTF;
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
		frame.setBounds(100, 100, 600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		setLookAndFeel();
		Database.connect();
		setupClosingDBConnection();
		
		usernameTF = new JTextField();
		usernameTF.setBounds(145, 85, 310, 20);
		frame.getContentPane().add(usernameTF);
		usernameTF.setColumns(10);
		
		pswdTF = new JPasswordField();
		pswdTF.setBounds(145, 138, 310, 20);
		frame.getContentPane().add(pswdTF);
		pswdTF.setColumns(10);
		
		fullNameTF = new JTextField();
		fullNameTF.setBounds(145, 196, 310, 20);
		frame.getContentPane().add(fullNameTF);
		fullNameTF.setColumns(10);
		
		addressTF = new JTextField();
		addressTF.setBounds(145, 254, 310, 20);
		frame.getContentPane().add(addressTF);
		addressTF.setColumns(10);
		
		JLabel usernameLBL = new JLabel("Username");
		usernameLBL.setBounds(145, 59, 310, 14);
		frame.getContentPane().add(usernameLBL);
		
		JLabel pswdLBL = new JLabel("Password");
		pswdLBL.setBounds(145, 112, 310, 14);
		frame.getContentPane().add(pswdLBL);
		
		JLabel fullNameLBL = new JLabel("Full Name");
		fullNameLBL.setBounds(145, 170, 310, 14);
		frame.getContentPane().add(fullNameLBL);
		
		JLabel addressLBL = new JLabel("Address");
		addressLBL.setBounds(145, 228, 310, 14);
		frame.getContentPane().add(addressLBL);
		
		JLabel lblNewLabel = new JLabel("Create an Account");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 17));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(145, 10, 310, 16);
		frame.getContentPane().add(lblNewLabel);
		
		JButton backButton = new JButton("Back");
        backButton.setBounds(30, 330, 80, 25);
        frame.getContentPane().add(backButton);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                goToCustomerSignIn1();
            }
        });
		
		newCustomerButton();
	}
	
	private void goToCustomerSignIn1() {
        frame.dispose();
        CustomerSignIn customerSignIn = new CustomerSignIn();
        customerSignIn.setVisible(true);
    }
    
	
	public void newCustomerButton() {
		JButton newCustomerButton = new JButton("Create New Customer Account");
		newCustomerButton.setBounds(145, 295, 310, 29);
		frame.getContentPane().add(newCustomerButton);
		
		newCustomerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addNewCustomer();
				goToCustomerSignIn1();
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
			String query = "INSERT INTO Customers (customer_name, customer_address, customer_username, customer_password) VALUES (?, ?, ?, ?)";
			PreparedStatement stm = connection.prepareStatement(query);
			String password = new String(pswdTF.getPassword());
			String encryptedPswd = Encryption.encrypt(password);
			stm.setString(1, fullNameTF.getText());
			stm.setString(2, addressTF.getText());
			stm.setString(3, usernameTF.getText());
			stm.setString(4, encryptedPswd);

			stm.executeUpdate();


			
//			String query2 = "CREATE USER ?@'localhost' IDENTIFIED BY ?";
//			PreparedStatement stm2 = connection.prepareStatement(query2);
//			stm2.setString(1, usernameTF.getText());
//			stm2.setString(2, password);
//
//			stm2.executeUpdate();
//			
//			String query3 = "GRANT SELECT, UPDATE ON restaurantdb.Customers TO ?@'localhost'";
//			PreparedStatement stm3 = connection.prepareStatement(query3);
//			stm3.setString(1, usernameTF.getText());
//			
//			stm3.executeUpdate();
			
			
			JOptionPane.showMessageDialog(null, "Account Created Successfully!", "", JOptionPane.DEFAULT_OPTION);
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

	public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
}