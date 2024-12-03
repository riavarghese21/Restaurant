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

import com.restaurant.Database;

public class AdminDeleteItem {

	public JFrame frame;
	private static JComboBox<String> itemCB;
	static DefaultComboBoxModel<String> itemCBModel = new DefaultComboBoxModel<String>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminDeleteItem window = new AdminDeleteItem();
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
	public AdminDeleteItem() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame("Delete Item");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel selectItemLBL = new JLabel("Select Item");
		selectItemLBL.setBounds(105, 24, 134, 14);
		frame.getContentPane().add(selectItemLBL);
		
		itemCB = new JComboBox<String>();
		itemCB.setBounds(105, 82, 192, 22);
		frame.getContentPane().add(itemCB);
		
		JButton deleteButton = new JButton("Delete Item");
		deleteButton.setBounds(117, 202, 180, 23);
		frame.getContentPane().add(deleteButton);
		
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteItem();
			}
		});

		populateComboBox();
	}
	public void populateComboBox() {
		try {
			Connection connection = Database.connection; // Connect to database
			Statement stm = connection.createStatement(); // Create statement
			String query = "SELECT * FROM Menu"; // Enter the query
			
			itemCBModel = new DefaultComboBoxModel<String>();
			
			ResultSet result = stm.executeQuery(query); // Execute the query
			while (result.next()) {
				String itemName = result.getString("item_name");
				itemCBModel.addElement(itemName);
			}
			
			itemCB.setModel(itemCBModel);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	public void deleteItem() {
		try {
			Connection connection = Database.connection;
			String query = "DELETE FROM Menu WHERE item_name = ?";
			PreparedStatement stm = connection.prepareStatement(query);
			String value = itemCB.getSelectedItem().toString();
			stm.setString(1, value);
			stm.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "Item Deleted Successfully!", "", JOptionPane.DEFAULT_OPTION);
			populateComboBox();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
