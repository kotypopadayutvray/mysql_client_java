package com.mgolikov.main;

import java.sql.*;

public class Main {
	public static void main(String[] args) {
		String jdbc_driver = "com.mysql.jdbc.Driver";
		String host = "jdbc:mysql://localhost/my_db?useSSL=true";
		String password = "myawesomepassword";
		String username = "myawesomeusername";
		Connection connection = null;
		Statement stmt = null;

		try {
			Class.forName(jdbc_driver);
			connection = DriverManager.getConnection(host, username, password);
			stmt = connection.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM book WHERE id = 1");

			while (rs.next()) {
				int id  = rs.getInt("id");
				String name = rs.getString("name");
				int year = rs.getInt("publ_year");
				System.out.print("ID: " + id);
				System.out.print(", name: " + name);
				System.out.println(", year: " + year);
			}

			rs.close();
			stmt.close();
			connection.close();
		} catch(SQLException se) {
			se.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(stmt!=null)
				stmt.close();
			} catch(SQLException se2) { }

			try {
				if(connection!=null)
				connection.close();
			} catch(SQLException se){
				se.printStackTrace();
			}
		}
	}
}
