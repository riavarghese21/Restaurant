package com.restaurant.Admin;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

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
		frame.setBounds(100, 100, 500, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Add Item");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 17));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(100, 10, 310, 16);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel NameLBL = new JLabel("Item Name");
		NameLBL.setBounds(111, 151, 68, 14);
		frame.getContentPane().add(NameLBL);
		
		itemNameTF = new JTextField();
		itemNameTF.setBounds(111, 177, 310, 20);
		frame.getContentPane().add(itemNameTF);
		
		JLabel priceLBL = new JLabel("Item Price");
		priceLBL.setBounds(111, 209, 68, 14);
		frame.getContentPane().add(priceLBL);
		
		itemPriceTF = new JTextField();
		itemPriceTF.setBounds(111, 236, 310, 20);
		frame.getContentPane().add(itemPriceTF);
		itemPriceTF.setColumns(10);
		
		JButton createEmployeeButton = new JButton("Add Item");
		createEmployeeButton.setBounds(185, 292, 124, 23);
		frame.getContentPane().add(createEmployeeButton);
		
		createEmployeeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addNewEmployee();
			}
		});
	       JButton backButton = new JButton("Back");
	        backButton.setBounds(25, 329, 80, 25);
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
			String query = "INSERT INTO Menu (item_name, item_price) VALUES (?, ?)";
			PreparedStatement stm = connection.prepareStatement(query);
			stm.setString(1, itemNameTF.getText());
			stm.setDouble(2, Double.parseDouble(itemPriceTF.getText()));

			stm.executeUpdate();

			
			
			JOptionPane.showMessageDialog(null, "Item Added Successfully!", "", JOptionPane.DEFAULT_OPTION);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
