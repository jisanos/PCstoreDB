package store;


import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import UtilityDB.ConnectDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class storeController implements Initializable {
	
	@FXML
	private Button searchbutton;
	
	@FXML
	private TextField search;
	
	@FXML
	private ComboBox<Categories> category;
	
	@FXML
	private TableView<itemInf> storetable;
	
	@FXML
	private TableColumn<itemInf, String> itemname;
	
	@FXML
	private TableColumn<itemInf, String> specs;
	
	@FXML
	private TableColumn<itemInf, Integer> price;
	
	@FXML
	private TableColumn<itemInf, String> categorycol;
	
	private ConnectDB connect;
	
	private ObservableList<itemInf> Inf;
	
	private String sql = "SELECT * FROM items";
	
	public void initialize(URL url, ResourceBundle rb) {
		
		this.connect = new ConnectDB();
		
		this.category.setItems(FXCollections.observableArrayList(Categories.values()));

	}
	
	@FXML
	private void searchItem(ActionEvent event)throws SQLException  {
		
		String opt = ((Categories)this.category.getValue()).toString();
		
		try {
			
			Connection connection = ConnectDB.getConnection();
			
			this.Inf = FXCollections.observableArrayList();
			
			ResultSet rs = connection.createStatement().executeQuery(sql);
			
			while(rs.next()) {
				this.Inf.add(new itemInf(rs.getString(1),rs.getString(2),rs.getInt(3), rs.getString(4)));
			}

		}catch(SQLException cls) {
			System.err.println("error "+cls);
		}
		
		
		
	}
	
	
	

}
