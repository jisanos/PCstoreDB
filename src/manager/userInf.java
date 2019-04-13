package manager;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class userInf {

	private final StringProperty USERNAME;
	
	private final StringProperty FIRSTNAME;
	
	private final StringProperty LASTNAME;
	
	private final IntegerProperty BALANCE;
	
	public userInf(String username, String firstname, String lastname, Integer balance)
	{
		
		
		this.USERNAME = new SimpleStringProperty(username);
		
		this.FIRSTNAME = new SimpleStringProperty(firstname);
		
		this.LASTNAME = new SimpleStringProperty(lastname);
		
		this.BALANCE = new SimpleIntegerProperty(balance);
		
		System.out.print(getUSERNAME());
		
	}

	public StringProperty getUSERNAME() {
		return USERNAME;
	}

	public StringProperty getFIRSTNAME() {
		return FIRSTNAME;
	}
	
	public StringProperty getLASTNAME() {
		return LASTNAME;
	}

	public IntegerProperty getBALANCE() {
		return BALANCE;
	}




}
