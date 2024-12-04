package com.restaurant.Admin;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.UIManager;

import com.restaurant.SignInPage;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

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
		frame.setBounds(100, 100, 500, 350);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblManageEmployees = new JLabel("Manage Employees");
		lblManageEmployees.setHorizontalAlignment(SwingConstants.CENTER);
		lblManageEmployees.setFont(new Font("Lucida Grande", Font.BOLD, 17));
		lblManageEmployees.setBounds(95, 10, 310, 30);
		frame.getContentPane().add(lblManageEmployees);
		
		JButton manageEmployeesButton = new JButton("Create Employee");
		manageEmployeesButton.setBounds(95, 70, 310, 29);
		frame.getContentPane().add(manageEmployeesButton);
		
		manageEmployeesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToCreateNewEmployee();
			}
		});
		
		JButton deleteEmployeesButton = new JButton("Delete Employee");
		deleteEmployeesButton.setBounds(95, 110, 310, 29);
		frame.getContentPane().add(deleteEmployeesButton);
		
		deleteEmployeesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToDeleteEmployee();
			}
		});
		
		JButton editUsernameButton = new JButton("Edit Username");
		editUsernameButton.setBounds(95, 150, 310, 29);
		frame.getContentPane().add(editUsernameButton);
		
		editUsernameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToEditUsername();
			}
		});
		
		JButton editPasswordButton = new JButton("Edit Password");
		editPasswordButton.setBounds(95, 190, 310, 29);
		frame.getContentPane().add(editPasswordButton);
		
		editPasswordButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToEditPassword();
			}
		});
		
		JButton editFullNameButton = new JButton("Edit Name");
		editFullNameButton.setBounds(95, 230, 310, 29);
		frame.getContentPane().add(editFullNameButton);
		editFullNameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToEditFullName();
			}
		});
        JButton backButton = new JButton("Back");
        backButton.setBounds(21, 277, 80, 25);
        frame.getContentPane().add(backButton);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                goToAdminSignedInPage();
            }
        });
	}
    private void goToAdminSignedInPage() {
        frame.dispose();
        AdminSignedIn AdminSignedIn = new AdminSignedIn();
        AdminSignedIn.setVisible(true);
    }
	public void goToCreateNewEmployee() {
		frame.dispose();
		AdminCreateNewEmployee CNE = new AdminCreateNewEmployee(); 
		CNE.initialize();
		CNE.frame.setVisible(true);
	}

	public void goToDeleteEmployee() {
		frame.dispose();
		AdminDeleteEmployee ADE = new AdminDeleteEmployee(); 
		ADE.initialize();
		ADE.frame.setVisible(true);
	}
	
	public void goToEditUsername() {
		frame.dispose();
		AdminEditEmployeeUsername AEEU = new AdminEditEmployeeUsername(); 
		AEEU.initialize();
		AEEU.frame.setVisible(true);
	}
	
	public void goToEditPassword() {
		frame.dispose();
		AdminEditEmployeePassword AEEP = new AdminEditEmployeePassword(); 
		AEEP.initialize();
		AEEP.frame.setVisible(true);
	}
	
	public void goToEditFullName() {
		frame.dispose();
		AdminEditEmployeeFullName AEEFN = new AdminEditEmployeeFullName(); 
		AEEFN.initialize();
		AEEFN.frame.setVisible(true);
	}
	public void setVisible(boolean visible) {
		frame.setVisible(visible);
    }

}
