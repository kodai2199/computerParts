package main.java.app.controller;

import main.java.app.ComputerPartsApp;
import main.java.app.SceneName;

import java.io.FileNotFoundException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import main.java.users.User;

public class LoginController extends GenericController {
	User user;
	
	// Login widgets
	@FXML
	private BorderPane login_container;
	@FXML
	private TextField login_user;
	@FXML
	private PasswordField login_pwd;
	@FXML
	private Label login_error_msg;
	
	public void initialize(){
		System.out.println("Initialized LoginController");
	}
	
	@FXML
	public void showSignup() {
		stage.setScene(ComputerPartsApp.getScenes().get(SceneName.SIGNUP).getScene());
	}
	
	@FXML
	public void login() {
		try {
			String user = this.login_user.getText();
			String pwd = this.login_pwd.getText();
			this.user = new User(user, pwd);
			stage.setScene(ComputerPartsApp.getScenes().get(SceneName.HOME).getScene());
			System.out.println("Login successful");
		}
		catch (SecurityException e){
			show(login_error_msg);
			System.out.println("Login attempt failed");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
