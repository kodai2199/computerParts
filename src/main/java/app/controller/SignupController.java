package main.java.app.controller;

import java.io.FileNotFoundException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import main.java.app.ComputerPartsApp;
import main.java.app.PasswordValidator;
import main.java.app.SceneName;
import main.java.users.User;

public class SignupController extends GenericController {
	
	// Signup widgets
	@FXML
	private BorderPane signup_container;
	@FXML
	private TextField signup_user;
	@FXML
	private PasswordField signup_pwd1;
	@FXML
	private PasswordField signup_pwd2;
	@FXML
	private TextField signup_question;
	@FXML
	private TextField signup_answer;
	@FXML
	private Button signup_btn;
	@FXML
	private Label signup_msg;
	
	@FXML
	public void showLogin() {
		stage.setScene(ComputerPartsApp.getScenes().get(SceneName.LOGIN).getScene());
	}
	
	@FXML
	public void signup() {
		try {
			boolean error = false;
			this.signup_btn.setDisable(true);
			String user = this.signup_user.getText();
			String pwd1 = this.signup_pwd1.getText();
			String pwd2 = this.signup_pwd2.getText();
			String question = this.signup_question.getText();
			String answer = this.signup_answer.getText();
			if (!pwd1.equals(pwd2)){
				this.signup_msg.setText("The two passwords are different");
				this.signup_msg.setOpacity(1);
				error = true;
			}
			if (user.length() > 64 || pwd1.length() > 64) {
				this.signup_msg.setText("Password and username should be shorter than 64 characters");
				this.signup_msg.setOpacity(1);
				error = true;
			}
			PasswordValidator pv = new PasswordValidator(pwd1);
			if (!pv.validate()) {
				this.signup_msg.setText("Your password isn't strong enough");
				this.signup_msg.setOpacity(1);
				error = true;
				// TODO the button should become clickable again only after the password has been modified
			}
			if (!error) {
				if(User.create(user, pwd1, question, answer)) {
					this.signup_msg.setStyle("-fx-text-fill: green");
					this.signup_msg.setText("User created successfully");
					this.signup_msg.setOpacity(1);
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Signup successful");
					alert.setHeaderText("User "+user+" created");
					alert.setContentText("Congratulations, you can now start using our application!");
					alert.showAndWait();
					this.showLogin();
				} else {
					this.signup_msg.setText("Error while creating user: try with a different username");
					this.signup_msg.setOpacity(1);
				}
			} else {
				
			}
		}
		catch (SecurityException e){
			// TODO
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			this.signup_btn.setDisable(false);
		}
	}
}
