package com.restaurant;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.UIManager;

public class AdminManageEmployees {

	public JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminManageEmployees window = new AdminManageEmployees();
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
	public AdminManageEmployees() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame("Manage Employees");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLookAndFeel();
		
		createNewEmployeeButton();
		editEmployeeButton();
	}
	
	public void createNewEmployeeButton() {
		frame.getContentPane().setLayout(null);
		JButton manageEmployeesButton = new JButton("Create Employee");
		manageEmployeesButton.setBounds(72, 70, 310, 29);
		frame.getContentPane().add(manageEmployeesButton);
		manageEmployeesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToCreateNewEmployee();
			}
		});
	}
	public void goToCreateNewEmployee() {
		frame.dispose();
		AdminCreateNewEmployee CNE = new AdminCreateNewEmployee(); 
		CNE.initialize();
		CNE.frame.setVisible(true);
	}
	public void editEmployeeButton() {
		JButton manageEmployeesButton = new JButton("Edit Employee");
		manageEmployeesButton.setBounds(72, 169, 310, 29);
		frame.getContentPane().add(manageEmployeesButton);
		manageEmployeesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToEditEmployee();
			}
		});
	}
	public void goToEditEmployee() {
		frame.dispose();
		AdminEditEmployee AEE = new AdminEditEmployee(); 
		AEE.initialize();
		AEE.frame.setVisible(true);
	}
	
	public void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) { }
	}

}
