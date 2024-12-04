package com.restaurant.Admin;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.UIManager;

import com.restaurant.SignInPage;

import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

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
		frame.setBounds(100, 100, 500, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		manageEmployeesButton();
		manageCustomersButton();
		manageMenuButton();
		manageReservationsButton();
		manageOrdersButton();
		deleteReviewsButton();
		logoutButton();
	}
    public void logoutButton() {
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(95, 294, 310, 29); // Adjust bounds as per your layout
        frame.getContentPane().add(logoutButton);
     
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Exit the application
            }
        });
    }
	public void manageEmployeesButton() {
		{
			JLabel lblWelcomeAdmin = new JLabel("Welcome Admin!");
			lblWelcomeAdmin.setHorizontalAlignment(SwingConstants.CENTER);
			lblWelcomeAdmin.setFont(new Font("Lucida Grande", Font.BOLD, 17));
			lblWelcomeAdmin.setBounds(95, 10, 310, 16);
			frame.getContentPane().add(lblWelcomeAdmin);
		}
		JButton manageEmployeesButton = new JButton("Manage Employees");
		manageEmployeesButton.setBounds(95, 48, 310, 29);
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
		manageCustomersButton.setBounds(95, 89, 310, 29);
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
		manageMenuButton.setBounds(95, 130, 310, 29);
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
		JButton manageReservationsButton = new JButton("Delete Reservations");
		manageReservationsButton.setBounds(95, 171, 310, 29);
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
		JButton manageOrdersButton = new JButton("Delete Orders");
		manageOrdersButton.setBounds(95, 212, 310, 29);
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
		manageReviewsButton.setBounds(95, 253, 310, 29);
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
	public void setVisible(boolean visible) {
		frame.setVisible(visible);
    }
}
