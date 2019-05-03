package store;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PurchasedInf {

	private StringProperty NAME = new SimpleStringProperty();
	
	private StringProperty SPECS = new SimpleStringProperty();
	
	private IntegerProperty PRICE= new SimpleIntegerProperty();
	
	private IntegerProperty QUANTITY = new SimpleIntegerProperty();
	
	public PurchasedInf(String name, String specs, int price, int quantity)
	{
		this.setNAME(name);
		
		this.setSPECS(specs);
		
		this.setPRICE(price);
		
		this.setQUANTITY(quantity);
		
		
	}
	
	public String getNAME() {
		return NAME.get();
	}

	public void setNAME(String name) {
		NAME.set(name);
	}

	public String getSPECS() {
		return SPECS.get();
	}

	public void setSPECS(String specs) {
		SPECS.set(specs);
	}

	public int getPRICE() {
		return PRICE.get();
	}
	public void setPRICE(int price) {
		PRICE.set(price);
	}

	public int getQUANTITY() {
		return QUANTITY.get();
	}
	public void setQUANTITY(int quantity)
	{
		QUANTITY.set(quantity);
	}
	
}
