package store;

public enum Categories {
	KEYBOARD, MOUSE, MONITOR, GPU, CPU, MOBO, RAM, CASE, COOLER, FAN;
	
	private Categories() {
		
	}
	
	public String value() {
		return name();
	}
	
	public static Categories fromvalue(String val) {
		return valueOf(val);
	}

}
