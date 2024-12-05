package com.restaurant.Employee;

import javax.swing.*;
import com.restaurant.Database;
import com.restaurant.Encryption;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmployeeChangePassword {

    public JFrame frame;
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;
    private int employeeId;

    /**
     * Create the application.
     */
    public EmployeeChangePassword(int employeeId) {
        this.employeeId = employeeId;
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame("Change Password");
        frame.setBounds(100, 100, 400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel changePasswordLabel = new JLabel("Change Password");
        changePasswordLabel.setFont(new Font("Lucida Grande", Font.BOLD, 17));
        changePasswordLabel.setBounds(120, 20, 167, 30);
        frame.getContentPane().add(changePasswordLabel);

        JLabel newPasswordLabel = new JLabel("New Password:");
        newPasswordLabel.setBounds(50, 80, 120, 25);
        frame.getContentPane().add(newPasswordLabel);

        newPasswordField = new JPasswordField();
        newPasswordField.setBounds(180, 80, 150, 25);
        frame.getContentPane().add(newPasswordField);

        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setBounds(50, 120, 120, 25);
        frame.getContentPane().add(confirmPasswordLabel);

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(180, 120, 150, 25);
        frame.getContentPane().add(confirmPasswordField);

        JButton changePasswordButton = new JButton("Change Password");
        changePasswordButton.setBounds(120, 180, 150, 30);
        frame.getContentPane().add(changePasswordButton);

        changePasswordButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changePassword();
            }
        });
    }

    private void changePassword() {
        String newPassword = new String(newPasswordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter both fields.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(frame, "Passwords do not match.", "Mismatch", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String encryptedPassword = Encryption.encrypt(newPassword);

        try {
            Connection connection = Database.getConnection();
            if (connection == null) {
                JOptionPane.showMessageDialog(frame, "Database connection failed.", "Database Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String updateQuery = "UPDATE Employees SET employee_password = ?, first_login = FALSE WHERE employee_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, encryptedPassword);
            preparedStatement.setInt(2, employeeId);

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(frame, "Password changed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();

                EmployeeMenu employeeMenu = new EmployeeMenu();
                employeeMenu.frame.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to change password. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "An error occurred while updating the password. Please try again.", "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
}
