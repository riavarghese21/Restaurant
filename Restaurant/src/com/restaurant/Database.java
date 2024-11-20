package com.restaurant;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    public static Connection connection;

    // Method to establish a connection
    public static void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurantdb?serverTimezone=EST", "root", "#Jarman28mysql");
            System.out.println("Database connected successfully.");
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Driver not found: " + e.getMessage());
        }
    }

    // Method to return the established connection
    public static Connection getConnection() {
        if (connection == null) {
            connect(); // Establish the connection if it hasn't been created yet
        }
        return connection;
    }

    // Method to close the database connection
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed successfully.");
            } catch (SQLException e) {
                System.out.println("Error closing the database connection: " + e.getMessage());
            }
        }
    }
}
