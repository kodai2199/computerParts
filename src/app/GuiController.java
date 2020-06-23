package app;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GuiController {
	
    private double xOffset = 0;
    private double yOffset = 0;
	
	private Stage stage;
	
	@FXML
	private BorderPane login_container;
	
	@FXML
	private TextField user;
	
	@FXML
	private PasswordField pwd;
	
	public void initialize() {
        // initialization here, if needed...
    }
	
	@FXML
	public void login() {
		
		
		try {
			String user = this.user.getText();
			String pwd = this.pwd.getText();
			if (pwd.equals("pentagono")) {
				stage = (Stage)login_container.getScene().getWindow();
				VBox root = (VBox)FXMLLoader.load(getClass().getResource("Home.fxml"));
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("app.css").toExternalForm());
				stage.setScene(scene);
				System.out.println("Login effettuato.");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
