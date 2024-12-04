package com.restaurant.Admin;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

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
		
		JLabel lblManageCustomers = new JLabel("Manage Customers");
		lblManageCustomers.setHorizontalAlignment(SwingConstants.CENTER);
		lblManageCustomers.setFont(new Font("Lucida Grande", Font.BOLD, 17));
		lblManageCustomers.setBounds(75, 10, 310, 16);
		frame.getContentPane().add(lblManageCustomers);

		JButton deleteCustomerButton = new JButton("Delete Customer");
		deleteCustomerButton.setBounds(72, 62, 310, 29);
		frame.getContentPane().add(deleteCustomerButton);
		
		deleteCustomerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToDeleteCustomer();
			}
		});
		
		JButton editUsernameButton = new JButton("Edit Username");
		editUsernameButton.setBounds(75, 138, 310, 29);
		frame.getContentPane().add(editUsernameButton);
		
		editUsernameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToEditUsername();
			}
		});
		
		JButton editPasswordButton = new JButton("Edit Password");
		editPasswordButton.setBounds(75, 179, 310, 29);
		frame.getContentPane().add(editPasswordButton);
		
		editPasswordButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToEditPassword();
			}
		});
		
		JButton editFullNameButton = new JButton("Edit Name");
		editFullNameButton.setBounds(75, 99, 310, 29);
		frame.getContentPane().add(editFullNameButton);
		
		editFullNameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToEditFullName();
			}
		});
        JButton backButton = new JButton("Back");
        backButton.setBounds(25, 220, 80, 25);
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
	public void setVisible(boolean visible) {
		frame.setVisible(visible);
    }
}
