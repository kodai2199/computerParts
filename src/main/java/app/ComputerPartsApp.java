package main.java.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.java.component.*;
import main.java.db.Connect;
import main.java.users.User;

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
	private static User user;
	private static Map<String, Image> brand_logos = new HashMap<>();
	private static Map<SceneName, FxmlData> scenes = new HashMap<>();
	private static ArrayList<CPU> cpus;
	private static ArrayList<Graphic_Cards> gpus;
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void start(Stage stage) {
		loadComponentsLists();
		//loadBrandsLogos();
		
		scenes.put(SceneName.LOGIN, new FxmlData(LOGIN_FXML, SceneName.LOGIN, stage));
		scenes.put(SceneName.HOME, new FxmlData(HOME_FXML, SceneName.HOME, stage));
		scenes.put(SceneName.SIGNUP, new FxmlData(SIGNUP_FXML, SceneName.SIGNUP, stage));
		scenes.put(SceneName.CATEGORYLIST, new FxmlData(CATEGORYLIST_FXML, SceneName.CATEGORYLIST, stage));
		
		stage.setScene(scenes.get(SceneName.LOGIN).getScene());
		stage.setTitle("Build your computer");
		stage.show();

	}	
	
	private void loadComponentsLists() {
		// Preload all Components lists to make the software overall more responsive
		try {
			Connect db = new Connect();
			cpus = db.loadCPUs();
		} catch (Exception e) {
			System.out.println("There was an error while loading the components' lists.");
		}
	}
	
	private void loadBrandLogos() {
		
	}
	
	public static Map<SceneName, FxmlData> getScenes(){
		return scenes;
	}
	
	public static void updateScenes(SceneName name, FxmlData info) {
		scenes.put(name, info);
	}
	
	public static User getUser() {
		return user;
	}
	
	public static void setUser(User u) {
		user = u;
	}
	
	public static ArrayList<CPU> getCPUs() {
		return cpus;
	}
}
