package main.java.app;

import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * 
 * @author ganze
 *	Puts all the scene information into a Map, and
 *	then shows the login scene.
 */
public class ComputerPartsApp extends Application {
	
	private static final String LOGIN_FXML = "fxml/login.fxml";
	private static final String HOME_FXML = "fxml/home.fxml";
	private static final String SIGNUP_FXML = "fxml/signup.fxml";
	private static final String CATEGORYLIST_FXML = "fxml/categorylist.fxml";
	
	private static Map<SceneName, FxmlData> scenes = new HashMap<>();
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void start(Stage stage) {
		scenes.put(SceneName.LOGIN, new FxmlData(LOGIN_FXML, SceneName.LOGIN, stage));
		scenes.put(SceneName.HOME, new FxmlData(HOME_FXML, SceneName.HOME, stage));
		scenes.put(SceneName.SIGNUP, new FxmlData(SIGNUP_FXML, SceneName.SIGNUP, stage));
		scenes.put(SceneName.CATEGORYLIST, new FxmlData(CATEGORYLIST_FXML, SceneName.CATEGORYLIST, stage));
		
		stage.setScene(scenes.get(SceneName.LOGIN).getScene());
		stage.setTitle("Build your computer");
		stage.show();

	}	
	
	public static Map<SceneName, FxmlData> getScenes(){
		return scenes;
	}
	
	public static void updateScenes(SceneName name, FxmlData info) {
		scenes.put(name, info);
	}
}
