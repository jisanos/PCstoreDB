package manager;

import java.util.ResourceBundle;
import java.net.URL;
import java.sql.Connection;
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
	private TableColumn<userInf, String> balancecolumn;
	
	private ConnectDB connect;
	
	private ObservableList<userInf> inf;
	
	private String sql = "SELECT * FROM users where ";
	
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
				this.inf.add(new userInf(rs.getString(1));
			}
			
		}
		catch(SQLException cls) {
			System.err.println("Error "+cls);
		}
	}
	
	


	
}
