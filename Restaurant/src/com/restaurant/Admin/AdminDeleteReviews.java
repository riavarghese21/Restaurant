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

public class AdminDeleteReviews {

	public JFrame frame;
	private static JComboBox<String> reviewCB;
	static DefaultComboBoxModel<String> reviewCBModel = new DefaultComboBoxModel<String>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminDeleteReviews window = new AdminDeleteReviews();
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
	public AdminDeleteReviews() {
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
		
		JLabel selectItemLBL = new JLabel("Select review");
		selectItemLBL.setBounds(105, 24, 134, 14);
		frame.getContentPane().add(selectItemLBL);
		
		reviewCB = new JComboBox<String>();
		reviewCB.setBounds(105, 82, 192, 22);
		frame.getContentPane().add(reviewCB);
		
		JButton deleteButton = new JButton("Delete Review");
		deleteButton.setBounds(117, 202, 180, 23);
		frame.getContentPane().add(deleteButton);
		
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteReview();
			}
		});

		populateComboBox();
	}
	public void populateComboBox() {
		try {
			Connection connection = Database.connection; // Connect to database
			Statement stm = connection.createStatement(); // Create statement
			String query = "SELECT * FROM Reviews"; // Enter the query
			
			reviewCBModel = new DefaultComboBoxModel<String>();
			
			ResultSet result = stm.executeQuery(query); // Execute the query
			while (result.next()) {
				String itemName = result.getString("review_title");
				reviewCBModel.addElement(itemName);
			}
			
			reviewCB.setModel(reviewCBModel);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	public void deleteReview() {
		try {
			Connection connection = Database.connection;
			String query = "DELETE FROM Reviews WHERE review_title = ?";
			PreparedStatement stm = connection.prepareStatement(query);
			String value = reviewCB.getSelectedItem().toString();
			stm.setString(1, value);
			stm.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "Review Deleted Successfully!", "", JOptionPane.DEFAULT_OPTION);
			populateComboBox();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
