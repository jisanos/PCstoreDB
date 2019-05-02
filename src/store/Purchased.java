package store;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Purchased {

	private StringProperty NAME = new SimpleStringProperty();
	
	private StringProperty SPECS = new SimpleStringProperty();
	
	private IntegerProperty PRICE= new SimpleIntegerProperty();
	
	private IntegerProperty QUANTITY = new SimpleIntegerProperty();
	
	public Purchased(String name, String specs, int price, int quantity)
	{
		this.setNAME(name);
		
		this.setSPECS(specs);
		
		this.setPRICE(price);
		
		this.setQUANTITY(quantity);
		
		
	}
////////////////////////////////////////	
	public String getNAME() {
		return NAME.get();
	}

	public void setNAME(String nAME) {
		NAME.set(nAME);
	}
//////////////////////////////////////////
	public String getSPECS() {
		return SPECS.get();
	}

	public void setSPECS(String sPECS) {
		SPECS.set(sPECS);
	}
/////////////////////////////////
	public int getPRICE() {
		return PRICE.get();
	}
	public void setPRICE(int pRICE) {
		PRICE.set(pRICE);
	}
///////////////////////////////
	public int getQUANTITY() {
		return QUANTITY.get();
	}
	public void setQUANTITY(int qUANTITY)
	{
		QUANTITY.set(qUANTITY);
	}
	
}
