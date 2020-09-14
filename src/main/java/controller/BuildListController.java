package main.java.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import main.java.app.ComputerPartsApp;
import main.java.app.FxmlData;
import main.java.app.SceneName;
import main.java.users.Computer;
import main.java.users.User;

public class BuildListController extends StdMenuBarController {

	@FXML
	private VBox buildlist_vbox;
	
	public void initialize() {
		User u = ComputerPartsApp.getUser();
		if (u.getComputers().size() == 0) {
			Label noBuilds = new Label("There are no builds!");
			noBuilds.getStyleClass().add("item-title");
			VBox box = new VBox(noBuilds);
			box.getStyleClass().add("category-box");
			this.buildlist_vbox.getChildren().add(box);
		}
		for(Computer c: u.getComputers()) {
			Label name = new Label(c.getName());
			name.getStyleClass().add("item-title");
			String components_s = "CPU: " + c.getCPU().getName();
			Label components = new Label(components_s);
			VBox box = new VBox(name, components);
			box.getStyleClass().add("category-box");
			this.buildlist_vbox.getChildren().add(box);
		}
        FxmlData fxml = ComputerPartsApp.getScenes().get(SceneName.BUILDLIST);
		super.initalize(fxml);
	}
	
}
