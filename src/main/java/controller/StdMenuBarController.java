package main.java.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import main.java.app.ComputerPartsApp;
import main.java.app.FxmlData;
import main.java.app.SceneName;

/*
 * This Controller class will take care of the standard menu bar
 * at the top of most stages (Home, CategoryList, ...);
 * It will handle the Back button, the Build button 
 */
public class StdMenuBarController extends GenericController {
	
	@FXML Button back;
	@FXML Button profile;
	@FXML Button savedbuilds;
	@FXML Button newbuild;
	
	SceneName lastSceneName;
	
	public void initalize(FxmlData fxml) {
		// Since every scene has its own FxmlData object, it must be provided
		lastSceneName = fxml.getLastSceneName();
		
		// Only show the back button if a "lastScene" is set
		if (lastSceneName != null) {
			back.setVisible(true);
		} else {
			back.setVisible(false);
		}
	}
	
	public void goBack() {
        stage.setScene(ComputerPartsApp.getScenes().get(lastSceneName).getScene());
	}

}
