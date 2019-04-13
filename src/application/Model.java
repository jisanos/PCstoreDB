package application;

import UtilityDB.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Model {
	
	Connection connection;
	
	public Model() {
		try {
			this.connection = ConnectDB.getConnection();
			
		}
		catch(SQLException cls) {
			cls.printStackTrace();
		}
	
		if(this.connection == null)
		{
			System.exit(1);
		}
	}
	
	public boolean checkConnection() {
		
		return (this.connection != null);
	}
	
	public boolean login(String usr, String passwd, String option) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
		
		if(option.equals("MANAGER")) {
			sql = "SELECT * FROM managers where username = ? and password = ?";
						
		}
		else if(option.equals("USER")) {
			sql = "SELECT * FROM users where username = ? and password = ?";
			
		}
		
			ps = this.connection.prepareStatement(sql);
			ps.setString(1, usr); //email
			ps.setString(2, passwd);
			
			
			rs = ps.executeQuery();
			
			boolean flag;
			
			
			if(rs.next()) {
				return true;
			}
			return false;
			
		}
		catch(SQLException cls) {
			return false;
		}
		finally {
			ps.close();
			rs.close();
		}
		
		
		
	}
		
	
	
	

}
