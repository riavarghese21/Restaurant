package com.restaurant.Admin;

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
		manageReservationsButton();
		manageOrdersButton();
		deleteReviewsButton();
	}
	
	public void manageEmployeesButton() {
		JButton manageEmployeesButton = new JButton("Manage Employees");
		manageEmployeesButton.setBounds(72, 11, 310, 29);
		frame.getContentPane().add(manageEmployeesButton);
		manageEmployeesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToManageEmployees();
			}
		});
	}
	public void goToManageEmployees() {
		frame.dispose();
		AdminManageEmployees AME = new AdminManageEmployees(); 
		AME.initialize();
		AME.frame.setVisible(true);
	}
	public void manageCustomersButton() {
		JButton manageCustomersButton = new JButton("Manage Customers");
		manageCustomersButton.setBounds(72, 51, 310, 29);
		frame.getContentPane().add(manageCustomersButton);
		manageCustomersButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToManageCustomers();
			}
		});
	}
	public void goToManageCustomers() {
		frame.dispose();
		AdminManageCustomers AMC = new AdminManageCustomers(); 
		AMC.initialize();
		AMC.frame.setVisible(true);
	}
	public void manageMenuButton() {
		JButton manageMenuButton = new JButton("Manage Menu");
		manageMenuButton.setBounds(72, 91, 310, 29);
		frame.getContentPane().add(manageMenuButton);
		manageMenuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToManageMenu();
			}
		});
	}
	public void goToManageMenu() {
		frame.dispose();
		AdminManageMenu AMM= new AdminManageMenu(); 
		AMM.initialize();
		AMM.frame.setVisible(true);
	}
	public void manageReservationsButton() {
		JButton manageReservationsButton = new JButton("Manage Reservations");
		manageReservationsButton.setBounds(72, 131, 310, 29);
		frame.getContentPane().add(manageReservationsButton);
		manageReservationsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToManageReservations();
			}
		});
	}
	public void goToManageReservations() {
		frame.dispose();
		AdminDeleteReservations AMO = new AdminDeleteReservations(); 
		AMO.initialize();
		AMO.frame.setVisible(true);
	}
	public void manageOrdersButton() {
		JButton manageOrdersButton = new JButton("Manage Orders");
		manageOrdersButton.setBounds(72, 181, 310, 29);
		frame.getContentPane().add(manageOrdersButton);
		manageOrdersButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToManageOrders();
			}
		});
	}
	public void goToManageOrders() {
		frame.dispose();
		AdminDeleteOrders AMO = new AdminDeleteOrders(); 
		AMO.initialize();
		AMO.frame.setVisible(true);
	}
	public void deleteReviewsButton() {
		JButton manageReviewsButton = new JButton("Delete Reviews");
		manageReviewsButton.setBounds(72, 221, 310, 29);
		frame.getContentPane().add(manageReviewsButton);
		manageReviewsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToDeleteReviews();
			}
		});
	}
	public void goToDeleteReviews() {
		frame.dispose();
		AdminDeleteReviews ADR = new AdminDeleteReviews(); 
		ADR.initialize();
		ADR.frame.setVisible(true);
	}
	//Makes the UI look modern if the user is on Windows
	public void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) { }
	}
}
