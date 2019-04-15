package store;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import UtilityDB.ConnectDB;
import application.LoginController;
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
import javafx.scene.text.Text;

public class storeController implements Initializable {

	//these represent each of the elements of the UI when inside the store tab
	
	private int userBalance;
	
	@FXML
	private Text notifier;
	
	@FXML
	private Text balance;
	
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
	
	@FXML
	private TableColumn<itemInf, Integer> stock;
	
	@FXML
	private TableColumn<itemInf, Integer> idcol;
	
	@FXML
	private TextField idbox;
	
	@FXML
	private Button buy;
	
	private ConnectDB connect;
	
	private ObservableList<itemInf> Inf;
	
	//sql commands to connect to the items table
	private String sql = "SELECT * FROM items";
	
	public void initialize(URL url, ResourceBundle rb) {
		
		this.connect = new ConnectDB();
		
		this.category.setItems(FXCollections.observableArrayList(Categories.values()));
		
		getBalance(LoginController.getUsername());
		
		this.balance.setText(Integer.toString(userBalance) + " $");

	}
	
	private void getBalance(String n) {
		
		try {
			Connection connection = ConnectDB.getConnection();
			
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
			
			ps.setString(1, LoginController.getUsername());
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()){
				
				this.userBalance = rs.getInt(5);
	
			}
			else
				System.err.print("Balance not found");

			connection.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void setBalance(int cost) {
		
		try {
			Connection connection = ConnectDB.getConnection();
			
			PreparedStatement ps = connection.prepareStatement("UPDATE users SET balance = ? WHERE username = ?");
			
			ps.setInt(1, ());
			ps.setString(2, LoginController.getUsername());
			

			connection.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@FXML
	private void searchItem(ActionEvent event)throws SQLException  {
		
		String opt = ((Categories)this.category.getValue()).toString(); //turns the selected category into a string
		
		String searched = this.search.getText(); //holds the searched item
				
		try {
			
			Connection connection = ConnectDB.getConnection(); //creates a connection to the database
			
			this.Inf = FXCollections.observableArrayList(); 
			
			ResultSet rs = connection.createStatement().executeQuery(sql); //excecutes the sql que
			
			while(rs.next()) {
				
				if(searched.equals("")) {
				
					String storage = rs.getString(4); //stores the item category for comparison
					
					if((storage).toUpperCase().equals(opt)) {
					
						this.Inf.add(new itemInf(rs.getString(1),rs.getString(2),rs.getInt(3), storage, rs.getInt(5), rs.getInt(6))); //this sends the contents from the database to
					}
					else if(opt.equals("ALL")) {
						this.Inf.add(new itemInf(rs.getString(1),rs.getString(2),rs.getInt(3), storage, rs.getInt(5), rs.getInt(6)));  //get managed by the itemInf class
					}
				}
				else {
					
					String storageitem = rs.getString(1); //stores the itemname for comparison
					
					String storageopt = rs.getString(4); //stores the category from the item
									
					if(storageitem.toUpperCase().contains(searched.toUpperCase()) && storageopt.toUpperCase().equals(opt)) {
						
						this.Inf.add(new itemInf(storageitem, rs.getString(2),rs.getInt(3), storageopt, rs.getInt(5), rs.getInt(6)));
					}
					
					else if(storageitem.toUpperCase().contains(searched.toUpperCase()) && opt.toUpperCase().equals("ALL")) {
						
						this.Inf.add(new itemInf(storageitem, rs.getString(2),rs.getInt(3),storageopt, rs.getInt(5), rs.getInt(6)));
					}
				}
			}

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
		
		this.storetable.setItems(null);
		
		this.storetable.setItems(this.Inf);
		
	}
	@FXML
	private void buyItem(ActionEvent event) throws SQLException{
		
		int id = Integer.parseInt(idbox.getText()); //turn id box into an int
		
		try {
			Connection connection = ConnectDB.getConnection();
			
			ResultSet rs = connection.createStatement().executeQuery(sql); //SELECT * FROM items
			
			
			while(rs.next()) {
				
				int store = rs.getInt(5); //gets the stock of the item and stores it
				
				int storeid = rs.getInt(6); //gets the id of the item and stores it
				
				//compares the user entered id with 
				if( storeid == id && store > 0) {
					
					store -= 1;
						
					PreparedStatement ps = connection.prepareStatement("UPDATE items SET inventory = ? WHERE ID = ?");
						
					ps.setInt(1, store);
					ps.setInt(2, id);
						
					ps.execute();
					
					notifier.setText("Purchased Succesfully");
					
					setBalance(this.userBalance);
											
					break;

				}
				else if(storeid == id && store == 0) {
					
					notifier.setText("Out of Stock");			
				}
				
			}
			connection.close();
			
		}catch(SQLException cls) {
			System.err.print(cls);
		}
		
		
		
	}
	
}
