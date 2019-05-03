package manager;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class userInf {

	private StringProperty USERNAME = new SimpleStringProperty();
	
	private StringProperty FIRSTNAME = new SimpleStringProperty();
	
	private StringProperty LASTNAME = new SimpleStringProperty();
	
	private IntegerProperty BALANCE = new SimpleIntegerProperty();
	
	private StringProperty ADDRESS = new SimpleStringProperty();

	
	public userInf(String username, String firstname, String lastname, Integer balance, String address)
	{

		this.setUSERNAME(username);
		
		this.setFIRSTNAME(firstname);
		
		this.setLASTNAME(lastname);
		
		this.setBALANCE(balance);
		
		this.setADDRESS(address);

	}

	public String getADDRESS() {
		return ADDRESS.get();
	}
	
	public void setADDRESS(String address) {
		ADDRESS.set(address);
	}
	
	public String getUSERNAME() {
		return USERNAME.get();
	}
	
	public void setUSERNAME(String username) {
		USERNAME.set(username);
	}
/////////////////////////////////////////
	public String getFIRSTNAME() {
		return FIRSTNAME.get();
	}
	

	public void setFIRSTNAME(String firstname) {
		FIRSTNAME.set(firstname);
	}

	public String getLASTNAME() {
		return LASTNAME.get();
	}

	public void setLASTNAME(String lastname) {
		LASTNAME.set(lastname);
	}

////////////////////////////////////////////////
	public Integer getBALANCE() {
		return BALANCE.get();
	}

	public void setBALANCE(Integer bALANCE) {
		BALANCE.set(bALANCE);
	}





}
