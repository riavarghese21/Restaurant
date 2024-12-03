package com.restaurant.Admin;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.restaurant.Database;

public class AdminEditItemPrice {

	public JFrame frame;
	private static JComboBox<String> menuCB;
	static DefaultComboBoxModel<String> menuCBModel = new DefaultComboBoxModel<String>();
	private JLabel ItemLBL;
	private JTextField newPrice;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminEditItemPrice window = new AdminEditItemPrice();
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
	public AdminEditItemPrice() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame("Edit Item");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton modifyButton = new JButton("Modify Price");
		modifyButton.setBounds(165, 204, 89, 23);
		frame.getContentPane().add(modifyButton);
		
		modifyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editCustomerUsername();
			}
		});
		
		JLabel usernameLBL = new JLabel("Old Price");
		usernameLBL.setBounds(165, 24, 88, 14);
		frame.getContentPane().add(usernameLBL);
		
		ItemLBL = new JLabel("New Price");
		ItemLBL.setBounds(165, 86, 88, 14);
		frame.getContentPane().add(ItemLBL);
		
		menuCB = new JComboBox<String>();
		menuCB.setBounds(165, 49, 88, 22);
		frame.getContentPane().add(menuCB);
		
		newPrice = new JTextField();
		newPrice.setBounds(165, 111, 89, 20);
		frame.getContentPane().add(newPrice);
		newPrice.setColumns(10);
		
		populateComboBox();
	}
	
	public void populateComboBox() {
		try {
			Connection connection = Database.connection; // Connect to database
			Statement stm = connection.createStatement(); // Create statement
			String query = "SELECT * FROM Menu"; // Enter the query
			
			menuCBModel = new DefaultComboBoxModel<String>();
			
			ResultSet result = stm.executeQuery(query); // Execute the query
			while (result.next()) {
				String customerName = result.getString("item_name");
				menuCBModel.addElement(customerName);
			}
			
			menuCB.setModel(menuCBModel);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void editCustomerUsername() {
		try {
			Connection connection = Database.connection;
			String query = "UPDATE Menu SET item_price = ? WHERE item_name = ?";
			PreparedStatement stm = connection.prepareStatement(query);
			String itemName = menuCB.getSelectedItem().toString();
			stm.setString(1, newPrice.getText());
			stm.setString(2, itemName);
			stm.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "Price Changed Successfully!", "", JOptionPane.DEFAULT_OPTION);
			populateComboBox();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
