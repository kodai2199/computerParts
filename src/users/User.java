package main.java.users;

import java.io.*;
import java.util.*;
import main.java.db.Connect;
import java.security.*;
import java.sql.SQLException;
import java.nio.charset.StandardCharsets;


public class User {
	private String username;
	private String password;
	private Boolean loggedin = false;
	private ArrayList<Computer> build;
	private String salt;
	private String secretQuestion;
	private String secretAnswer;
	
	public User(String username, String password) throws SecurityException {
		this.username = username;
		
		// Login
		// 1. Establish a connection with the DB
		// 2. Get the salt for this username (if it exists)
		// 3. Hash the password with the given salt
		// 4. Check if a user with that hashed password and username exists
		// Login complete
		
		try {
			Connect db = new Connect();
			String salt = db.getSalt(username);
			this.password = hash(password, salt);
			if (!db.hasUser(username, this.password) || salt.equals("Salt not found")) {
				throw(new SecurityException());
			}
			this.loggedin = true;
			this.salt = salt;
			this.build=new ArrayList<Computer>();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean create(String username, String password, String secretQuestion, String secretAnswer) {
		//Generate a new User
		String salt = generateSalt();
		password = hash(password, salt);
		String tmp = secretAnswer.toUpperCase();
		secretAnswer = hash(tmp, salt);
		boolean r = false;
		
		try {
			// 	Try to insert into the database
			Connect db = new Connect();
			/* Just for logging and debugging purposes
			System.out.println("AFTER HASH");
			System.out.println("Nome utente: "+username);
			System.out.println("Password: "+password);
			System.out.println("Salt: "+salt);
			System.out.println("Domanda segreta: "+secretQuestion);
			System.out.println("Risposta: "+secretAnswer);
			*/ 
			r = db.insertUser(username, password, salt, secretQuestion, secretAnswer);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return r;
	}
	
	private static String hash(String passwordToHash, String salt) {														//Hash code for the password cryptography.
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
	
	private static String generateSalt() {
		String salt = "";
		Random r=new Random();
		for(int i=0;i<32;++i) {
			char tmp=(char)('!'+r.nextInt(93));
			salt+=tmp;
		}
		return salt;
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
	
	public boolean isLoggedin() {
		return this.loggedin;
	}
}