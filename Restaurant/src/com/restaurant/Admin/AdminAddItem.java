package com.restaurant.Admin;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.restaurant.Database;

public class AdminAddItem {

	public JFrame frame;
	private JTextField itemIdTF;
	private JTextField itemNameTF;
	private JTextField itemPriceTF;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminAddItem window = new AdminAddItem();
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
	public AdminAddItem() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame("Add Item");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel IDLBL = new JLabel("Item Id");
		IDLBL.setBounds(130, 11, 56, 14);
		frame.getContentPane().add(IDLBL);
		
		itemIdTF = new JTextField();
		itemIdTF.setBounds(130, 36, 158, 20);
		frame.getContentPane().add(itemIdTF);
		itemIdTF.setColumns(10);
		
		JLabel NameLBL = new JLabel("Item Name");
		NameLBL.setBounds(130, 67, 46, 14);
		frame.getContentPane().add(NameLBL);
		
		itemNameTF = new JTextField();
		itemNameTF.setBounds(130, 92, 158, 20);
		frame.getContentPane().add(itemNameTF);
		
		JLabel priceLBL = new JLabel("Item Price");
		priceLBL.setBounds(130, 123, 46, 14);
		frame.getContentPane().add(priceLBL);
		
		itemPriceTF = new JTextField();
		itemPriceTF.setBounds(130, 148, 158, 20);
		frame.getContentPane().add(itemPriceTF);
		itemPriceTF.setColumns(10);
		
		JButton createEmployeeButton = new JButton("Add Item");
		createEmployeeButton.setBounds(145, 208, 124, 23);
		frame.getContentPane().add(createEmployeeButton);
		
		createEmployeeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addNewEmployee();
			}
		});
	       JButton backButton = new JButton("Back");
	        backButton.setBounds(50, 220, 80, 25);
	        frame.getContentPane().add(backButton);
	        backButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	goToAdminManageMenuPage();
	            }
	        });
		}
	    private void goToAdminManageMenuPage() {
	        frame.dispose();
	        AdminManageMenu AdminManageMenu = new AdminManageMenu();
	        AdminManageMenu.setVisible(true);
	    }
	
	public void addNewEmployee() {
		try {
			Connection connection = Database.connection;
			String query = "INSERT INTO Menu VALUES (?, ?, ?)";
			PreparedStatement stm = connection.prepareStatement(query);
			stm.setInt(1, Integer.parseInt(itemIdTF.getText()));
			stm.setString(2, itemNameTF.getText());
			stm.setDouble(3, Double.parseDouble(itemPriceTF.getText()));

			stm.executeUpdate();

			
			
			JOptionPane.showMessageDialog(null, "Item Added Successfully!", "", JOptionPane.DEFAULT_OPTION);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
