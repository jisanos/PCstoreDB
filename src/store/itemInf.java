package store;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class itemInf {
	
	//all of the contends of the store table
	
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

	public void setNAME(String name) {
		NAME.set(name);
	}

	public String getSPECS() {
		return SPECS.get();
	}

	public void setSPECS(String specs) {
		SPECS.set(specs);
	}

	public Integer getPRICE() {
		return PRICE.get();
	}

	public void setPRICE(Integer pRICE) {
		PRICE.set(pRICE);
	}

	public String getCATEGORY() {
		return CATEGORY.get();
	}

	public void setCATEGORY(String category) {
		CATEGORY.set(category);
	}

	public Integer getSTOCK() {
		return STOCK.get();
	}

	public void setSTOCK(Integer stock) {
		STOCK.set(stock);
	}


	public Integer getID() {
		return ID.get();
	}

	public void setID(Integer id) {
		ID.set(id);
	}

}
