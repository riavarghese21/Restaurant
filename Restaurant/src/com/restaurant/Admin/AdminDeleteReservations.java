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

public class AdminDeleteReservations {

	public JFrame frame;
	private static JComboBox<String> reservationCB;
	static DefaultComboBoxModel<String> reservationCBModel = new DefaultComboBoxModel<String>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminDeleteReservations window = new AdminDeleteReservations();
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
	public AdminDeleteReservations() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame("Delete Reservations");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel selectReservationLBL = new JLabel("Select Reservation");
		selectReservationLBL.setBounds(105, 24, 134, 14);
		frame.getContentPane().add(selectReservationLBL);
		
		reservationCB = new JComboBox<String>();
		reservationCB.setBounds(105, 82, 192, 22);
		frame.getContentPane().add(reservationCB);
		
		JButton deleteButton = new JButton("Delete Reservation");
		deleteButton.setBounds(117, 202, 180, 23);
		frame.getContentPane().add(deleteButton);
		
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteReservation();
			}
		});

		populateComboBox();

	}
	public void populateComboBox() {
		try {
			Connection connection = Database.connection; // Connect to database
			Statement stm = connection.createStatement(); // Create statement
			String query = "SELECT * FROM Reservations"; // Enter the query
			
			reservationCBModel = new DefaultComboBoxModel<String>();
			
			ResultSet result = stm.executeQuery(query); // Execute the query
			while (result.next()) {
				String itemName = result.getString("reservation_id");
				reservationCBModel.addElement(itemName);
			}
			
			reservationCB.setModel(reservationCBModel);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	public void deleteReservation() {
		try {
			Connection connection = Database.connection;
			String query = "DELETE FROM Reservations WHERE reservation_id = ?";
			PreparedStatement stm = connection.prepareStatement(query);
			String value = reservationCB.getSelectedItem().toString();
			stm.setString(1, value);
			stm.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "Reservation Deleted Successfully!", "", JOptionPane.DEFAULT_OPTION);
			populateComboBox();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
