package app;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import users.User;

public class GuiController {
	
    //private double xOffset = 0;
    //private double yOffset = 0;
	private Stage stage;
	
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
	
	public void initialize() {
        // initialization here, if needed...
    }
	
	private void show(Node n) {
		FadeTransition ft = new FadeTransition(Duration.millis(100), n);
		ft.setFromValue(1);
		ft.setToValue(1);
		ft.play();
	}
	
	public void showLoginPane() {
		try {
			stage = (Stage)signup_container.getScene().getWindow();
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Login.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("app.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void showSignupPane() {
		try {
			stage = (Stage)login_container.getScene().getWindow();
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Signup.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("app.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@FXML
	public void login() {
		try {
			String user = this.login_user.getText();
			String pwd = this.login_pwd.getText();
			this.user = new User(user, pwd);
			stage = (Stage)login_container.getScene().getWindow();
			VBox root = (VBox)FXMLLoader.load(getClass().getResource("Home.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("app.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
			System.out.println("Login successful");
		}
		catch (SecurityException e){
			this.show(login_error_msg);
			System.out.println("Login attempt failed");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
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
					this.showLoginPane();
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
