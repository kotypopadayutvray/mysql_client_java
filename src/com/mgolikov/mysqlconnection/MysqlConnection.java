package com.mgolikov.mysqlconnection;

import java.sql.*;

public class MysqlConnection {
	private final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private final String START_URL = "jdbc:mysql://";
	private final String CONNECT_PARAMS = "?useSSL=true";

	private Connection connection = null;
	private Statement statement = null;
	private ResultSet result_set = null;

	public MysqlConnection(String host, String database, String user, String password) {
		String connection_host = JDBC_DRIVER + host + '/' + database + CONNECT_PARAMS;
		Class.forName(jdbc_driver);
		connection = DriverManager.getConnection(connection_host, username, password);
		statement = connection.createStatement();
	}

	public ArrayList<String> select_query(String table_name, String columns) {
		return select_query(table_name, columns, "");
	}

	public ArrayList<String> select_query(String table_name, String columns, String condition) {
		ArrayList<String> result = new ArrayList<String>();
		// Split string with column's names for using in the future
		String[] columns_splitted = columns.replaceAll(" ", "").split("!");
		// Create a string with query
		String query = "SELECT " + columns + " FROM " + table_name;
		// Check if we have the condition
		if (condition != "") {
			query += " WHERE " + condition;
		}
		// Send query to MySQL database
		result_set = stmt.executeQuery(query);
		// Collect all results to list of strings
		while (result_set.next()) {
			String record_values = new String();
			for (int i = 0; i < columns_splitted.length; i++) {
				record_values += columns_splitted[i] + ": " + result_set.getString(columns_splitted[i]) + "; ";
			}
			result.add(record_values);
		}
		return result;
	}

	public void update_query(String table_name, String values) {
		update_query(table_name, values, "");
	}

	public void update_query(String table_name, String columns, String values, String condition) {
		// Create a string with query
		String query = "UPDATE " + table_name + " SET" + values;
		if (condition != "") {
			query += " WHERE " + condition;
		}
		stmt.executeUpdate(query);
	}

	public void insert_query(String table_name, String values) {
		insert_query(table_name, values, "");
	}

	public void insert_query(String table_name, String columns, String values, String condition) {
		// Create a string with query
		String query = "INSERT INTO " + table_name + '(' + columns + ')' + " VALUES (" + values + ')';
		stmt.executeUpdate(query);
	}
}
