package app;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import users.User;

public class GuiController {
	
    private double xOffset = 0;
    private double yOffset = 0;
	
	private Stage stage;
	
	
	// Login widgets
	@FXML
	private BorderPane login_container;
	
	@FXML
	private TextField user;
	
	@FXML
	private PasswordField pwd;
	
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
	
	public void initialize() {
        // initialization here, if needed...
    }
	
	@FXML
	public void login() {
		try {
			String user = this.user.getText();
			String pwd = this.pwd.getText();
			User u = new User(user, pwd);
			stage = (Stage)login_container.getScene().getWindow();
			VBox root = (VBox)FXMLLoader.load(getClass().getResource("Home.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("app.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
		}
		catch (SecurityException e){
			this.show(login_error_msg);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void show(Node n) {
		FadeTransition ft = new FadeTransition(Duration.millis(100), n);
		ft.setToValue(1);
		ft.play();
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
	
	public void signup() {
		try {
			String user = this.signup_user.getText();
			String pwd1 = this.signup_pwd1.getText();
			String pwd2 = this.signup_pwd2.getText();
			System.out.println(user);
		}
		catch (SecurityException e){
			// TODO
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
