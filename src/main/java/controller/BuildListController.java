package main.java.controller;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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
		NumberFormat nf = new DecimalFormat("#0.00");
		User u = ComputerPartsApp.getUser();
		if (u.getComputers().size() == 0) {
			Label noBuilds = new Label("There are no builds!");
			noBuilds.getStyleClass().add("item-title");
			VBox box = new VBox(noBuilds);
			box.getStyleClass().add("category-box");
			this.buildlist_vbox.getChildren().add(box);
		}
		for(Computer c: u.getComputers()) {
			Label name = new Label(c.getName() + " - " + nf.format(c.getTotalPrice()) + "€");
			name.getStyleClass().add("item-title");
			String components_s = "";
			if (c.getCPU() != null) {
				components_s += "CPU: " + c.getCPU().getName() + " - ";
			} 
			if (c.getGPU() != null) {
				components_s += "GPU: " + c.getGPU().getName() + " - ";
			}
			if (c.getMemory() != null) {
				components_s += "RAM: " + c.getMemoryCapacity() + "GB"; 
			}
			if (components_s == "") {
				components_s = "Empty";
			}
			Label components = new Label(components_s);
			VBox data = new VBox(name, components);
			Button deleteButton = new Button("Delete");
			deleteButton.getStyleClass().add("std-button");
			EventHandler<MouseEvent> deleteHandler = new EventHandler<MouseEvent>() {
			    @Override
			    public void handle(MouseEvent event) {
			        // DELETE BUILD
			    	deleteBuild(c.getId());
			        event.consume();
			    }
			};
			deleteButton.setOnMouseClicked(deleteHandler);
			AnchorPane box = new AnchorPane();
			AnchorPane.setLeftAnchor(data, 45.0);
			AnchorPane.setTopAnchor(data, 20.0);
			AnchorPane.setBottomAnchor(data, 20.0);
			AnchorPane.setRightAnchor(deleteButton, 45.0);
			AnchorPane.setTopAnchor(deleteButton, 20.0);
			AnchorPane.setBottomAnchor(deleteButton, 20.0);
			box.getChildren().addAll(data, deleteButton);
			box.getStyleClass().add("category-box");
			EventHandler<MouseEvent> buildPageHandler = new EventHandler<MouseEvent>() {
			    @Override
			    public void handle(MouseEvent event) {
			        // Open the Build page
			    	openBuild(c.getId());
			        event.consume();
			    }
			};
			box.setOnMouseClicked(buildPageHandler);
			this.buildlist_vbox.getChildren().add(box);
		}
        FxmlData fxml = ComputerPartsApp.getScenes().get(SceneName.BUILDLIST);
		super.initalize(fxml);
	}
	
	private void deleteBuild(int id) {
		ComputerPartsApp.getUser().deleteComputer(id);
		stage.setScene(ComputerPartsApp.getScenes().get(SceneName.BUILDLIST).getScene());
	}

	
	private void openBuild(int id) {
        FxmlData fxml = ComputerPartsApp.getScenes().get(SceneName.BUILDPAGE);
        fxml.setOption("IdComputer", ""+id);
        fxml.setLastSceneName(sceneName);
        stage.setScene(ComputerPartsApp.getScenes().get(SceneName.BUILDPAGE).getScene());
	}
}
