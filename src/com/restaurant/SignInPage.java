package com.restaurant;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.UIManager;

import com.restaurant.Admin.AdminSignIn;
import com.restaurant.Customer.CustomerSignIn;
import com.restaurant.Employee.EmployeeSignIn;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.*;
import java.awt.event.ActionEvent;


public class SignInPage {
	private JFrame frame;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignInPage window = new SignInPage();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}
	public SignInPage() {
		initialize();
	}
	private void initialize() {
		frame = new JFrame("Choose sign-in option");
		frame.setBounds(100, 100, 450, 253);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		setLookAndFeel(); // Makes the UI look modern if the user is running on Windows
		
		Database.connect(); // Establish connection to database
		setupClosingDBConnection(); // Handles closing the database connection if the user closes the program
		
		customerSignInButton();
		employeeSignInButton();
		adminSignInButton();
	}
	//Makes the UI look modern if the user is on Windows
	public void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) { }
	}
	
	// Handles closing the database connection if the user closes the program
	public static void setupClosingDBConnection() {
	    Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
	        public void run() {
	            Database.closeConnection(); // Use the closeConnection() method to close the connection
	        }
	    }, "Shutdown-thread"));
	}


	public void customerSignInButton() {
		JButton newCustomerButton = new JButton("Customer Sign in");
		newCustomerButton.setBounds(72, 52, 310, 29);
		frame.getContentPane().add(newCustomerButton);
		newCustomerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToCustomerSignIn();
			}
		});
	}
	public void employeeSignInButton() {
		JButton newCustomerButton = new JButton("Employee Sign in");
		newCustomerButton.setBounds(72, 91, 310, 29);
		frame.getContentPane().add(newCustomerButton);
		newCustomerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToEmployeeSignIn();
			}
		});
	}
	public void adminSignInButton() {
		JButton newCustomerButton = new JButton("Admin Sign in");
		newCustomerButton.setBounds(72, 155, 310, 29);
		frame.getContentPane().add(newCustomerButton);
		newCustomerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToAdminSignIn();
			}
		});
	}
	public void goToButtonLabelTextFieldDemoPage() {
		frame.dispose();
		ButtonLabelTextFieldDemo BLTFDP = new ButtonLabelTextFieldDemo(); 
		BLTFDP.initialize();
		BLTFDP.frame.setVisible(true);
	}
	public void goToCustomerSignIn() {
		frame.dispose();
		CustomerSignIn CSI = new CustomerSignIn(); 
		CSI.initialize();
		CSI.frame.setVisible(true);
	}
	public void goToEmployeeSignIn() {
		frame.dispose();
		EmployeeSignIn ESI = new EmployeeSignIn(); 
		ESI.initialize();
		ESI.frame.setVisible(true);
	}
	public void goToAdminSignIn() {
		frame.dispose();
		AdminSignIn ASI = new AdminSignIn(); 
		ASI.initialize();
		ASI.frame.setVisible(true);
	}
}
