package manager;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class userInf {

	private final StringProperty USERNAME;
	
	public userInf(String username)
	{
		this.USERNAME = new SimpleStringProperty(username);
		
	}

	public StringProperty getUSERNAME() {
		return USERNAME;
	}
	
}
