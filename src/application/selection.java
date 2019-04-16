package application;

public enum selection {
	//the combobox values
	MANAGER, USER;
	
	private selection() {
		
	}
	
	public String value() {
		return name();
	}
	
	public static selection fromvalue(String val) {
		
		return valueOf(val);
	}

}
