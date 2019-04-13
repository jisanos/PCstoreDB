package UtilityDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
	
	//private static final String userName = "user";
	//private static final String passWord = "password";
	//private static final String connection = "jdbc:mysql://localhost/login";
	
	private static final String sqliteConnection = "jdbc:sqlite:store.sqlite"; //constant string that connects to sqlite
	
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
