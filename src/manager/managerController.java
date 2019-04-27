package manager;

import java.util.ResourceBundle;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import UtilityDB.ConnectDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


public class managerController implements Initializable{
	
	//all of the GUI options in the manager pane
	
	@FXML
	private TextField userremove;
	
	@FXML
	private TableView<userInf> usertable;

	@FXML
	private TableColumn<userInf, String> usercolumn;
	
	@FXML
	private TableColumn<userInf, String> firstnamecolumn;
	
	@FXML
	private TableColumn<userInf, String> lastnamecolumn;
	
	@FXML
	private TableColumn<userInf, Integer> balancecolumn;
	
	@FXML
	private TableColumn<userInf, String> addresscolumn;
	
	private ConnectDB connect;
	
	private ObservableList<userInf> inf;
	
	private String sql = "SELECT * FROM users";
	
	public void initialize(URL url, ResourceBundle rb) {
		
		this.connect = new ConnectDB();
		
	}
	
	//load button action event
	@FXML
	private void loadUserInf(ActionEvent event)throws SQLException{
		try {
			Connection connection = ConnectDB.getConnection();
			this.inf = FXCollections.observableArrayList();
			//excecute the sql statement and save it in resultset
			ResultSet rs = connection.createStatement().executeQuery(sql); //"SELECT * FROM users"
			
			while(rs.next()) {
					//get all of the data int the result set (obtained from the database) and sava it in the inf observable list
				this.inf.add(new userInf(rs.getString(1), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getString(6)));

			}
			
		}
		catch(SQLException cls) {
			System.err.println("Error "+cls);
		}
		
		//place all the data saved in the observable list into their respective columns in the GUI
		
		this.usercolumn.setCellValueFactory(new PropertyValueFactory<userInf, String>("USERNAME"));
		
		this.firstnamecolumn.setCellValueFactory(new PropertyValueFactory<userInf, String>("FIRSTNAME"));
		
		this.lastnamecolumn.setCellValueFactory(new PropertyValueFactory<userInf, String>("LASTNAME"));
		
		this.balancecolumn.setCellValueFactory(new PropertyValueFactory<userInf, Integer>("BALANCE"));
		
		this.addresscolumn.setCellValueFactory(new PropertyValueFactory<userInf, String>("ADDRESS"));
		
		this.usertable.setItems(null);
		
		this.usertable.setItems(this.inf);
	}
	
	//action event for the remove user button in the GUI
	@FXML
	private void removeUser(ActionEvent event) throws SQLException {
		String deleteSql = "DELETE FROM users WHERE username = ?"; //delete from the users table where the username is equal to
		
		try {
			Connection connection = ConnectDB.getConnection();
			
			PreparedStatement ps = connection.prepareStatement(deleteSql); 
			
			ps.setString(1, this.userremove.getText());//finish the script by inserting the entered string
			
			ps.execute(); //excecute query
			
			connection.close(); 
			
			
		}catch(SQLException cls) {
			System.err.println("Error "+cls);
		}
		
		
	}
	
	


	
}
