package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
import user.userController;

public class LoginController implements Initializable{
	
	Model logModel = new Model();
	
	@FXML
	private Label status;
	@FXML 
	private TextField username;
	@FXML
	private PasswordField password;
	@FXML
	private ComboBox<selection> selectionbox;  //the drop down where user selects manager or user
	@FXML
	private Button login;
	@FXML
	private Label attempt;
	
	
	public void initialize(URL link, ResourceBundle rb) {
		if(this.logModel.checkConnection()) {  //unsure
			 this.status.setText("Connected");
		}
		else {
			this.status.setText("No Connection");
		}
		
		this.selectionbox.setItems(FXCollections.observableArrayList(selection.values()));
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
						
		}
		catch(Exception exception) {
			
		}
		
		
	}
	
	public void managerLog() { //method that activated the manager pane
		
		try {
		
			Stage mngrStage = new Stage();
			
			FXMLLoader loader = new FXMLLoader();
			
			

			
			Pane root = (Pane) loader.load(getClass().getResource("/manager/manager.fxml").openStream());
			
			System.out.println("nuun");
			
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
		
		try {
			Stage usrStage = new Stage();
			
			FXMLLoader loader = new FXMLLoader();
			
			Pane root = (Pane) loader.load(getClass().getResource("/user/user.fxml").openStream());
			
			userController usrCtrl = (userController)loader.getController();
			
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
	
	
}
