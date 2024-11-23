package com.restaurant;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.UIManager;

public class CustomerSignedIn {

	public JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CustomerSignedIn window = new CustomerSignedIn();
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
	public CustomerSignedIn() {
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
		paymentInfoButton();
	}
	
	public void changePasswordButton() {
		frame.getContentPane().setLayout(null);
		JButton changePasswordButton = new JButton("Change Password");
		changePasswordButton.setBounds(57, 49, 310, 29);
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
		changeNameButton.setBounds(57, 89, 310, 29);
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
		changeAddressButton.setBounds(57, 129, 310, 29);
		frame.getContentPane().add(changeAddressButton);
		changeAddressButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToCustomerChangeAddress();
			}
		});
	}
	public void goToCustomerPaymentInfo() {
		frame.dispose();
		CustomerPaymentInfo CPI = new CustomerPaymentInfo(); 
		CPI.initialize();
		CPI.frame.setVisible(true);
	}
	public void paymentInfoButton() {
		JButton paymentInfoButton = new JButton("Enter Payment Information");
		paymentInfoButton.setBounds(57, 209, 310, 29);
		frame.getContentPane().add(paymentInfoButton);
		paymentInfoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToCustomerPaymentInfo();
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
