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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

public class storeController implements Initializable {
	

	//these represent each of the elements of the UI when inside the store tab
	
	private int userBalance; //stores the balance of the user
	
	private int userID;
	
	@FXML
	private Text notifier; //text under the ID box notifying the user of the purchase
	@FXML
	private Text balance; //shows the user his balance
	@FXML
	private Button searchbutton; 
	@FXML
	private TextField search; //field user enters to search
	@FXML
	private ComboBox<Categories> category; //dropbox with item categories
	@FXML
	private TableView<itemInf> storetable; //the table of contents
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
	private TextField idbox; //where user enters ID of purchase

	
	private ObservableList<itemInf> Inf;
	
		
	public void initialize(URL url, ResourceBundle rb) {
				
		this.category.setItems(FXCollections.observableArrayList(Categories.values()));//adds the data to the selection dropbox from the Categories.java enum
		
		getBalance();//gets the balance of the user and displays it
		
		getUserID();
		
		try {
			getUserInfo();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}
	private void getUserID() {
		
		try {
			Connection connection = ConnectDB.getConnection();
			
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE username = ?"); //selection statement
			
			ps.setString(1, LoginController.getUsername()); //sets = ?
			
			ResultSet rs = ps.executeQuery();
	
			this.userID = rs.getInt(7); //gets the ID from the user table
			
			ps.close();
			rs.close();
			connection.close();
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		
	}
	
	private void getBalance() {
		
		try {
			Connection connection = ConnectDB.getConnection();
			
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE username = ?"); //selection statement
			
			ps.setString(1, LoginController.getUsername()); //sets = ?
			
			ResultSet rs = ps.executeQuery();
	
			this.userBalance = rs.getInt(5); //gets the balance from the users database
			
			this.balance.setText(Integer.toString(userBalance) + " $"); //displays on the balance textbox (IU)
			
			
			ps.close();
			rs.close();
			connection.close();
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		
	}
	
	private void setBalance(int cost) throws SQLException{
		
		try {
			
			int reduction = this.userBalance - cost; //calculates the new user balance
			
			Connection connection = ConnectDB.getConnection();
			
			PreparedStatement ps = connection.prepareStatement("UPDATE users SET balance = ? WHERE username = ?"); //uptade statement for the balance
			
			
			ps.setInt(1, reduction);//adds the new balance value to the users database
			ps.setString(2, LoginController.getUsername());
			
			ps.execute();
			
			ps.close();
			connection.close();
			
		} catch (SQLException e) {
		
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
			
			ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM items"); 
			
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
		
		this.storetable.setItems(null);
		
		this.storetable.setItems(this.Inf);
		
	}
	@FXML
	private void buyItem(ActionEvent event) throws SQLException{
		
		int id = Integer.parseInt(idbox.getText()); //turn id box into an int
		
		try {
			Connection connection = ConnectDB.getConnection();
			
			PreparedStatement ps1 = connection.prepareStatement("SELECT * FROM items where ID = ?"); //searches for the item in the database via its id
			
			//add the user entered id
			ps1.setInt(1, id);
			
			ResultSet rs = ps1.executeQuery(); //SELECT * FROM items
			
				if(!rs.next()) {
					notifier.setText("Invalid ID");
					return;
				}
			
				int storestock = rs.getInt(5); //gets the stock of the item and stores it
				int pricers = rs.getInt(3); ///gets item price
				ps1.close();

				
				//checks if theres stock
				if(storestock > 0 && pricers <= this.userBalance) {
					
					storestock -= 1;
						
					PreparedStatement ps = connection.prepareStatement("UPDATE items SET inventory = ? WHERE ID = ?");
						
					
					ps.setInt(1, storestock);
					ps.setInt(2, id);
						
					ps.execute();
					
					//
					
					PreparedStatement psVer = connection.prepareStatement("SELECT * FROM purchased WHERE userid = ? AND itemid = ?"); //Checks if a row with the same user id and same item ID already exists
					
					psVer.setInt(1, this.userID);
					psVer.setInt(2, id);
					
					ResultSet rsVer = psVer.executeQuery();
					
					//if the row already exists in the purchased table, it will only modify it.
					
					if(rsVer.next()) {
						
						int quantity = rsVer.getInt(3);
						
						quantity +=1;
						
						PreparedStatement ps2 = connection.prepareStatement("UPDATE purchased SET quantity = ? WHERE userid = ? AND itemid = ?"); //insert the item bought into the purchased database
						
						ps2.setInt(1, quantity);		
						ps2.setInt(2, this.userID);
						ps2.setInt(3, id);
						
						ps2.execute();
						
						ps2.close();
						rsVer.close();
						
						
					}
					
					//if the row does not exist in the purchased table, it will insert a new row with its contents
					else {
						PreparedStatement ps2 = connection.prepareStatement("INSERT INTO purchased(userid,itemid,quantity) VALUES(?,?,?)"); //insert the item bought into the purchased database
								
						ps2.setInt(1, this.userID);		
						ps2.setInt(2, id);
						ps2.setInt(3, 1);
						
						ps2.execute();
						
						ps2.close();
						rsVer.close();
					}
					
					//
					ps1.close();
					ps.close();
					rs.close();
					connection.close();
					
					
					setBalance(pricers);
					getBalance();
					
					notifier.setText("Purchased Succesfully");

				}
				else if(storestock == 0) {
					
					notifier.setText("Out of Stock");			
				}
				else if(pricers > this.userBalance) {
					notifier.setText("Not enough cash");	
				}

				
				ps1.close();
				rs.close();
				connection.close();
			}
			
			catch(SQLException cls) {
			System.err.print(cls);
		}
				
	}
	
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
	@FXML
	private TextField newusername;
	@FXML
	private Label successfullchange;
	@FXML
	private PasswordField pass1;
	@FXML
	private PasswordField pass2;
	@FXML
	private TextField newfirstname;
	@FXML
	private TextField newlastname;
	@FXML
	private TextField addbalance;
	@FXML
	private TextField newaddress;

	
	public void getUserInfo() throws SQLException{
		
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
	
	@FXML
	private void changeUsername(ActionEvent event)throws SQLException {
		
		try {
			Connection connection = ConnectDB.getConnection();
			
			PreparedStatement ps = connection.prepareStatement("UPDATE users SET username = ? WHERE username = ?"); //selection statement
			
			ps.setString(1, newusername.getText());
			
			ps.setString(2, LoginController.getUsername()); //sets = ?
			
			ps.execute();
			
			successfullchange.setText("Successfully Changed Username!");
				
			LoginController.setUsername(newusername.getText());
				
			ps.close();
			connection.close();
			
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		
	}
	@FXML
	private void changePassword(ActionEvent event) throws SQLException {
	
		if(pass1.getText().equals(pass2.getText())) {
		
				try {
					Connection connection = ConnectDB.getConnection();
					
					PreparedStatement ps = connection.prepareStatement("UPDATE users SET password = ? WHERE username = ?"); //selection statement
					
					ps.setString(1, pass1.getText());
					
					ps.setString(2, LoginController.getUsername()); //sets = ?
					
					ps.execute();
					
					successfullchange.setText("Successfully Changed Password!");
												
					ps.close();
					connection.close();
					
					
				} catch (SQLException e) {
				
					e.printStackTrace();
				}
			}
		else
		{
			successfullchange.setText("Passwords do not match");
		}
	}
	
	@FXML
	private void changeFirstname(ActionEvent event) throws SQLException{
		
		try {
			Connection connection = ConnectDB.getConnection();
			
			PreparedStatement ps = connection.prepareStatement("UPDATE users SET firstname = ? WHERE username = ?"); //selection statement
			
			ps.setString(1, newfirstname.getText());
			
			ps.setString(2, LoginController.getUsername()); //sets = ?
			
			ps.execute();
			
			successfullchange.setText("Successfully Changed Firstname!");
				
			ps.close();
			connection.close();
			
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		
	}
	
	@FXML
	private void changeLastName(ActionEvent event)throws SQLException {
		
		try {
			Connection connection = ConnectDB.getConnection();
			
			PreparedStatement ps = connection.prepareStatement("UPDATE users SET lastname = ? WHERE username = ?"); //selection statement
			
			ps.setString(1, newlastname.getText());
			
			ps.setString(2, LoginController.getUsername()); //sets = ?
			
			ps.execute();
			
			successfullchange.setText("Successfully Changed Lastname!");
				
			ps.close();
			connection.close();
			
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		
	}
	
	@FXML
	private void addMoreBalance(ActionEvent event)throws SQLException{
		getBalance();
		
		int newBal = (Integer.parseInt(addbalance.getText()) + this.userBalance);
		
		try {
			Connection connection = ConnectDB.getConnection();
			
			PreparedStatement ps = connection.prepareStatement("UPDATE users SET balance = ? WHERE username = ?"); //selection statement
			
			ps.setInt(1, newBal);
			
			ps.setString(2, LoginController.getUsername()); //sets = ?
			
			ps.execute();
			
			successfullchange.setText("Successfully Added Curency!");
			
				
			ps.close();
			connection.close();
			
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		getBalance();

		
	}
	
	@FXML
	private void changeAdrress(ActionEvent event)throws SQLException{
		
		try {
			Connection connection = ConnectDB.getConnection();
			
			PreparedStatement ps = connection.prepareStatement("UPDATE users SET address = ? WHERE username = ?"); //selection statement
			
			ps.setString(1, newaddress.getText());
			
			ps.setString(2, LoginController.getUsername()); //sets = ?
			
			ps.execute();
			
			successfullchange.setText("Successfully Changed Address!");
				
			ps.close();
			connection.close();
			
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		
	}
	
	@FXML
	private void refresh(ActionEvent event){
			try {
				getUserInfo();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
	}
	
	
	@FXML
	private TableView<PurchasedInf> purchasedtable;
	@FXML
	private TableColumn<PurchasedInf, String> purchaseditemname;
	@FXML
	private TableColumn<PurchasedInf, String> purchaseditemdescription;
	@FXML
	private TableColumn<PurchasedInf, Integer> purchaseditemprice;
	@FXML
	private TableColumn<PurchasedInf, Integer> purchaseditemquantity;
	
	private ObservableList<PurchasedInf> pur;

	@FXML
	private void refreshPurchased(ActionEvent event) throws SQLException{
		
		try {
			Connection connection = ConnectDB.getConnection();
			
			pur = FXCollections.observableArrayList();
			
			ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM purchased");
			
			while(rs.next()) {
				
				int savedUserID = rs.getInt(1);
				int itemID = rs.getInt(2);
				
				
				ResultSet rs2 = connection.createStatement().executeQuery("SELECT * FROM items");
				
				
				while(rs2.next()) {
					
					if(rs2.getInt(6) == itemID && savedUserID == this.userID) {
						
						this.pur.add(new PurchasedInf(rs2.getString(1), rs2.getString(2), rs2.getInt(3), rs.getInt(3)));
					
					}
					
				}
				
				rs2.close();
			}
			
			connection.close();
			
			rs.close();
			
		}catch(SQLException e) {
			System.err.println("error: "+e);
		}
		this.purchaseditemname.setCellValueFactory(new PropertyValueFactory<PurchasedInf, String>("NAME"));
		this.purchaseditemdescription.setCellValueFactory(new PropertyValueFactory<PurchasedInf, String>("SPECS"));
		this.purchaseditemprice.setCellValueFactory(new PropertyValueFactory<PurchasedInf, Integer>("PRICE"));
		this.purchaseditemquantity.setCellValueFactory(new PropertyValueFactory<PurchasedInf, Integer>("QUANTITY"));
		
		this.purchasedtable.setItems(null);
		
		this.purchasedtable.setItems(this.pur);

		
	}
	
	
}

