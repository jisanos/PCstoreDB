package application;

public enum selection {
	
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
