package com.restaurant;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
    public static Connection connection;

    // Method to establish a connection
    public static void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurantdb?serverTimezone=EST", "root", "Ria212002");
            System.out.println("Database connected successfully.");
        } catch (Exception e) {
            System.out.println("Error connecting to database: " + e);
        }
    }

    // Method to return the established connection
    public static Connection getConnection() {
        if (connection == null) {
            connect(); // Establish the connection if it hasn't been created yet
        }
        return connection;
    }
}
