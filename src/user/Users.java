package user;
import java.util.*;
public class Users {
	private String name;
	private String password;
	private ArrayList<String> build;
	
	public Users(String name, String password) {
		this.name=name;
		this.password=password;
		this.build=new ArrayList<String>();
	}

	public String getName() {
		return name;
	}

	public ArrayList<String> getBuild() {
		return build;
	}
	
	public void changePassword(String newPassword) {
		this.password=newPassword;
	}
	
	public void changeName(String newName) {
		this.name=newName;
	}
	
	
	
}
