package com.restaurant.Customer;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.restaurant.Employee.EmployeeMenu;

public class CustomerAccountSettings {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CustomerAccountSettings window = new CustomerAccountSettings();
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
	public CustomerAccountSettings() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame("Account Settings");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		changePasswordButton();
		changeNameButton();
		changeAddressButton();
		
		JLabel lblNewLabel = new JLabel("Account Settings");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 17));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(75, 21, 310, 29);
		frame.getContentPane().add(lblNewLabel);
		
		 JButton backButton = new JButton("Back");
	        backButton.setBounds(25, 230, 80, 25);
	        frame.getContentPane().add(backButton);

	        backButton.addActionListener(e -> {
	            frame.dispose(); 
	            CustomerSignedIn customerSignedInPage = new CustomerSignedIn(); 
	            customerSignedInPage.setVisible(true); 
	        });
	}
	
	public void changePasswordButton() {
		frame.getContentPane().setLayout(null);
		JButton changePasswordButton = new JButton("Change Password");
		changePasswordButton.setBounds(75, 77, 310, 29);
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
		changeNameButton.setBounds(75, 115, 310, 29);
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
		changeAddressButton.setBounds(75, 155, 310, 29);
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

	 public void setVisible(boolean visible) {
	        frame.setVisible(visible);
	    }

}
