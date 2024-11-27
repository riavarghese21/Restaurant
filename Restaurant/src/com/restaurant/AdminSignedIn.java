package com.restaurant;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.JButton;

public class AdminSignedIn {

	public JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminSignedIn window = new AdminSignedIn();
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
	public AdminSignedIn() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame("Admin");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		setLookAndFeel();
		manageEmployeesButton();
		manageCustomersButton();
		manageMenuButton();
		manageOrdersButton();
	}
	
	public void manageEmployeesButton() {
		JButton manageEmployeesButton = new JButton("Manage Employees");
		manageEmployeesButton.setBounds(72, 28, 310, 29);
		frame.getContentPane().add(manageEmployeesButton);
		manageEmployeesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//goToCustomerSignIn();
			}
		});
	}
	public void manageCustomersButton() {
		JButton manageCustomersButton = new JButton("Manage Customers");
		manageCustomersButton.setBounds(72, 81, 310, 29);
		frame.getContentPane().add(manageCustomersButton);
		manageCustomersButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//goToCustomerSignIn();
			}
		});
	}
	public void manageMenuButton() {
		JButton manageMenuButton = new JButton("Manage Menu");
		manageMenuButton.setBounds(72, 136, 310, 29);
		frame.getContentPane().add(manageMenuButton);
		manageMenuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//goToCustomerSignIn();
			}
		});
	}
	public void manageOrdersButton() {
		JButton manageOrdersButton = new JButton("Manage Reservations/Orders");
		manageOrdersButton.setBounds(72, 193, 310, 29);
		frame.getContentPane().add(manageOrdersButton);
		manageOrdersButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//goToCustomerSignIn();
			}
		});
	}
	//Makes the UI look modern if the user is on Windows
	public void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) { }
	}
}
