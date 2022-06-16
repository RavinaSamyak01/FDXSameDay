package fdxsdtest;

//~--- JDK imports ------------------------------------------------------------
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author hgohil
 */
import java.util.ArrayList;
import java.util.List;

public class DataAccess {

	private static Boolean dumpFlag = Boolean.FALSE;
	private static String driverName;
	private static String dbName;
	private static String connURL;
	private static String username;
	private static String password;

	/**
	 * NetLInkGX TEST Connection Info
	 */
	public static void setGXTestConnectInfo() {
		driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

		dbName = "ngldevgxsql02";
		connURL = "jdbc:sqlserver://" + dbName + ":1433;database=Dev_Test;integratedSecurity=false;";
		username = "ngl_qa";
		password = "!rvine";
	}

	/**
	 * NetLInkGX Staging Connection Info
	 */
	public static void setGXStagingConnectInfo() {
		driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		dbName = "stg-nglgxsql01";
		connURL = "jdbc:sqlserver://" + dbName + ":1433;database=netlinkGX_STG;integratedSecurity=false;";
		username = "ngl_stg";
		password = "samyak";
	}

	/**
	 * NetLInkGX Production Connection Info
	 */
	public static void setGXProdConnectInfo() {
		driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		dbName = "nglgxsql02";
		connURL = "jdbc:sqlserver://" + dbName + ":1433;database=netlinkGX;integratedSecurity=false;";
		username = "ngl_prod";
		password = "Ahmedabad";
	}

	/**
	 * NVS05 Connection Info
	 */
	public static void setNVS05ConnectInfo() {
		driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		dbName = "nvs05";
		connURL = "jdbc:sqlserver://" + dbName + ":1433;database=netlink2;integratedSecurity=false;";
		username = "read_only";
		password = "ngl_readonly";
	}

	/**
	 * NCS19 Connection Info
	 */
	public static void setNCS19ConnectInfo() {
		driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		dbName = "ncs19";
		connURL = "jdbc:sqlserver://" + dbName + ":1433;database=netlink2;integratedSecurity=false;";
		username = "read_only";
		password = "ngl_readonly";
	}

	public static void SetDumpFlagOn() {
		dumpFlag = Boolean.TRUE;
	}

	public static void SetDumpFlagOff() {
		dumpFlag = Boolean.FALSE;
	}

	/**
	 * @param query
	 *
	 * @return
	 */
	public static ResultSet getRSFromDB(String query) {
		ResultSet rs = null;

		try {

			// Create a result set containing all data from my_table
			Connection conn = getJDBCConnection();
			Statement stmt = conn.createStatement();

			rs = stmt.executeQuery(query);

			// stmt.close();
			// conn.close();
		} catch (SQLException e) {
			System.out.println("JDBC RS Error " + e.toString());
		}

		return rs;
	}

	/**
	 * @param query
	 * @param conn
	 * @return
	 */
	public static ResultSet getRSFromDB(String query, Connection conn) {
		ResultSet rs = null;

		try {

			// Create a result set containing all data from my_table
			Statement stmt = conn.createStatement();

			rs = stmt.executeQuery(query);

			// stmt.close();
			// conn.close();
		} catch (SQLException e) {
			System.out.println("JDBC RS Error " + e.toString());
		}

		return rs;
	}
	/*
	 * 
	 */

	public static int execQueryOnDB(String query) {
		int count = 0;
		Connection conn = null;
		Statement stmt = null;
		try {
			// Create a result set containing all data from my_table
			conn = getJDBCConnection();
			stmt = conn.createStatement();
			count = stmt.executeUpdate(query);

		} catch (SQLException e) {
			System.out.println("JDBC RS Error " + e.toString());
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				System.out.println("SQL Execution Error" + query + "\n" + ex.toString());
			}
		}
		return count;
	}

	/**
	 * @param query
	 *
	 * @return
	 */
	public static int[] getIntArray(String query) {
		List<List<String>> list = getListFromDBUsingJDBC(query);
		int[] intArr = new int[list.size()];

		for (int i = 0; i < list.size(); i++) {
			List<String> row = list.get(i);
			String strValue = row.get(0); // assuming only one column
			intArr[i] = Integer.parseInt(strValue);
		}

		return intArr;
	}

	/**
	 * @param query
	 * @param conn
	 * @return
	 */
	public static int[] getIntArray(String query, Connection conn) {
		List<List<String>> list = getListFromDBUsingJDBC(query, conn);
		int[] intArr = new int[list.size()];

		for (int i = 0; i < list.size(); i++) {
			List<String> row = list.get(i);
			String strValue = row.get(0); // assuming only one column

			intArr[i] = Integer.parseInt(strValue);
		}

		return intArr;
	}

	/**
	 * @param query
	 * @param column
	 * @param conn
	 * @return
	 */
	public static int[] getIntArray(String query, int column, Connection conn) {
		List<List<String>> list = getListFromDBUsingJDBC(query, conn);
		int[] intArr = new int[list.size()];

		for (int i = 0; i < list.size(); i++) {
			List<String> row = list.get(i);
			String strValue = row.get(column); // assuming only one column
			intArr[i] = Integer.parseInt(strValue);
		}

		return intArr;
	}

	/**
	 * @param query
	 * @param column
	 *
	 * @return
	 */
	public static int[] getIntArray(String query, int column) {
		List<List<String>> list = getListFromDBUsingJDBC(query);
		int[] intArr = new int[list.size()];

		for (int i = 0; i < list.size(); i++) {
			List<String> row = list.get(i);
			String strValue = row.get(column); // assuming only one column
			intArr[i] = Integer.parseInt(strValue);
		}

		return intArr;
	}

	/**
	 * @param query
	 *
	 * @return
	 */
	public static String[] getStringArray(String query) {
		List<List<String>> list = getListFromDBUsingJDBC(query);
		String[] strArr = new String[list.size()];

		for (int i = 0; i < list.size(); i++) {
			List<String> row = list.get(i);
			String strValue = row.get(0); // assuming only one column

			strArr[i] = strValue.trim();
		}

		return strArr;
	}

	/**
	 * @param query
	 *
	 * @return
	 */
	public static String getString(String query) {
		List<List<String>> list = getListFromDBUsingJDBC(query);
		String[] strArr = new String[list.size()];

		for (int i = 0; i < list.size(); i++) {
			List<String> row = list.get(i);
			String strValue = row.get(0); // assuming only one column

			strArr[i] = strValue.trim();
		}

		return strArr[0];
	}

	/**
	 * @param query
	 * @param conn
	 *
	 * @return
	 */
	public static String[] getStringArray(String query, Connection conn) {
		List<List<String>> list = getListFromDBUsingJDBC(query, conn);
		String[] strArr = new String[list.size()];

		for (int i = 0; i < list.size(); i++) {
			List<String> row = list.get(i);
			String strValue = row.get(0); // assuming only one column

			strArr[i] = strValue.trim();
		}

		return strArr;
	}

	/**
	 * @param query
	 * @param column
	 *
	 * @return
	 */
	public static String[] getStringArray(String query, int column) {
		List<List<String>> list = getListFromDBUsingJDBC(query);
		String[] strArr = new String[list.size()];

		for (int i = 0; i < list.size(); i++) {
			List<String> row = list.get(i);
			String strValue = row.get(column); // assuming only one column

			strArr[i] = strValue.trim();
		}

		return strArr;
	}

	/**
	 * @param query
	 * @param column
	 * @param conn
	 *
	 * @return
	 */
	public static String[] getStringArray(String query, int column, Connection conn) {
		List<List<String>> list = getListFromDBUsingJDBC(query, conn);
		String[] strArr = new String[list.size()];

		for (int i = 0; i < list.size(); i++) {
			List<String> row = list.get(i);
			String strValue = row.get(column); // assuming only one column

			strArr[i] = strValue.trim();
		}

		return strArr;
	}

	/**
	 * @return
	 */
	public static Connection getJDBCConnection() {
		Connection connection = null;

		try {

			// Load the JDBC driver
			Class.forName(driverName);

			// Create a connection to the database
			connection = DriverManager.getConnection(connURL, username, password);
		} catch (ClassNotFoundException e) {
			System.out.println("DB Driver NOT found");

			// Could not find the database driver
		} catch (SQLException e) {
			System.out.println("Error while Connecting " + e.toString());

			// Could not connect to the database
		}

		return connection;
	}

	/**
	 * @param sql
	 *
	 * @return
	 */
	public static List<List<String>> getListFromDBUsingJDBC(String sql) {
		ResultSet rs = null;
		List<List<String>> list = null;

		try {

			// Create a result set containing all data from my_table
			Connection conn = getJDBCConnection();
			Statement stmt = conn.createStatement();

			rs = stmt.executeQuery(sql);
			list = JdbcRS2List(rs);

			if (dumpFlag) {
				dumpList(list);
			}

			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("JDBC RS Error " + e.toString());
		}

		return list;
	}

	/**
	 * @param sql
	 * @param conn
	 * @return
	 */
	public static List<List<String>> getListFromDBUsingJDBC(String sql, Connection conn) {
		ResultSet rs = null;
		List<List<String>> list = null;

		try {

			// Create a result set containing all data from my_table
			Statement stmt = conn.createStatement();

			rs = stmt.executeQuery(sql);
			list = JdbcRS2List(rs);

			if (dumpFlag) {
				dumpList(list);
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			System.out.println("JDBC RS Error " + e.toString());
		}

		return list;
	}

	/**
	 * @param rs
	 *
	 * @return
	 */
	public static List<List<String>> JdbcRS2List(ResultSet rs) {
		List<List<String>> list = new ArrayList<List<String>>();

		try {
			int numColumns = rs.getMetaData().getColumnCount();

			while (rs.next()) {
				ArrayList<String> row = new ArrayList<String>();

				for (int i = 1; i <= numColumns; i++) {

					// Object colValue = rs.getObject( i );
					String colValue = rs.getString(i);

					if (colValue != null) {
						row.add(colValue);
					} else {
						row.add("");
					}
				}

				list.add(row);
			}
		} catch (SQLException e) {
			System.out.println("Error in List conversion " + e.toString());
		}

		// dumpList( list );
		return list;
	}

	/**
	 * @param list
	 */
	public static void dumpList(List<List<String>> list) {
		for (int i = 0; i < list.size(); i++) {

			List<String> row = list.get(i);

			for (int j = 0; j < row.size(); j++) {
				try {
					String obj = row.get(j);

					System.out.print(obj + "\t");
				} catch (NullPointerException npe) {
					System.out.println("");
				}
			}

			System.out.println("");
		}
	}
}
