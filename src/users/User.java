package users;
import java.io.*;
import java.util.*;
import java.security.*;
import java.nio.charset.StandardCharsets;
public class User {
	private String username;
	private String password;
	private ArrayList<Computer> build;
	private String salt;
	private String secretQuestion;
	private String secretAnswer;
	
	public User(String username, String password, String secretQuestion, String secretAnswer) {
		this.username=username;
		this.build=new ArrayList<Computer>();
		Random r=new Random();
		this.salt="";
		for(int i=0;i<32;++i) {
			char tmp=(char)('!'+r.nextInt(93));
			this.salt+=tmp;
		}
		this.password=hash(password,this.salt);
		this.secretQuestion=secretQuestion;
		String tmp=secretAnswer.toUpperCase();
		this.secretAnswer=hash(tmp, salt);		
	}
	
	private String hash(String passwordToHash, String salt) {
		String generatedPassword = null;
	    try {
	        MessageDigest md = MessageDigest.getInstance("SHA-512");
	        md.update(salt.getBytes(StandardCharsets.UTF_8));
	        byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
	        StringBuilder sb = new StringBuilder();
	        for(int i=0; i< bytes.length ;i++){
	            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	        }
	        generatedPassword = sb.toString();
	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    }
	    return generatedPassword;
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
	
	public boolean checkPassword(String password) throws SecurityException {
		if(hash(password,this.salt).equals(this.password)) 
			return true;
		else
			throw (new SecurityException());
	}
	
	public boolean changePassword(String answer, String newPassword) throws SecurityException {
		if(hash(answer.toUpperCase(),this.salt).equals(secretAnswer)) {
			this.password=hash(newPassword,this.salt);
			return true;
		}
		else
			throw (new SecurityException());
	}
	
	public void changeUsername(String newUsername) {
		this.username=newUsername;
	}
}