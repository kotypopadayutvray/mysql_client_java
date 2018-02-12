import java.sql.*;

public class MysqlConnection {
	private final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private final String START_URL = "jdbc:mysql://";
	private final String CONNECT_PARAMS = "?useSSL=true";
	
	private Connection connection = null;
	private Statement statement = null;
	private ResultSet result_set = null;

	public MysqlConnection(
		String host,
		String database,
		String user,
		String password
	) {
		String connection_host = JDBC_DRIVER + host + '/' + database + CONNECT_PARAMS;
		Class.forName(jdbc_driver);
		connection = DriverManager.getConnection(connection_host, username, password);
		statement = connection.createStatement();
	}

	public String[] insert_query() {

	}
}
