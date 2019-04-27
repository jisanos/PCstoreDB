package store;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import UtilityDB.ConnectDB;
import application.LoginController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class myAccountController {
	
	@FXML
	private Label usernamelabel;
	@FXML
	private Label passwordlabel;
	@FXML
	private Label firstnamelabel;
	@FXML
	private Label lastnamelabel;
	@FXML
	private Label balancelabel;
	@FXML
	private Label addresslabel;
	
	public void getUserInfo() {
		
		try {
			Connection connection = ConnectDB.getConnection();
			
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE username = ?"); //selection statement
			
			ps.setString(1, LoginController.getUsername()); //sets = ?
			
			ResultSet rs = ps.executeQuery();
	
			this.usernamelabel.setText(rs.getString(1));
			
			this.passwordlabel.setText(rs.getString(2));
			
			this.firstnamelabel.setText(rs.getString(3));
			
			this.lastnamelabel.setText(rs.getString(4));
			
			this.balancelabel.setText(Integer.toString(rs.getInt(5)) + " $");
			
			this.addresslabel.setText(rs.getString(6));
			
			ps.close();
			rs.close();
			connection.close();
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		
	}

}
