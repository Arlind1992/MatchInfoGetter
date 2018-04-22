package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbConnector {
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver"; 
	static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/matches";
	// Database credentials
	static final String USER = "root";
	static final String PASS = "interlostDB";
	private static Connection connection;
	public static void connect() {		
		Connection conn = null;
		Statement stmt = null;
		try{
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			//STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			//STEP 4: Execute a query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT * from teams ";
			ResultSet rs = stmt.executeQuery(sql);
			//STEP 5: Extract data from result set
			while(rs.next()){
			//Retrieve by column name
			int id = rs.getInt("id");
			String age = rs.getString("Name");
			//rs.
			// String first = rs.getString("first");
			// String last = rs.getString("last");
			}
			//STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();
			}catch(SQLException se){
			//Handle errors for JDBC
			se.printStackTrace();
			}catch(Exception e){
			//Handle errors for Class.forName
			e.printStackTrace();
			}finally{
		//finally block used to close resources
		try{
		if(stmt!=null)
		stmt.close();
		}catch(SQLException se2){
		}// nothing we can do
		//end finally try
			}//end try
		System.out.println("Goodbye!");
	}//end main
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		if(connection==null) {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			//STEP 3: Open a connection
			System.out.println("Connecting to database...");
			connection = DriverManager.getConnection(DB_URL,USER,PASS);
			System.out.println("Connection successful");
		}
		return connection;
	}
	public static void closeConnection() {
		try{
			if(connection!=null)
				connection.close();
		}catch(SQLException se){
			se.printStackTrace();
		}
	}
	
}
