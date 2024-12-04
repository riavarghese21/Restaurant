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
import java.awt.Font;
import javax.swing.SwingConstants;

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
		frame = new JFrame("Delete Review");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblDeleteItem = new JLabel("Delete Review");
		lblDeleteItem.setHorizontalAlignment(SwingConstants.CENTER);
		lblDeleteItem.setFont(new Font("Lucida Grande", Font.BOLD, 17));
		lblDeleteItem.setBounds(75, 10, 310, 16);
		frame.getContentPane().add(lblDeleteItem);
		
		JLabel selectItemLBL = new JLabel("Select review");
		selectItemLBL.setBounds(135, 83, 134, 14);
		frame.getContentPane().add(selectItemLBL);
		
		reviewCB = new JComboBox<String>();
		reviewCB.setBounds(135, 109, 192, 22);
		frame.getContentPane().add(reviewCB);
		
		JButton deleteButton = new JButton("Delete Review");
		deleteButton.setBounds(135, 189, 192, 23);
		frame.getContentPane().add(deleteButton);
		
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteReview();
			}
		});

		populateComboBox();
        JButton backButton = new JButton("Back");
        backButton.setBounds(25, 230, 80, 25);
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
