package com.restaurant.Admin;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.restaurant.Database;
import com.restaurant.Encryption;

import javax.swing.JPasswordField;
import javax.swing.JButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminCreateNewEmployee {

	public JFrame frame;
	private JTextField usernameTF;
	private JPasswordField passwordField;
	private JTextField fullNameTF;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminCreateNewEmployee window = new AdminCreateNewEmployee();
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
	public AdminCreateNewEmployee() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame("Create New Employee");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel usernameLBL = new JLabel("Username");
		usernameLBL.setBounds(130, 11, 56, 14);
		frame.getContentPane().add(usernameLBL);
		
		usernameTF = new JTextField();
		usernameTF.setBounds(130, 36, 158, 20);
		frame.getContentPane().add(usernameTF);
		usernameTF.setColumns(10);
		
		JLabel pswdLBL = new JLabel("Password");
		pswdLBL.setBounds(130, 67, 46, 14);
		frame.getContentPane().add(pswdLBL);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(130, 92, 158, 20);
		frame.getContentPane().add(passwordField);
		
		JLabel fullNameLBL = new JLabel("Full Name");
		fullNameLBL.setBounds(130, 123, 46, 14);
		frame.getContentPane().add(fullNameLBL);
		
		fullNameTF = new JTextField();
		fullNameTF.setBounds(130, 148, 158, 20);
		frame.getContentPane().add(fullNameTF);
		fullNameTF.setColumns(10);
		
		JButton createEmployeeButton = new JButton("Create Account");
		createEmployeeButton.setBounds(145, 208, 124, 23);
		frame.getContentPane().add(createEmployeeButton);
		
		createEmployeeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addNewEmployee();
			}
		});
	}
	
	public void addNewEmployee() {
		try {
			Connection connection = Database.connection;
			String query = "INSERT INTO Employees VALUES (?, ?, ?)";
			PreparedStatement stm = connection.prepareStatement(query);
			String password = new String(passwordField.getPassword());
			String encryptedPswd = Encryption.encrypt(password);
			stm.setString(1, usernameTF.getText());
			stm.setString(2, encryptedPswd);
			stm.setString(3, fullNameTF.getText());

			stm.executeUpdate();


			
//			String query2 = "CREATE USER ?@'localhost' IDENTIFIED BY ?";
//			PreparedStatement stm2 = connection.prepareStatement(query2);
//			stm2.setString(1, usernameTF.getText());
//			stm2.setString(2, password);
//
//			stm2.executeUpdate();
//			
//			String query3 = "GRANT SELECT, UPDATE ON restaurantdb.Orders TO ?@'localhost'";
//			PreparedStatement stm3 = connection.prepareStatement(query3);
//			stm3.setString(1, usernameTF.getText());
//			
//			stm3.executeUpdate();
			
			
			JOptionPane.showMessageDialog(null, "Account Created Successfully!", "", JOptionPane.DEFAULT_OPTION);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
