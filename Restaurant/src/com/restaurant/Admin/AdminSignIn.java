package com.restaurant.Admin;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.*;

import com.restaurant.Database;
import com.restaurant.SignInPage;

public class AdminSignIn {

	public JFrame frame;
	private static JTextField usernameTF;
	private static JPasswordField pswdTF;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminSignIn window = new AdminSignIn();
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
	public AdminSignIn() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame("Admin Sign In");
        frame.setBounds(100, 100, 400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
		
		signInButton();
		
		JLabel loginLabel = new JLabel("Admin Login");
        loginLabel.setFont(new Font("Lucida Grande", Font.BOLD, 17));
        loginLabel.setBounds(150, 21, 150, 30);
        frame.getContentPane().add(loginLabel);
		
		usernameTF = new JTextField();
		usernameTF.setBounds(139, 80, 180, 25);
		frame.getContentPane().add(usernameTF);
		usernameTF.setColumns(10);
		
		pswdTF = new JPasswordField();
		pswdTF.setBounds(139, 120, 180, 25);
		frame.getContentPane().add(pswdTF);
		pswdTF.setColumns(10);
		
		JLabel usernameLBL = new JLabel("Username");
		usernameLBL.setBounds(66, 81, 80, 25);
		frame.getContentPane().add(usernameLBL);
		
		JLabel pswdLBL = new JLabel("Password");
		pswdLBL.setBounds(66, 121, 80, 25);
		frame.getContentPane().add(pswdLBL);

        JButton backButton = new JButton("Back");
        backButton.setBounds(25, 229, 80, 25);
        frame.getContentPane().add(backButton);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                goToSignInPage();
            }
        });
	}
    private void goToSignInPage() {
        frame.dispose();
        SignInPage signInPage = new SignInPage();
        signInPage.setVisible(true);
    }
	public void signInButton() {
		JButton signInBTN = new JButton("Sign-in");
		signInBTN.setBounds(150, 170, 100, 30);
		frame.getContentPane().add(signInBTN);
		signInBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connect();
				//Add if statement so users with no account can't go to the page
				goToAdminSignedIn();
			}
		});
	}
	public void goToAdminSignedIn() {
		frame.dispose();
		AdminSignedIn SIC = new AdminSignedIn(); 
		SIC.initialize();
		SIC.frame.setVisible(true);
	}

    public static void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String databaseName = "restaurantdb";
            String username = usernameTF.getText(); String password = new String(pswdTF.getPassword());
            Database.connection = DriverManager.getConnection("jdbc:mysql://localhost/" + databaseName + "?serverTimesone=EST", username, password);
            System.out.println("Database connected successfully.");
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Driver not found: " + e.getMessage());
        }
    }
	public void setVisible(boolean visible) {
		frame.setVisible(visible);
    }
}
