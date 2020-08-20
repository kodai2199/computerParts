package main.java.app;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class FxmlLoad {
	
	public Scene load(FxmlData fxmlData) throws FileNotFoundException {
		if (fxmlData.hasScene()) {
			return fxmlData.getScene();
		}
		URL url = FxmlLoad.class.getClassLoader().getResource(fxmlData.getResourceName());
		if (url == null) {
			throw(new FileNotFoundException());
		}
		FXMLLoader loader = new FXMLLoader(url);
		Scene scene;
		
		try {
			scene = new Scene(loader.load());
		} catch (IOException e) {
			e.printStackTrace();
			Platform.exit();
			return null;
		}
		
		// Write back the updated FxmlData to scenes Map in ComputerPartsApp
		fxmlData.setScene(scene);
		ComputerPartsApp.updateScenes(fxmlData.getSceneName(), fxmlData);
		Stageable controller = loader.getController();
		if (controller != null) {
			controller.setStage(fxmlData.getStage());
		}
		
		return scene;
	}
	
}
