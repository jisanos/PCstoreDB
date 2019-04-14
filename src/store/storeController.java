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
import javafx.scene.control.cell.PropertyValueFactory;

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
		String searched = this.search.getText();
		
		System.out.println(searched);
		
		try {
			
			Connection connection = ConnectDB.getConnection();
			
			this.Inf = FXCollections.observableArrayList();
			
			ResultSet rs = connection.createStatement().executeQuery(sql);
			
			while(rs.next()) {
				
				if(searched.equals("")) {
				
					String storage = rs.getString(4); //stores the item category for comparison
					
					
					if((storage).toUpperCase().equals(opt)) {
					
						this.Inf.add(new itemInf(rs.getString(1),rs.getString(2),rs.getInt(3), storage));
					}
					else if(opt.equals("ALL")) {
						this.Inf.add(new itemInf(rs.getString(1),rs.getString(2),rs.getInt(3), storage));
					}
				}
				else {
					
					String storageitem = rs.getString(1); //stores the itemname for comparison
					
					String storageopt = rs.getString(4);
					
					System.out.println(storageitem.toUpperCase().contains(searched.toUpperCase()));
					
					if(storageitem.toUpperCase().contains(searched.toUpperCase()) && storageopt.toUpperCase().equals(opt)) {
						this.Inf.add(new itemInf(storageitem, rs.getString(2),rs.getInt(3), storageopt));
					}
					
					else if(storageitem.toUpperCase().contains(searched.toUpperCase()) && opt.toUpperCase().equals("ALL")) {
						this.Inf.add(new itemInf(storageitem, rs.getString(2),rs.getInt(3),storageopt));
					}

				}
			}

		}catch(SQLException cls) {
			System.err.println("error "+cls);
		}
		this.itemname.setCellValueFactory(new PropertyValueFactory<itemInf, String>("NAME"));
		
		this.specs.setCellValueFactory(new PropertyValueFactory<itemInf, String>("SPECS"));

		this.price.setCellValueFactory(new PropertyValueFactory<itemInf, Integer>("PRICE"));
		
		this.categorycol.setCellValueFactory(new PropertyValueFactory<itemInf, String>("CATEGORY"));
		
		this.storetable.setItems(null);
		
		this.storetable.setItems(this.Inf);
		
		
	}
	

	
	
	

}
