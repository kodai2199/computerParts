package main.java.app;

import java.io.FileNotFoundException;

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
	
	public Scene getScene() throws FileNotFoundException {
		if (scene == null) {
			scene = new FxmlLoad().load(this);
		}
		return scene;
	}
	
	public boolean hasScene() {
		if (scene == null) {
			return false;
		} else {
			return true;
		}
	}
	
}
