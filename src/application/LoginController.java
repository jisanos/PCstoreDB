package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import UtilityDB.ConnectDB;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import manager.managerController;
import store.storeController;

public class LoginController implements Initializable{
	
	Model logModel = new Model();
	
	private static String usernameString;
	
	@FXML
	private Label status;
	@FXML 
	private TextField username;
	@FXML
	private PasswordField password;
	@FXML
	private ComboBox<selection> selectionbox;  //the drop down where user selects manager or user
	@FXML
	private Button login; //used later on to call the new GUI scene
	@FXML
	private Label attempt;
		
	public static String getUsername() {
		return usernameString;
	}
	public static void setUsername(String n) {
		usernameString = n;
	}
	
	public void initialize(URL link, ResourceBundle rb) {
		if(this.logModel.checkConnection()) {  //unsure
			 this.status.setText("Connected");
		}
		else {
			this.status.setText("No Connection");
		}
		
		this.selectionbox.setItems(FXCollections.observableArrayList(selection.values())); //Drop down for manager and user
	}
	
	@FXML
	public void loginMethod(ActionEvent event) {
		
		String opt = ((selection)this.selectionbox.getValue()).toString();
		
		try {
			
			if(this.logModel.login(this.username.getText(), this.password.getText(), opt)){
				
				
				
				Stage stage = (Stage)this.login.getScene().getWindow();
				stage.close();
				
				switch(opt) {
				
				case "MANAGER":
					managerLog();
					break;
				case "USER":
					userLog();
					break;
					
				}
			
			}
			else 
			{
				this.attempt.setText("Login Failed");
			}
						
		}catch(Exception exception) {
		
			System.err.print(exception);
		}
		
		
	}
	
	public void managerLog() { //method that activates the manager pane
		
		try {
		
			Stage mngrStage = new Stage();
			
			FXMLLoader loader = new FXMLLoader();
			
			Pane root = (Pane) loader.load(getClass().getResource("/manager/manager.fxml").openStream());
			
			managerController managerCtrl = (managerController)loader.getController();
			
			Scene scene = new Scene(root);
			
			mngrStage.setScene(scene);
			mngrStage.setTitle("Manager");
			mngrStage.setResizable(false);
			mngrStage.show();
			
			
		}
		catch(IOException cls){
			cls.printStackTrace();
		}
	}
	
	public void userLog() {
		
		setUsername(username.getText()); //set the username as a static private string for later use in the program (other packages)
										
		try {
			Stage usrStage = new Stage();
			
			FXMLLoader loader = new FXMLLoader();
			
			Pane root = (Pane) loader.load(getClass().getResource("/store/user.fxml").openStream());
			
			storeController usrCtrl = (storeController)loader.getController();
			
			Scene scene = new Scene(root);
			
			usrStage.setScene(scene);

			usrStage.setTitle("User");
			
			usrStage.setResizable(false);
			
			usrStage.show();
						
			
		}
		catch(IOException cls)
		{
			cls.printStackTrace();
		}
		
	}
	
	@FXML
	private TextField createusername;
	
	@FXML
	private TextField createfirstname;
	
	@FXML
	private TextField createlastname;
	
	@FXML
	private PasswordField createpasword;
	
	@FXML
	private PasswordField recreatepassword;
	
	@FXML
	private TextField createaddress;
	
	@FXML
	private TextField insertbalance;
	
	@FXML
	private Label passwordnotifier;
	
	private int usrID()throws SQLException {
		
		int count = 0;
			
		try {
			
			Connection connection = ConnectDB.getConnection();
			
			ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM useduserid");
			
			rs.next();
				
			count = rs.getInt(1);
			
			PreparedStatement ps = connection.prepareStatement("UPDATE useduserid SET id = ? WHERE id = ?");
			
			ps.setInt(1, count+1);
			ps.setInt(2, count);
			
			ps.execute();
			
			ps.close();
			connection.close();
			rs.close();
			
				
		}
		catch(SQLException e) {
			System.err.print(e);
		}
		return (count+1);
	}
	
	@FXML
	public void createUser(ActionEvent event) throws SQLException{
		
		
		if(createpasword.getText().equals(recreatepassword.getText()))
		{
			try {
				
				Connection connection = ConnectDB.getConnection();
				
				PreparedStatement ps = connection.prepareStatement("INSERT INTO users(username, password, firstname, lastname,balance,address,userID) VALUES(?,?,?,?,?,?,?)");
				
				ps.setString(1, this.createusername.getText());
				
				ps.setString(2, this.createpasword.getText());
				
				ps.setString(3, this.createfirstname.getText());
				
				ps.setString(4, this.createlastname.getText());
				
				ps.setInt(5, Integer.parseInt(this.insertbalance.getText()));
				
				ps.setString(6, this.createaddress.getText());
				
				ps.setInt(7, usrID());
				
				ps.execute();
				
				passwordnotifier.setText("Account Succesfully Created");
				
				ps.close();
				connection.close();
			
			}catch(SQLException e) {
				System.err.print(e);
			}
		}
		else
		{
			passwordnotifier.setText("Passwords do not match.");
		}
		
	}
	
}
