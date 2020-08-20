package main.java.app.controller;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.java.app.Stageable;

public class GenericController implements Stageable {
	protected Stage stage;
	
	protected void show(Node n) {
		FadeTransition ft = new FadeTransition(Duration.millis(100), n);
		ft.setFromValue(1);
		ft.setToValue(1);
		ft.play();
	}

	/** 
	 * @param stage primary stage to set 
	 */
	@Override
	public void setStage(Stage stage) {
		this.stage = stage;
	}
}
