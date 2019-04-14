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
	
	public userInf(String username, String firstname, String lastname, Integer balance)
	{

		this.setUSERNAME(username);
		
		this.setFIRSTNAME(firstname);
		
		this.setLASTNAME(lastname);
		
		this.setBALANCE(balance);

		
	}


	public String getUSERNAME() {
		return USERNAME.get();
	}
	
	public void setUSERNAME(String uSERNAME) {
		USERNAME.set(uSERNAME);
	}
/////////////////////////////////////////
	public String getFIRSTNAME() {
		return FIRSTNAME.get();
	}
	

	public void setFIRSTNAME(String fIRSTNAME) {
		FIRSTNAME.set(fIRSTNAME);
	}
/////////////////////////////////////////
	public String getLASTNAME() {
		return LASTNAME.get();
	}

	public void setLASTNAME(String lASTNAME) {
		LASTNAME.set(lASTNAME);
	}

////////////////////////////////////////////////
	public Integer getBALANCE() {
		return BALANCE.get();
	}

	public void setBALANCE(Integer bALANCE) {
		BALANCE.set(bALANCE);
	}





}
