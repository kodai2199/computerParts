package main.java.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import main.java.app.ComputerPartsApp;
import main.java.app.FxmlData;
import main.java.app.SceneName;

public class BuildPageController extends StdMenuBarController {
	
	@FXML
	private VBox buildpage_vbox;
	
	public void initialize(){
		System.out.println("GONE");
        FxmlData fxml = ComputerPartsApp.getScenes().get(SceneName.BUILDPAGE);
		super.initalize(fxml);
	}
	
}
