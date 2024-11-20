package com.restaurant;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;

public class CustomerSignIn {

	public JFrame frame;
	private JTextField userNameTF;
	private JTextField pswdTF;

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
		
		createAccountButton();
		
		userNameTF = new JTextField();
		userNameTF.setBounds(72, 36, 310, 29);
		frame.getContentPane().add(userNameTF);
		userNameTF.setColumns(10);
		
		pswdTF = new JTextField();
		pswdTF.setBounds(72, 94, 310, 29);
		frame.getContentPane().add(pswdTF);
		pswdTF.setColumns(10);
		
		JButton signInBtn = new JButton("Sign-in");
		signInBtn.setBounds(72, 134, 310, 29);
		frame.getContentPane().add(signInBtn);
		
		JLabel usernameLBL = new JLabel("Username");
		usernameLBL.setBounds(72, 11, 310, 14);
		frame.getContentPane().add(usernameLBL);
		
		JLabel pswdLBL = new JLabel("Password");
		pswdLBL.setBounds(72, 76, 310, 14);
		frame.getContentPane().add(pswdLBL);
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
}
