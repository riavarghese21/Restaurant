package com.restaurant.Admin;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.UIManager;

public class AdminManageCustomers {

	public JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminManageCustomers window = new AdminManageCustomers();
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
	public AdminManageCustomers() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame("Manage Customers");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JButton deleteCustomerButton = new JButton("Delete Customer");
		deleteCustomerButton.setBounds(72, 62, 310, 29);
		frame.getContentPane().add(deleteCustomerButton);
		
		deleteCustomerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToDeleteCustomer();
			}
		});
		
		JButton editUsernameButton = new JButton("Edit Username");
		editUsernameButton.setBounds(72, 102, 310, 29);
		frame.getContentPane().add(editUsernameButton);
		
		editUsernameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToEditUsername();
			}
		});
		
		JButton editPasswordButton = new JButton("Edit Password");
		editPasswordButton.setBounds(72, 142, 310, 29);
		frame.getContentPane().add(editPasswordButton);
		
		editPasswordButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToEditPassword();
			}
		});
		
		JButton editFullNameButton = new JButton("Edit Name");
		editFullNameButton.setBounds(165, 111, 89, 20);
		frame.getContentPane().add(editFullNameButton);
		editFullNameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToEditFullName();
			}
		});
	}

	public void goToDeleteCustomer() {
		frame.dispose();
		AdminDeleteCustomer ADC = new AdminDeleteCustomer(); 
		ADC.initialize();
		ADC.frame.setVisible(true);
	}
	
	public void goToEditUsername() {
		frame.dispose();
		AdminEditCustomerUsername AECU = new AdminEditCustomerUsername(); 
		AECU.initialize();
		AECU.frame.setVisible(true);
	}
	
	public void goToEditPassword() {
		frame.dispose();
		AdminEditCustomerPassword AECP = new AdminEditCustomerPassword(); 
		AECP.initialize();
		AECP.frame.setVisible(true);
	}
	
	public void goToEditFullName() {
		frame.dispose();
		AdminEditCustomerFullName AECFN = new AdminEditCustomerFullName(); 
		AECFN.initialize();
		AECFN.frame.setVisible(true);
	}

}
