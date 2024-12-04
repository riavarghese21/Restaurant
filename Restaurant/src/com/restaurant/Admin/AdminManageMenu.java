package com.restaurant.Admin;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class AdminManageMenu {

	public JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminManageMenu window = new AdminManageMenu();
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
	public AdminManageMenu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame("Manage Menu");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton addItemButton = new JButton("Add Item");
		addItemButton.setBounds(72, 62, 310, 29);
		frame.getContentPane().add(addItemButton);
		
		addItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToAddItem();
			}
		});

		
		JButton editItemButton = new JButton("Edit Item");
		editItemButton.setBounds(72, 102, 310, 29);
		frame.getContentPane().add(editItemButton);
		
		editItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToEditItem();
			}
		});
		
		JButton deleteItemButton = new JButton("Delete Item");
		deleteItemButton.setBounds(72, 142, 310, 29);
		frame.getContentPane().add(deleteItemButton);
		
		deleteItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToDeleteItem();
			}
		});
        JButton backButton = new JButton("Back");
        backButton.setBounds(50, 220, 80, 25);
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
	public void goToAddItem() {
		frame.dispose();
		AdminAddItem AAI = new AdminAddItem(); 
		AAI.initialize();
		AAI.frame.setVisible(true);
	}
	
	public void goToEditItem() {
		frame.dispose();
		AdminEditItemPrice AEI = new AdminEditItemPrice(); 
		AEI.initialize();
		AEI.frame.setVisible(true);
	}
	
	public void goToDeleteItem() {
		frame.dispose();
		AdminDeleteItem ADI = new AdminDeleteItem(); 
		ADI.initialize();
		ADI.frame.setVisible(true);
	}
	public void setVisible(boolean visible) {
		frame.setVisible(visible);
    }
}
