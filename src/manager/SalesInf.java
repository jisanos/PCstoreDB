package manager;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SalesInf {
	
	private StringProperty USERNAME = new SimpleStringProperty(); //username for the user that bought the item
	
	private StringProperty NAME = new SimpleStringProperty(); //name of the item itself
	
	private IntegerProperty PRICE= new SimpleIntegerProperty();
	
	private IntegerProperty QUANTITY = new SimpleIntegerProperty();

	public SalesInf(String username, String name, int price, int quantity) {
		
		this.setUSERNAME(username);
		
		this.setNAME(name);
		
		this.setPRICE(price);
		
		this.setQUANTITY(quantity);
				
	}
	
	public void setUSERNAME(String username) {
		USERNAME.set(username);
	}
	
	public String getUSERNAME() {
		return USERNAME.get();
	}

	public void setNAME(String name) {
		NAME.set(name);;
	}
	
	public String getNAME() {
		return NAME.get();
	}
	
	public void setPRICE(int price) {
		PRICE.set(price);
	}
	
	public int getPRICE() {
		return PRICE.get();
	}
	
	public void setQUANTITY(int quantity) {
		QUANTITY.set(quantity);
	}
	
	public int getQUANTITY() {
		return QUANTITY.get();
	}
	
		
}
