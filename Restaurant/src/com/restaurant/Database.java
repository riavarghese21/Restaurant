package com.restaurant;

import java.sql.Connection;
import java.sql.DriverManager;

class Database {
	public static Connection connection;
	
	public static void connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost/restaurantdb?serverTimezone=EST", "root", "#Jarman28mysql");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}