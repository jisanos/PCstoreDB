package store;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class itemInf {
	
	private StringProperty NAME = new SimpleStringProperty();
	
	private StringProperty SPECS = new SimpleStringProperty();
	
	private IntegerProperty PRICE= new SimpleIntegerProperty();
	
	private StringProperty CATEGORY= new SimpleStringProperty();
	
	private IntegerProperty STOCK = new SimpleIntegerProperty();
	
	private IntegerProperty ID = new SimpleIntegerProperty();
	
	public itemInf(String name, String specs, Integer price, String category, Integer stock, Integer id) {
		
		this.setNAME(name);
		
		this.setSPECS(specs);
		
		this.setPRICE(price);
		
		this.setCATEGORY(category);
		
		this.setSTOCK(stock);
		
		this.setID(id);
		
	}

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
///////////////////////////////////////
	public Integer getPRICE() {
		return PRICE.get();
	}

	public void setPRICE(Integer pRICE) {
		PRICE.set(pRICE);
	}
/////////////////////////////////////
	public String getCATEGORY() {
		return CATEGORY.get();
	}

	public void setCATEGORY(String cATEGORY) {
		CATEGORY.set(cATEGORY);
	}
////////////////////////////////////////
	public Integer getSTOCK() {
		return STOCK.get();
	}

	public void setSTOCK(Integer sTOCK) {
		STOCK.set(sTOCK);
	}
	////////////////////////////////////////

	public Integer getID() {
		return ID.get();
	}

	public void setID(Integer iD) {
		ID.set(iD);
	}

}
