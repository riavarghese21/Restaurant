package com.restaurant.Admin;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.restaurant.Database;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

public class AdminEditEmployeeUsername {

	public JFrame frame;
	static DefaultComboBoxModel<String> employeeCBModel = new DefaultComboBoxModel<String>();
	private JLabel passwordLBL;
	private JTextField textField;
	private JPasswordField passwordField;
	private JTextField nameTF;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminEditEmployeeUsername window = new AdminEditEmployeeUsername();
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
	public AdminEditEmployeeUsername() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame("Edit Username");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("Modify Info");
		btnNewButton.setBounds(165, 204, 89, 23);
		frame.getContentPane().add(btnNewButton);
		
		JLabel usernameLBL = new JLabel("Username");
		usernameLBL.setBounds(72, 11, 145, 14);
		frame.getContentPane().add(usernameLBL);
		
		passwordLBL = new JLabel("Password");
		passwordLBL.setBounds(72, 73, 86, 14);
		frame.getContentPane().add(passwordLBL);
		
		textField = new JTextField();
		textField.setBounds(72, 36, 86, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(72, 98, 89, 20);
		frame.getContentPane().add(passwordField);
		
		JLabel nameLBL = new JLabel("Full Name");
		nameLBL.setBounds(72, 129, 46, 14);
		frame.getContentPane().add(nameLBL);
		
		nameTF = new JTextField();
		nameTF.setBounds(72, 154, 86, 20);
		frame.getContentPane().add(nameTF);
		nameTF.setColumns(10);		
	}
	

}
