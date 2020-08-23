package main.java.app;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 
 * @author ganze
 *	Contains information for FXML files.
 */
public class FxmlData {
	
	private String resourceName;
	private SceneName sceneName;
	private Stage stage;
	private Scene scene;
	
	/*
	 * This map provides a simple "interface" for inter-controller
	 * communication: the controller A (loaded from FxmlData A) 
	 * sets an option on FxmlData B and then sets FxmlData B's scene
	 * as scene. Controller B will then be able to read the options
	 * that were set from Controller A.
	 * */
	private static Map<String, String> options = new HashMap<>();
	
	/**
	 * @param resourceName the FXML's filename
	 * @param sceneName the FXML's SceneName reference
	 * @param stage the primary Stage in which display the scene
	 */
	public FxmlData(String resourceName, SceneName sceneName, Stage stage) {
		this.resourceName = resourceName;
		this.sceneName = sceneName;
		this.stage = stage;
	}
	
	public String getResourceName() {
		return resourceName;
	}
	
	public Stage getStage() {
		return stage;
	}
	
	public SceneName getSceneName() {
		return sceneName;
	}
	
	public void setScene(Scene scene) {
		this.scene = scene;
	}
	
	public Scene getScene() {
		/*
		 * Disabled to force refresh and controller reinitialization
		if (scene == null) {
			System.out.println("Called FxmlLoad.load");
			scene = new FxmlLoad().load(this);
		}
		*/

		try { 
			System.out.println("Called FxmlLoad.load");
			scene = new FxmlLoad().load(this);
			return scene;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Platform.exit();
			return null;
		}
	}
	
	public boolean hasScene() {
		if (scene == null) {
			return false;
		} else {
			return true;
		}
	}

	public Map<String, String> getOptions() {
		return options;
	}

	public void setOption(String key, String value) {
		options.put(key, value);
	}
	
}
