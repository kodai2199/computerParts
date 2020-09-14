package main.java.users;

import java.io.*;
import java.util.*;
import main.java.db.Connect;
import java.security.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.nio.charset.StandardCharsets;

public class User {
	private String username;
	private String password;
	private Boolean loggedin = false;
	private ArrayList<Computer> computers;
	private ResultSet UserData;
	private String salt;
	private String secretQuestion;
	private String secretAnswer;
	
	public User(String username, String password) throws SecurityException {
		this.username = username;
		try {
			/* === Login ===
			 * 1. Establish a connection with the DB
			 * 2. Get the salt for this username (if it exists)
			 * 3. Hash the password with the given salt
			 * 4. Get the user with the corresponding data
			 * If the provided username or password is wrong
			 * then a SecurityException is thrown	
			 * 5. Login complete
			 */
			Connect db = new Connect();
			String salt = db.getSalt(username);
			this.password = hash(password, salt);
			UserData = db.getUser(username, this.password);		
			this.loggedin = true;
			this.salt = salt;
			secretQuestion = UserData.getString("secretQuestion");
			secretAnswer = UserData.getString("secretAnswer");
			// Load the user's computers
			loadComputers();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Try to create a new user with the provided data. 
	 * */
	public static boolean create(String username, String password, String secretQuestion, String secretAnswer) {
		String salt = generateSalt();
		password = hash(password, salt);
		String tmp = secretAnswer.toUpperCase();
		secretAnswer = hash(tmp, salt);
		boolean r = false;
		
		try {
			Connect db = new Connect();
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
	
	//Hash code for the password cryptography.
	private static String hash(String passwordToHash, String salt) {
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
	
	/*
	 * Generate a random 32 characters long string.
	 * The string (salt) will be use to make every
	 * password look different even if they are the same.
	 * In this case, in the event of a DB leak, a lot of
	 * additional time will be required to discover and
	 * decipher all the passwords.
	 * */
	private static String generateSalt() {
		String salt = "";
		Random r=new Random();
		for(int i=0;i<32;++i) {
			char tmp=(char)('!'+r.nextInt(93));
			salt+=tmp;
		}
		return salt;
	}
	
	/*
	 * Loads the builds' list for the current user
	 * */
	public boolean loadComputers() throws ClassNotFoundException, SQLException {
		if (!loggedin) {
			return false;
		} else {
			Connect db = new Connect();
			computers = db.getComputers(this);
			return true;
		}
	}
	
	// Try to create a new build;
	public void createComputer(String name) throws IOException {														
		if(!hasComputer(name)) {
			Connect db;
			try {
				db = new Connect();
				computers.add(db.createComputer(name, this));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
		else
			throw (new IOException());
	}
	
	public void deleteComputer(int id) {
		if (!loggedin)
			return;
		if (hasComputer(id)) {
			Connect db;
			try {
				db = new Connect();
				db.removeComputer(id, this);
				loadComputers();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	// Check if the computer exists by the name and return the computer himself. If the computer doesn't exist, throws FileNotFoundException.
	public Computer getComputer(String cname) throws FileNotFoundException {										
		if(!hasComputer(cname))
			throw (new FileNotFoundException());
		else
			for(Computer c: computers) {
				if(c.getName().equals(cname)) {
					return c;
				}
			}
		return null;
	}
	
	//Check method. If the computer exists return true, else return false.
	public boolean hasComputer(String cname) {																	
		for(Computer c: computers) {
			if(c.getName().equals(cname)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean hasComputer(int id) {																	
		for(Computer c: computers) {
			if(c.getId() == id) {
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<Computer> getComputers() {
		return computers;
	}
	
	/* TODO (not working on the db)
	 * Change the name of a specific computer. If the check method return true,
	 * check if exists another computer with the new name. Throws FileNotFoundException
	 * if the file doesn't exist, throws IOException if exists another computer with
	 *  the same name
	 */
	
	/* TODO implement Username and password change
	public void changeUsername(String newUsername) {
		this.username=newUsername;
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
	*/
	
	public String getUsername() {
		return username;
	}
	
	public String getQuestion() {
		return this.secretQuestion;
	}
	
	public boolean isLoggedin() {
		return this.loggedin;
	}
}