package com.restaurant.Employee;

import javax.swing.*;
import com.restaurant.Database;
import com.restaurant.Encryption;
import com.restaurant.SignInPage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeSignIn {

    public JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                EmployeeSignIn window = new EmployeeSignIn();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the application.
     */
    public EmployeeSignIn() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    public void initialize() {
        frame = new JFrame("Employee Sign In");
        frame.setBounds(100, 100, 400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel loginLabel = new JLabel("Employee Login");
        loginLabel.setFont(new Font("Lucida Grande", Font.BOLD, 17));
        loginLabel.setBounds(129, 21, 150, 30);
        frame.getContentPane().add(loginLabel);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(66, 81, 80, 25);
        frame.getContentPane().add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(139, 80, 180, 25);
        frame.getContentPane().add(usernameField);
        usernameField.setColumns(10);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(66, 121, 80, 25);
        frame.getContentPane().add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(139, 120, 180, 25);
        frame.getContentPane().add(passwordField);
        passwordField.setColumns(10);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(150, 170, 100, 30);
        frame.getContentPane().add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loginEmployee();
            }
        });

        JButton backButton = new JButton("Back");
        backButton.setBounds(50, 220, 80, 25);
        frame.getContentPane().add(backButton);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                goToSignInPage();
            }
        });
        
    }
    
    private void goToSignInPage() {
        frame.dispose();
        SignInPage signInPage = new SignInPage();
        signInPage.setVisible(true);
    }
    	

    private void loginEmployee() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String encryptedPassword = Encryption.encrypt(password);

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter both username and password.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Connection connection = Database.getConnection();
            if (connection == null) {
                JOptionPane.showMessageDialog(frame, "Database connection failed.", "Database Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String query = "SELECT * FROM Employees WHERE Employee_username = ? AND Employee_password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, encryptedPassword);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int EmployeeId = resultSet.getInt("Employee_id");
                EmployeeSession.getInstance().setEmployeeId(EmployeeId);

                JOptionPane.showMessageDialog(frame, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();

                EmployeeMenu EmployeeSignedInPage = new EmployeeMenu();
                EmployeeSignedInPage.frame.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "An error occurred while processing your request. Please try again.", "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
}
