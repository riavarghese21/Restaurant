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
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;


public class SignInPage {
    public JFrame frmChooseSigninOption;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignInPage window = new SignInPage();
					window.frmChooseSigninOption.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}
	public SignInPage() {
		initialize();
	}
	public void initialize() {
		frmChooseSigninOption = new JFrame("Choose sign-in option");
		frmChooseSigninOption.setTitle("Choose Sign-in Option");
		frmChooseSigninOption.setBounds(100, 100, 500, 300);
		frmChooseSigninOption.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmChooseSigninOption.getContentPane().setLayout(null);
		
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
		newCustomerButton.setBounds(95, 100, 310, 29);
		frmChooseSigninOption.getContentPane().add(newCustomerButton);
		newCustomerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToCustomerSignIn();
			}
		});
	}
	public void employeeSignInButton() {
		JButton newCustomerButton = new JButton("Employee Sign in");
		newCustomerButton.setBounds(95, 141, 310, 29);
		frmChooseSigninOption.getContentPane().add(newCustomerButton);
		newCustomerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToEmployeeSignIn();
			}
		});
	}
	public void adminSignInButton() {
		JButton newCustomerButton = new JButton("Admin Sign in");
		newCustomerButton.setBounds(95, 182, 310, 29);
		frmChooseSigninOption.getContentPane().add(newCustomerButton);
		
		JLabel lblNewLabel = new JLabel("WELCOME!");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 17));
		lblNewLabel.setBounds(95, 26, 310, 21);
		frmChooseSigninOption.getContentPane().add(lblNewLabel);
		newCustomerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToAdminSignIn();
			}
		});
	}
	public void goToCustomerSignIn() {
		frmChooseSigninOption.dispose();
		CustomerSignIn CSI = new CustomerSignIn(); 
		CSI.initialize();
		CSI.frame.setVisible(true);
	}
	public void goToEmployeeSignIn() {
		frmChooseSigninOption.dispose();
		EmployeeSignIn ESI = new EmployeeSignIn(); 
		ESI.initialize();
		ESI.frame.setVisible(true);
	}
	public void goToAdminSignIn() {
		frmChooseSigninOption.dispose();
		AdminSignIn ASI = new AdminSignIn(); 
		ASI.initialize();
		ASI.frame.setVisible(true);
	}
	public void setVisible(boolean visible) {
		frmChooseSigninOption.setVisible(visible);
    }
}
