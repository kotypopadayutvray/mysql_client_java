package com.mgolikov.program;

import java.sql.*;
import java.util.Scanner;
import java.util.ArrayList;
import com.mgolikov.mysqlconnection.MysqlConnection;

public class Program {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter host of DB: ");
		String host = scanner.nextLine();
		System.out.print("Enter name of DB: ");
		String database = scanner.nextLine();
		System.out.print("Enter username of DB: ");
		String username = scanner.nextLine();
		System.out.print("Enter password for DB: ");
		String password = scanner.nextLine();

		System.out.println("Connecting to database...");
		MysqlConnection mysql_conn = new MysqlConnection(host, database, username, password);
		System.out.println("Succesfully connected to database!");

		try {
			int answer;
			while (true) {
				System.out.println("\t\tMENU:");
				System.out.println("* enter 1 for select query;");
				System.out.println("* enter 2 for insert query;");
				System.out.println("* enter 3 for upadte query;");
				System.out.println("* enter 0 for exit;");
				System.out.print("\tYour answer: ");
				answer = Integer.parseInt(scanner.nextLine());
				if (answer == 0) {
					break;
				}
				switch (answer) {
					case 1:
						selectQuery(mysql_conn, scanner);
						break;
					case 2:
						insertQuery(mysql_conn, scanner);
						break;
					case 3:
						updateQuery(mysql_conn, scanner);
						break;
					default:
						System.out.println("You entered a wrong command. Try again.\n\n");
						break;
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			mysql_conn.closeConnection();
		}
	}

	private static void selectQuery(MysqlConnection mysql_conn, Scanner scanner) {
		System.out.print("Enter tablename: ");
		String tablename = scanner.nextLine();
		System.out.print("Enter columns with comma: ");
		String columns = scanner.nextLine();
		System.out.print("Enter condition (if you don't want to use condition enter an empty string): ");
		String condition = scanner.nextLine();
		ArrayList<String> res = mysql_conn.selectQuery(tablename, columns, condition);
		System.out.println("Query results:");
		if (res.size() == 0) {
			System.out.println("No results.");
		}
		for (int i = 0; i < res.size(); i++) {
			System.out.println(res.get(i));
		}
		System.out.println("");
	}

	private static void insertQuery(MysqlConnection mysql_conn, Scanner scanner) {
		System.out.print("Enter tablename: ");
		String tablename = scanner.nextLine();
		System.out.print("Enter columns with comma: ");
		String columns = scanner.nextLine();
		System.out.print("Enter values with comma:");
		String values = scanner.nextLine();
		mysql_conn.insertQuery(tablename, columns, values);
		System.out.println("Values was successfully inserted!\n");
	}

	private static void updateQuery(MysqlConnection mysql_conn, Scanner scanner) {
		System.out.print("Enter tablename: ");
		String tablename = scanner.nextLine();
		System.out.print("Enter values with comma:");
		String values = scanner.nextLine();
		System.out.print("Enter condition (if you don't want to use condition enter an empty string): ");
		String condition = scanner.nextLine();
		mysql_conn.updateQuery(tablename, values, condition);
		System.out.println("Values was successfully updated!\n");
	}
}
