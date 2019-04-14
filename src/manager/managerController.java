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
	
	private ConnectDB connect;
	
	private ObservableList<userInf> inf;
	
	private String sql = "SELECT * FROM users";
	
	public void initialize(URL url, ResourceBundle rb) {
		
		this.connect = new ConnectDB();
		
	}
	
	@FXML
	private void loadUserInf(ActionEvent event)throws SQLException{
		try {
			Connection connection = ConnectDB.getConnection();
			this.inf = FXCollections.observableArrayList();
			
			ResultSet rs = connection.createStatement().executeQuery(sql);
			
			while(rs.next()) {
				
				
				
				this.inf.add(new userInf(rs.getString(1), rs.getString(3), rs.getString(4), rs.getInt(5)));

			}
			
		}
		catch(SQLException cls) {
			System.err.println("Error "+cls);
		}
		
		this.usercolumn.setCellValueFactory(new PropertyValueFactory<userInf, String>("USERNAME"));
		
		this.firstnamecolumn.setCellValueFactory(new PropertyValueFactory<userInf, String>("FIRSTNAME"));
		
		this.lastnamecolumn.setCellValueFactory(new PropertyValueFactory<userInf, String>("LASTNAME"));
		
		this.balancecolumn.setCellValueFactory(new PropertyValueFactory<userInf, Integer>("BALANCE"));
		
		this.usertable.setItems(null);
		
		this.usertable.setItems(this.inf);
	}
	
	@FXML
	private void removeUser(ActionEvent event) throws SQLException {
		String deleteSql = "DELETE FROM users WHERE username = ?";
		
		try {
			Connection connection = ConnectDB.getConnection();
			
			PreparedStatement ps = connection.prepareStatement(deleteSql);
			
			ps.setString(1, this.userremove.getText());
			
			ps.execute();
			
			connection.close();
			
			
		}catch(SQLException cls) {
			System.err.println("Error "+cls);
		}
		
		
	}
	
	


	
}
