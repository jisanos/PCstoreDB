package UtilityDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
		
	private static final String sqliteConnection = "jdbc:sqlite:store.sqlite"; //query to connect to the sqlite database
	
	//method that Connects to the sqlite databse
	public static Connection getConnection() throws SQLException {
		
		try {
			Class.forName("org.sqlite.JDBC");
			return DriverManager.getConnection(sqliteConnection);
			
		}
		catch(ClassNotFoundException cls) {
			
			cls.printStackTrace();
		}
	return null;
	}
	
	
	

}
