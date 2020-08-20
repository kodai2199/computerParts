package main.java.app;

public class PasswordValidator {
	
	String pwd;
	
	public PasswordValidator(String pwd) {
		this.pwd = pwd;
	}
	
	public boolean validate() {
		boolean strong = true;
			if (this.pwd.length() < 8) {
				strong = false;
			}	
		return strong;
	}
	
	// The class should implement a way to calculate the strength of the password and provide
	// feedback about it (does it satisfy the requirements, etc...)
}
