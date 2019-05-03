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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import store.Categories;
import store.itemInf;


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
		
	private ObservableList<userInf> inf;
			
	public void initialize(URL url, ResourceBundle rb) {	
		
		this.cat.setItems(FXCollections.observableArrayList(Categories.values()));//adds the data to the selection dropbox from the Categories.java enum

		
	}
	
	//load button action event
	@FXML
	private void loadUserInf(ActionEvent event)throws SQLException{
		try {
			Connection connection = ConnectDB.getConnection();
			this.inf = FXCollections.observableArrayList();
			
			ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM users"); //selects all from users table
			
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
	
	@FXML
	private TableView<SalesInf> salestable;
	@FXML
	private TableColumn<SalesInf, String> salesuser;
	@FXML
	private TableColumn<SalesInf, String> salesitem;
	@FXML
	private TableColumn<SalesInf, Integer> salesprice;
	@FXML
	private TableColumn<SalesInf, Integer> salequantity;
	
	private ObservableList<SalesInf> salesInf;

	@FXML
	private void loadSalesInf(ActionEvent event) throws SQLException{
		
		this.salesInf = FXCollections.observableArrayList();
		
		try {
			
			Connection connection = ConnectDB.getConnection();
			
			ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM purchased");
			
			while(rs.next()) {
				int userID = rs.getInt(1);
				int itemID = rs.getInt(2);
				
				PreparedStatement psUsr = connection.prepareStatement("SELECT * FROM users WHERE userID = ?");
				
				psUsr.setInt(1, userID);
				
				ResultSet rsUsr = psUsr.executeQuery();
				
				PreparedStatement psItms = connection.prepareStatement("SELECT * FROM items WHERE ID = ?");
				
				psItms.setInt(1, itemID);
				
				ResultSet rsItms = psItms.executeQuery();
				
				rsItms.next();
				rsUsr.next();
				
				this.salesInf.add(new SalesInf(rsUsr.getString(1), rsItms.getString(1), rsItms.getInt(3), rs.getInt(3)));
				
				
				psUsr.close();
				rsUsr.close();
				psItms.close();
				rsItms.close();
				
			}
						
			connection.close();
			rs.close();
			
		}catch(SQLException e) {
			System.err.print(e);
		}
		
		this.salesuser.setCellValueFactory(new PropertyValueFactory<SalesInf, String>("USERNAME"));
		this.salesitem.setCellValueFactory(new PropertyValueFactory<SalesInf, String>("NAME"));
		this.salesprice.setCellValueFactory(new PropertyValueFactory<SalesInf, Integer>("PRICE"));
		this.salequantity.setCellValueFactory(new PropertyValueFactory<SalesInf, Integer>("QUANTITY"));
		
		this.salestable.setItems(null);
		this.salestable.setItems(this.salesInf);

	}

	@FXML
	private TextField searchbox;
	@FXML
	private ComboBox<Categories> cat;
	@FXML
	private TableView<itemInf> inventorytable;
	@FXML
	private TableColumn<itemInf, String> itemname; 
	@FXML
	private TableColumn<itemInf, String> specs;
	@FXML
	private TableColumn<itemInf, Integer> price;
	@FXML
	private TableColumn<itemInf, String> categorycol;
	@FXML
	private TableColumn<itemInf, Integer> stock;
	@FXML
	private TableColumn<itemInf, Integer> idcol;
	
	private ObservableList<itemInf> itmInf;
	
	@FXML
	private void loadInventory(ActionEvent event) throws SQLException{
		
		String opt = ((Categories)this.cat.getValue()).toString(); //turns the selected category into a string
		
		String searched = this.searchbox.getText(); //holds the searched item
				
		try {
			
			Connection connection = ConnectDB.getConnection(); //creates a connection to the database
			
			this.itmInf = FXCollections.observableArrayList(); 
			
			ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM items"); 
			
			while(rs.next()) {
				
				if(searched.equals("")) {
				
					String storage = rs.getString(4); //stores the item category for comparison
					
					if((storage).toUpperCase().equals(opt)) {
					
						this.itmInf.add(new itemInf(rs.getString(1),rs.getString(2),rs.getInt(3), storage, rs.getInt(5), rs.getInt(6))); //this sends the contents from the database to
					}
					else if(opt.equals("ALL")) {
						this.itmInf.add(new itemInf(rs.getString(1),rs.getString(2),rs.getInt(3), storage, rs.getInt(5), rs.getInt(6)));  //get managed by the itemInf class
					}
				}
				else {
					
					String storageitem = rs.getString(1); //stores the itemname for comparison
					
					String storageopt = rs.getString(4); //stores the category from the item
									
					if(storageitem.toUpperCase().contains(searched.toUpperCase()) && storageopt.toUpperCase().equals(opt)) {
						
						this.itmInf.add(new itemInf(storageitem, rs.getString(2),rs.getInt(3), storageopt, rs.getInt(5), rs.getInt(6)));
					}
					
					else if(storageitem.toUpperCase().contains(searched.toUpperCase()) && opt.toUpperCase().equals("ALL")) {
						
						this.itmInf.add(new itemInf(storageitem, rs.getString(2),rs.getInt(3),storageopt, rs.getInt(5), rs.getInt(6)));
					}
				}
			}
			rs.close();
			connection.close();

		}catch(SQLException cls) {
			
			System.err.println("error "+cls);
		}
		
		//here we set the data to heach of the rows in the table
		
		this.itemname.setCellValueFactory(new PropertyValueFactory<itemInf, String>("NAME"));
		
		this.specs.setCellValueFactory(new PropertyValueFactory<itemInf, String>("SPECS"));

		this.price.setCellValueFactory(new PropertyValueFactory<itemInf, Integer>("PRICE"));
		
		this.categorycol.setCellValueFactory(new PropertyValueFactory<itemInf, String>("CATEGORY"));
		
		this.stock.setCellValueFactory(new PropertyValueFactory<itemInf, Integer>("STOCK"));
		
		this.idcol.setCellValueFactory(new PropertyValueFactory<itemInf, Integer>("ID"));
		
		this.inventorytable.setItems(null);
		
		this.inventorytable.setItems(this.itmInf);
		
		
	}
}
