package com.mgolikov.mysqlconnection;

import java.sql.*;
import java.util.ArrayList;

public class MysqlConnection {
	private final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private final String START_URL = "jdbc:mysql://";
	private final String CONNECT_PARAMS = "?useSSL=true";

	private Connection connection = null;
	private Statement statement = null;
	private ResultSet result_set = null;

	public MysqlConnection(String host, String database, String user, String password) {
		String connection_host = START_URL + host + '/' + database + CONNECT_PARAMS;
		try {
			Class.forName(JDBC_DRIVER);
			connection = DriverManager.getConnection(connection_host, user, password);
			statement = connection.createStatement();
		} catch(SQLException se) {
			se.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<String> selectQuery(String table_name, String columns) {
		return selectQuery(table_name, columns, "");
	}

	public ArrayList<String> selectQuery(String table_name, String columns, String condition) {
		ArrayList<String> result = new ArrayList<String>();
		// Split string with column's names for using in the future
		String[] columns_splitted = columns.replaceAll(" ", "").split("!");
		// Create a string with query
		String query = "SELECT " + columns + " FROM " + table_name;
		// Check if we have the condition
		if (!condition.isEmpty()) {
			query += " WHERE " + condition;
		}
		// Send query to MySQL database
		try {
			result_set = statement.executeQuery(query);
			// Collect all results to list of strings
			while (result_set.next()) {
				String record_values = new String();
				if (!columns.equals("*")) {
					for (int i = 0; i < columns_splitted.length; i++) {
						record_values += columns_splitted[i] + ": " + result_set.getString(columns_splitted[i]) + "; ";
					}
				} else {
					ResultSetMetaData metadata = result_set.getMetaData();
					int columnCount = metadata.getColumnCount();
					for (int i = 1; i <= columnCount; i++) {
						String column_name = metadata.getColumnName(i);
						record_values += column_name + ": " + result_set.getString(column_name) + "; ";
					}
				}
				result.add(record_values);
			}
		} catch(SQLException se) {
			se.printStackTrace();
		}
		return result;
	}

	public void updateQuery(String table_name, String values) {
		updateQuery(table_name, values, "");
	}

	public void updateQuery(String table_name, String values, String condition) {
		// Create a string with query
		String query = "UPDATE " + table_name + " SET " + values;
		if (condition != "") {
			query += " WHERE " + condition;
		}
		try {
			statement.executeUpdate(query);
		} catch(SQLException se) {
			se.printStackTrace();
		}
	}

	public void insertQuery(String table_name, String columns, String values) {
		// Create a string with query
		String query = "INSERT INTO " + table_name + '(' + columns + ')' + " VALUES (" + values + ')';
		try {
			statement.executeUpdate(query);
		} catch(SQLException se) {
			se.printStackTrace();
		}
	}

	private void closeResultSet() {
		if (result_set == null) {
			return;
		}
		try {
			result_set.close();
		} catch(SQLException se) {
			se.printStackTrace();
		}
	}

	private void closeStatement() {
		if (statement == null) {
			return;
		}
		try {
			statement.close();
		} catch(SQLException se) {
			se.printStackTrace();
		}
	}

	public void closeConnection() {
		closeStatement();
		closeResultSet();
		try {
			connection.close();
		} catch(SQLException se) {
			se.printStackTrace();
		}
	}
}
