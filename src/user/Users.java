package user;
import java.io.*;
import java.util.*;
public class Users {
	private String username;
	private String password;
	private ArrayList<Computer> build;
	
	public Users(String username, String password) {
		this.username=username;
		this.password=password;
		this.build=new ArrayList<Computer>();
	}

	public String getUsername() {
		return username;
	}
	
	public void addComputer(String cname) throws IOException {
		if(checkComputer(cname)==null) {
			Computer c=new Computer(cname);
			build.add(c);
		}	
		else
			throw (new IOException());
	}
	
	public Computer getComputer(String cname) throws FileNotFoundException {
		Computer c=checkComputer(cname);
		if(c==null)
			throw (new FileNotFoundException());
		else
			return c;
	}
	
	private Computer checkComputer(String cname) {
		for(Computer c: build) {
			if(c.getName().equals(cname)) {
				return c;
			}
		}
		return null;
	}
	
	public void changeComputerName(String currentName, String newName)throws IOException, FileNotFoundException {
		if(currentName.equals(newName))
			return;
		Computer c=checkComputer(currentName);
		if(c==null)
			throw (new FileNotFoundException());
		else {
			Computer tmp=checkComputer(newName);
			if(tmp==null) {
				int index=build.indexOf(c);
				build.get(index).rename(newName);
			}
			else
				throw(new IOException());
		}
	}
	
	
	public void changePassword(String newPassword) {
		this.password=newPassword;
	}
	
	public void changeUsername(String newUsername) {
		this.username=newUsername;
	}
}
