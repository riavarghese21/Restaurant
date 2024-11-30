package com.restaurant.Customer;

import javax.swing.*;
import com.restaurant.Database;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerSignIn {

    public JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                CustomerSignIn window = new CustomerSignIn();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the application.
     */
    public CustomerSignIn() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    public void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel loginLabel = new JLabel("Customer Login");
        loginLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        loginLabel.setBounds(125, 20, 150, 30);
        frame.getContentPane().add(loginLabel);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 80, 80, 25);
        frame.getContentPane().add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(150, 80, 180, 25);
        frame.getContentPane().add(usernameField);
        usernameField.setColumns(10);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 120, 80, 25);
        frame.getContentPane().add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 120, 180, 25);
        frame.getContentPane().add(passwordField);
        passwordField.setColumns(10);

        // Login Button
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(175, 174, 100, 30);
        frame.getContentPane().add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loginCustomer();
            }
        });

        JButton backButton = new JButton("Back");
        backButton.setBounds(50, 220, 80, 25);
        frame.getContentPane().add(backButton);
        
        JButton createAccountButton = new JButton("Create Account");
        createAccountButton.setBounds(173, 202, 121, 30);
        frame.getContentPane().add(createAccountButton);
        
        //View Cart
        JButton CreateAccount = new JButton("View Cart");
        CreateAccount.setBounds(468, 515, 117, 29);
        frame.getContentPane().add(CreateAccount);

        
        JButton createAccountButton1 = new JButton("Create Account");
        createAccountButton1.setBounds(173, 202, 121, 30);
        frame.getContentPane().add(createAccountButton1);
        
        createAccountButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                goToCreateAccountPage();
            }
            
        });

    }
    

      private void goToCreateAccountPage() {
                frame.dispose();
                CreateCustomerAccount createCustomerAccount = new CreateCustomerAccount();
                createCustomerAccount.setVisible(true);
            }
      
   
    

    private void loginCustomer() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

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

            String query = "SELECT * FROM Customers WHERE customer_username = ? AND customer_password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Login successful, set customerId in UserSession
                int customerId = resultSet.getInt("customer_id");
                UserSession.getInstance().setCustomerId(customerId);

                JOptionPane.showMessageDialog(frame, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();

                // Open the customer signed-in page
                CustomerSignedIn customerSignedInPage = new CustomerSignedIn();
                customerSignedInPage.frame.setVisible(true);
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
