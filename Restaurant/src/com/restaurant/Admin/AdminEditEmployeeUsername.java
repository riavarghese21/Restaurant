package com.restaurant.Admin;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.restaurant.Database;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;

public class AdminEditEmployeeUsername {

	public JFrame frame;
	private static JComboBox<String> employeeCB;
	static DefaultComboBoxModel<String> employeeCBModel = new DefaultComboBoxModel<String>();
	private JLabel newUserLBL;
	private JTextField newUser;
	private JLabel lblEditEmployeeUsername;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminEditEmployeeUsername window = new AdminEditEmployeeUsername();
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
	public AdminEditEmployeeUsername() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame("Edit Username");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		lblEditEmployeeUsername = new JLabel("Edit Employee Username");
		lblEditEmployeeUsername.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditEmployeeUsername.setFont(new Font("Lucida Grande", Font.BOLD, 17));
		lblEditEmployeeUsername.setBounds(75, 10, 310, 20);
		frame.getContentPane().add(lblEditEmployeeUsername);
		
		JButton modifyButton = new JButton("Modify Info");
		modifyButton.setBounds(125, 186, 210, 23);
		frame.getContentPane().add(modifyButton);
		
		modifyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editEmployeeUsername();
			}
		});
		
		JLabel usernameLBL = new JLabel("Old Username");
		usernameLBL.setBounds(125, 57, 210, 14);
		frame.getContentPane().add(usernameLBL);
		
		newUserLBL = new JLabel("New Username");
		newUserLBL.setBounds(125, 117, 210, 14);
		frame.getContentPane().add(newUserLBL);
		
		employeeCB = new JComboBox<String>();
		employeeCB.setBounds(125, 83, 210, 22);
		frame.getContentPane().add(employeeCB);
		
		newUser = new JTextField();
		newUser.setBounds(125, 143, 210, 20);
		frame.getContentPane().add(newUser);
		newUser.setColumns(10);
		
		populateComboBox();
        JButton backButton = new JButton("Back");
        backButton.setBounds(25, 230, 80, 25);
        frame.getContentPane().add(backButton);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	goToAdminManageEmployeesPage();
            }
        });
	}
    private void goToAdminManageEmployeesPage() {
        frame.dispose();
        AdminManageEmployees AdminManageEmployees = new AdminManageEmployees();
        AdminManageEmployees.setVisible(true);
    }
	public void populateComboBox() {
		try {
			Connection connection = Database.connection; // Connect to database
			Statement stm = connection.createStatement(); // Create statement
			String query = "SELECT * FROM Employees"; // Enter the query
			
			employeeCBModel = new DefaultComboBoxModel<String>();
			
			ResultSet result = stm.executeQuery(query); // Execute the query
			while (result.next()) {
				String employeeName = result.getString("employee_username");
				employeeCBModel.addElement(employeeName);
			}
			
			employeeCB.setModel(employeeCBModel);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void editEmployeeUsername() {
		try {
			Connection connection = Database.connection;
			String query = "UPDATE Employees SET employee_username = ? WHERE employee_username = ?";
			PreparedStatement stm = connection.prepareStatement(query);
			String oldUser = employeeCB.getSelectedItem().toString();
			stm.setString(1, newUser.getText());
			stm.setString(2, oldUser);
			stm.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "Username Changed Successfully!", "", JOptionPane.DEFAULT_OPTION);
			populateComboBox();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
