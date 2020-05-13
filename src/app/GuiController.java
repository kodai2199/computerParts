package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class GuiController {
	
	@FXML
	private TextField user;
	
	@FXML
	private PasswordField pwd;
	
	public void initialize() {
        // initialization here, if needed...
    }
	
	@FXML
	public void login() {
		String user = this.user.getText();
		String pwd = this.pwd.getText();
		System.out.println("Fa schifo, lo so, ma "+user+" ha provato a fare il login con la password "+pwd);
	}
}
