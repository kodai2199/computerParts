package main.java.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import main.java.app.ComputerPartsApp;
import main.java.app.FxmlData;
import main.java.app.SceneName;

/*
 * This Controller class will take care of the standard menu bar
 * at the top of most stages (Home, CategoryList, ...);
 * It will handle the Back button, the Build button 
 */
public class StdMenuBarController extends GenericController {
	
	@FXML HBox menu;
	@FXML Button back;
	@FXML Button profile;
	@FXML Button savedbuilds;
	@FXML Button newbuild;
	
	SceneName lastSceneName;
	SceneName sceneName;
	
	public void initalize(FxmlData fxml) {
		// Since every scene has its own FxmlData object, it must be provided
		lastSceneName = fxml.getLastSceneName();
		sceneName = fxml.getSceneName();
		
		// Only show the back button if a "lastScene" is set
		if (lastSceneName != null) {
			back.setVisible(true);
		} else {
			back.setVisible(false);
		}
		
		if (this instanceof BuildListController) {
			menu.getChildren().remove(savedbuilds);
		}
	}
	
	public void goBack() {
        stage.setScene(ComputerPartsApp.getScenes().get(lastSceneName).getScene());
	}
	
	public void openBuildList() {
        FxmlData fxml = ComputerPartsApp.getScenes().get(SceneName.BUILDLIST);
        fxml.setLastSceneName(sceneName);
        stage.setScene(ComputerPartsApp.getScenes().get(SceneName.BUILDLIST).getScene());
	}
	
	public void openBuildPage() {
		FxmlData fxml = ComputerPartsApp.getScenes().get(SceneName.BUILDPAGE);
        fxml.setLastSceneName(sceneName);
        stage.setScene(ComputerPartsApp.getScenes().get(SceneName.BUILDPAGE).getScene());
	}
	
	public void openHome() {
		FxmlData fxml = ComputerPartsApp.getScenes().get(SceneName.HOME);
        fxml.setLastSceneName(sceneName);
        stage.setScene(ComputerPartsApp.getScenes().get(SceneName.HOME).getScene());
	}

}
