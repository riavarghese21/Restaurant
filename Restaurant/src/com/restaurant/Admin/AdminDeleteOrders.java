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

public class AdminDeleteOrders {

	public JFrame frame;
	private JFrame frame_1;
	private static JComboBox<String> orderCB;
	static DefaultComboBoxModel<String> orderCBModel = new DefaultComboBoxModel<String>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminDeleteOrders window = new AdminDeleteOrders();
					window.frame_1.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AdminDeleteOrders() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame("Delete Orders");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		frame_1 = new JFrame("Delete Order");
		frame_1.setBounds(100, 100, 450, 300);
		frame_1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame_1.getContentPane().setLayout(null);
		
		JLabel selectOrderLBL = new JLabel("Select Order");
		selectOrderLBL.setBounds(130, 72, 134, 14);
		frame_1.getContentPane().add(selectOrderLBL);
		
		orderCB = new JComboBox<String>();
		orderCB.setBounds(130, 98, 192, 22);
		frame_1.getContentPane().add(orderCB);
		
		JLabel lblDeleteCustomerAccount = new JLabel("Delete Order");
		lblDeleteCustomerAccount.setHorizontalAlignment(SwingConstants.CENTER);
		lblDeleteCustomerAccount.setFont(new Font("Lucida Grande", Font.BOLD, 17));
		lblDeleteCustomerAccount.setBounds(75, 10, 310, 16);
		frame_1.getContentPane().add(lblDeleteCustomerAccount);
		
		JButton deleteButton = new JButton("Delete Order");
		deleteButton.setBounds(130, 159, 192, 23);
		frame_1.getContentPane().add(deleteButton);
		
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteItem();
			}
		});

		populateComboBox();
        JButton backButton = new JButton("Back");
        backButton.setBounds(25, 220, 80, 25);
        frame_1.getContentPane().add(backButton);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                goToAdminSignedInPage();
            }
        });
	}
    private void goToAdminSignedInPage() {
        frame_1.dispose();
        AdminSignedIn AdminSignedIn = new AdminSignedIn();
        AdminSignedIn.setVisible(true);
    }
	public void populateComboBox() {
		try {
			Connection connection = Database.connection; // Connect to database
			Statement stm = connection.createStatement(); // Create statement
			String query = "SELECT * FROM Orders"; // Enter the query
			
			orderCBModel = new DefaultComboBoxModel<String>();
			
			ResultSet result = stm.executeQuery(query); // Execute the query
			while (result.next()) {
				String itemName = result.getString("order_id");
				orderCBModel.addElement(itemName);
			}
			
			orderCB.setModel(orderCBModel);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	public void deleteItem() {
		try {
			Connection connection = Database.connection;
			String query = "DELETE FROM Orders WHERE order_id = ?";
			PreparedStatement stm = connection.prepareStatement(query);
			String value = orderCB.getSelectedItem().toString();
			stm.setString(1, value);
			stm.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "Order Deleted Successfully!", "", JOptionPane.DEFAULT_OPTION);
			populateComboBox();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
