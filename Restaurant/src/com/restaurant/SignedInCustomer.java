package com.restaurant;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.UIManager;

public class SignedInCustomer {

	public JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignedInCustomer window = new SignedInCustomer();
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
	public SignedInCustomer() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame("");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLookAndFeel();
		
		changePasswordButton();
		changeNameButton();
		changeAddressButton();
	}
	
	public void changePasswordButton() {
		frame.getContentPane().setLayout(null);
		JButton changePasswordButton = new JButton("Change Password");
		changePasswordButton.setBounds(57, 37, 310, 29);
		frame.getContentPane().add(changePasswordButton);
		changePasswordButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToCustomerChangePassword();
			}
		});
	}
	public void goToCustomerChangePassword() {
		frame.dispose();
		CustomerChangePassword CCP = new CustomerChangePassword(); 
		CCP.initialize();
		CCP.frame.setVisible(true);
	}
	public void changeNameButton() {
		JButton changeNameButton = new JButton("Change Name");
		changeNameButton.setBounds(57, 106, 310, 29);
		frame.getContentPane().add(changeNameButton);
		changeNameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToCustomerChangeName();
			}
		});
	}
	public void goToCustomerChangeName() {
		frame.dispose();
		CustomerChangeName CCN = new CustomerChangeName(); 
		CCN.initialize();
		CCN.frame.setVisible(true);
	}
	public void changeAddressButton() {
		JButton changeAddressButton = new JButton("Change Address");
		changeAddressButton.setBounds(57, 186, 310, 29);
		frame.getContentPane().add(changeAddressButton);
		changeAddressButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToCustomerChangeAddress();
			}
		});
	}
	public void goToCustomerChangeAddress() {
		frame.dispose();
		CustomerChangeAddress CCA = new CustomerChangeAddress(); 
		CCA.initialize();
		CCA.frame.setVisible(true);
	}
	
	public void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) { }
	}

}
