package com.restaurant;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class CustomerSignIn {

	public JFrame frame;
	private static JTextField usernameTF;
	private static JTextField pswdTF;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CustomerSignIn window = new CustomerSignIn();
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
	public CustomerSignIn() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame("Customer Sign in page");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		setLookAndFeel();
		signInButton();
		createAccountButton();
		
		usernameTF = new JTextField();
		usernameTF.setBounds(72, 36, 310, 29);
		frame.getContentPane().add(usernameTF);
		usernameTF.setColumns(10);
		
		pswdTF = new JTextField();
		pswdTF.setBounds(72, 94, 310, 29);
		frame.getContentPane().add(pswdTF);
		pswdTF.setColumns(10);
		
		JLabel usernameLBL = new JLabel("Username");
		usernameLBL.setBounds(72, 11, 310, 14);
		frame.getContentPane().add(usernameLBL);
		
		JLabel pswdLBL = new JLabel("Password");
		pswdLBL.setBounds(72, 76, 310, 14);
		frame.getContentPane().add(pswdLBL);
	}
	public void signInButton() {
		JButton signInBTN = new JButton("Sign-in");
		signInBTN.setBounds(72, 134, 310, 29);
		frame.getContentPane().add(signInBTN);
		signInBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connect();
			}
		});
	}
	public void createAccountButton() {
		JButton createAccountBtn = new JButton("Create Account");
		createAccountBtn.setBounds(72, 221, 310, 29);
		frame.getContentPane().add(createAccountBtn);
		createAccountBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToCreateAccountPage();
			}
		});
	}
	public void goToCreateAccountPage() {
		frame.dispose();
		createCustomerAccount CCA = new createCustomerAccount(); 
		CCA.initialize();
		CCA.frame.setVisible(true);
	}

    public static void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String databaseName = "restaurantdb";
            String username = usernameTF.getText(); String password = pswdTF.getText();
            Database.connection = DriverManager.getConnection("jdbc:mysql://localhost/" + databaseName + "?serverTimesone=EST", username, password);
            System.out.println("Database connected successfully.");
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Driver not found: " + e.getMessage());
        }
    }
	
	//Makes the UI look modern if the user is on Windows
	public void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) { }
	}
}
