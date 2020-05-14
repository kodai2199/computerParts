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
	
	public User(String username, String password, String secretQuestion, String secretAnswer) {						//Generate a new User.
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
	
	private String hash(String passwordToHash, String salt) {														//Hash code for the password cryptography.
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
	
	public String getUsername() {																					//Getter method for the username.
		return username;
	}
	
	public void addComputer(String cname) throws IOException {														//Add method for Computer. Throws IOException.
		if(checkComputer(cname)==null) {
			Computer c=new Computer(cname);
			build.add(c);
		}	
		else
			throw (new IOException());
	}
	
	public Computer getComputer(String cname) throws FileNotFoundException {										//Getter method for Computer. Check if the computer exists by the name and return the computer himself. If the computer doesn't exist, throws FileNotFoundException.
		Computer c=checkComputer(cname);
		if(c==null)
			throw (new FileNotFoundException());
		else
			return c;
	}
	
	private Computer checkComputer(String cname) {																	//Check method. If the computer exists return true, else return false.
		for(Computer c: build) {
			if(c.getName().equals(cname)) {
				return c;
			}
		}
		return null;
	}
	
	public void changeComputerName(String currentName, String newName)throws IOException, FileNotFoundException {	//Change the name of a specific computer. If the check method return true, check if exists another computer with the new name. Throws FileNotFoundException if the file doesn't exist, throws IOException if exists another computer with the same name.
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
	
	public boolean checkPassword(String password) throws SecurityException {										//Check if the insert password is correct. If it is wrong, throws SecurityException.
		if(hash(password,this.salt).equals(this.password)) 
			return true;
		else
			throw (new SecurityException());
	}
	
	public boolean changePassword(String answer, String newPassword) throws SecurityException {						//Change the password only if the answer is correct.						
		if(hash(answer.toUpperCase(),this.salt).equals(secretAnswer)) {
			this.password=hash(newPassword,this.salt);
			return true;
		}
		else
			throw (new SecurityException());
	}
	
	public String getQuestion() {																					//Getter method for secretQuestion.
		return this.secretQuestion;
	}
	
	public void changeUsername(String newUsername) {																//Change the username of the user.
		this.username=newUsername;
	}
}