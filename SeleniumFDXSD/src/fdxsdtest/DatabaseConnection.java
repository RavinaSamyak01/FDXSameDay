package fdxsdtest;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
	private static DatabaseConnection instance;
	private Connection connection;

	String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

	
	//====New server=======
	
	String url = "jdbc:sqlserver://10.100.115.64\\SQLEXPRESS;databaseName=netlinkGX_QA";
	String username = "ngl_qa";
	String password = "samyak";

	
	//====old server=======
	// For run in local machine for QA Environment==By Ravina
	/*
	 * String url =
	 * "jdbc:sqlserver://10.100.112.5\\sql2016;databaseName=netlinkGX_QA"; // QA
	 * Local String username = "ngl"; String password = "samyak";
	 */
	// For Server
	/*
	 * String url =
	 * "jdbc:sqlserver://SamyakApp\\SQLEXPRESS2014;databaseName=netlinkGX_QA";
	 * String username = "ngl_qa"; String password = "samyak";
	 */

	DatabaseConnection() {
		try {

			Class.forName(driver);
			connection = DriverManager.getConnection(url, username, password);

			if (connection != null) {
				System.out.println(".. Created Database Connection ..");
				DatabaseMetaData dm = (DatabaseMetaData) connection.getMetaData();
				System.out.println("Driver name: " + dm.getDriverName());
				System.out.println("Driver version: " + dm.getDriverVersion());
				System.out.println("Product name: " + dm.getDatabaseProductName());
				System.out.println("Product version: " + dm.getDatabaseProductVersion());
			}

		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
		}
	}

	public Connection getConnection() {
		return connection;
	}

	public static DatabaseConnection getInstance() throws SQLException {
		if (instance == null) {
			instance = new DatabaseConnection();
		} else if (instance.getConnection().isClosed()) {
			instance = new DatabaseConnection();
		}

		return instance;
	}

	public boolean _insertDataFromDB(String currentDate, double loginTime, double quoteTime) {
		boolean retData = false;
		try {
			Statement statement = connection.createStatement();
//			String sql = "INSERT INTO netlinkGX_QA.dbo.PerformanceReport (EnteredOn, LoginTime, QuoteTime, SystemNumber) Values ('" + currentDate + "', " + loginTime + ", " + quoteTime + ",'SIPL01' )";
//			String sql = "INSERT INTO netlinkGX_QA.dbo.PerformanceReportSTG (EnteredOn, LoginTime, QuoteTime, SystemNumber) Values ('" + currentDate + "', " + loginTime + ", " + quoteTime + ",'SIPL01' )";
			String sql = "INSERT INTO netlinkGX_QA.dbo.PerformanceReport (EnteredOn, LoginTime, QuoteTime, SystemNumber) Values ('"
					+ currentDate + "', " + loginTime + ", " + quoteTime + ",'SamyakAzure' )";
			System.out.println("sql :: " + sql);
			statement.executeUpdate(sql);
			System.out.println("Query executed"); // by ravina
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Query not executed, issue in try statement"); // by ravina
		}
		return retData;
	}

	public static void main(String[] args) {
		DatabaseConnection databaseConnection = new DatabaseConnection();
		databaseConnection.getConnection();
	}
}
