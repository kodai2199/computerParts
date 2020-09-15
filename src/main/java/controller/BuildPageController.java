package main.java.controller;

import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.java.app.ComputerPartsApp;
import main.java.app.FxmlData;
import main.java.app.SceneName;
import main.java.component.Component;
import main.java.component.Storage;
import main.java.users.Computer;

public class BuildPageController extends StdMenuBarController {
	
	@FXML private TextField buildName;
	@FXML private Button save;
	@FXML private Label buildTotal;
	@FXML private VBox buildpage_vbox;
	private Computer c;
	
	public void initialize(){
		NumberFormat nf = new DecimalFormat("#0.00");
        FxmlData fxml = ComputerPartsApp.getScenes().get(SceneName.BUILDPAGE);
        int id = Integer.valueOf(fxml.getOptions().get("IdComputer"));
        try {
			c = ComputerPartsApp.getUser().getComputer(id);
			buildName.setText(c.getName());
			buildTotal.setText("Grand total: " + nf.format(c.getTotalPrice()) + "€");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        loadComponentsPanes();
		super.initalize(fxml);
	}
	
	private void loadComponentsPanes() {
    	for(String category:Component.COMPONENT_LIST.keySet()) {;
    		Label name = new Label(Component.COMPONENT_LIST.get(category));
			name.getStyleClass().add("item-title");

			// Get the array of all the components of this category
			ArrayList<Component> components = c.getComponentsByCategory(category);
			
			if (category.equals("Storage")) {
				createStorageBox(name, components);
			} else {
				createStandardBox(name, components, category);
			}
    	}
	}
	
	public void createStandardBox(Label name, ArrayList<Component> components, String category) {
		boolean isSet = false;
		String component_name_string = "";
		
		// Compute the text to display in the form (quantity)x (Component's name)
		if (components.size() != 0) {
			component_name_string = components.size() + "x ";
			component_name_string += components.get(0).getBrand() + " " + components.get(0).getName();
			isSet = true;
		} else {
			component_name_string = "Click to choose";
		}
		
		
		// Create the label and populate the container
		Label component_name = new Label(component_name_string);
		HBox data = new HBox(name, component_name);
		data.setAlignment(Pos.CENTER_LEFT);
		data.getStyleClass().add("category-box");
		AnchorPane box = new AnchorPane();
		AnchorPane.setLeftAnchor(data, 45.0);
		AnchorPane.setTopAnchor(data, 20.0);
		AnchorPane.setBottomAnchor(data, 20.0);
		box.getChildren().add(data);
		
		if (isSet) {
			Button removeButton = new Button("Remove all");
			removeButton.getStyleClass().add("std-button");
			EventHandler<MouseEvent> removeHandler = new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					// Remove Components
					removeButton.setDisable(true);
					c.removeAllComponentsOfCategory(category);
					event.consume();
					removeButton.setDisable(false);
					stage.setScene(ComputerPartsApp.getScenes().get(SceneName.BUILDPAGE).getScene());
				}
			};
			removeButton.setOnMouseClicked(removeHandler);
			
			AnchorPane.setRightAnchor(removeButton, 45.0);
			AnchorPane.setTopAnchor(removeButton, 20.0);
			AnchorPane.setBottomAnchor(removeButton, 20.0);
			box.getChildren().add(removeButton);
		}
			
		box.getStyleClass().add("category-box");
		EventHandler<MouseEvent> selectionPageHandler = new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent event) {
		        // Let the user choose a component
		    	chooseComponent(Component.COMPONENT_LIST.get(category));
		        event.consume();
		    }
		};
		box.setOnMouseClicked(selectionPageHandler);
		this.buildpage_vbox.getChildren().add(box);
	}
	
	/*
	 * The method is similar to createStandardBox, but since there
	 * may be multiple different storage components, they need
	 * to be displayed slightly differently. So a VBox is created
	 * instead of an HBox.
	 * */
	public void createStorageBox(Label name, ArrayList<Component> components) {
		boolean isSet = false;
		VBox data = new VBox(name);
		
		/* Prepare an array for the storages (it is the only category
		 * that can have multiple different components), since it
		 * is displayed differently
		 */
		LinkedHashMap<Storage, Integer> storages = new LinkedHashMap<Storage, Integer>();
		if (components.size() != 0) {
			/* Count occurrences of every storage component */
			for (Component component:components) {
				if (!(component instanceof Storage)) {
					System.out.println("There was an error while loading the storages");
					return;
				}
				Storage s = (Storage)component;
				if (storages.containsKey(s)) {
					int quantity = storages.get(s);
					storages.put(s, quantity+1);
				} else {
					storages.put(s, 1);
				}
			}
				
			/*
			 * Create a Label for every storage and add it
			 * to the VBox
			 * */
			for (Storage s:storages.keySet()) {
				int quantity = storages.get(s);
				Label l = new Label(quantity + "x " + s.getBrand() + " " + s.getName() + " " + s.getSize()+"GB");
				data.getChildren().add(l);
			}
			isSet = true;
		} 
		else {
			Label l = new Label("Click to choose");
			data.getChildren().add(l);
		}
		

		data.setAlignment(Pos.CENTER_LEFT);
		data.getStyleClass().add("category-box");
		AnchorPane box = new AnchorPane();
		AnchorPane.setLeftAnchor(data, 45.0);
		AnchorPane.setTopAnchor(data, 20.0);
		AnchorPane.setBottomAnchor(data, 20.0);
		box.getChildren().add(data);
		
		if (isSet) {
			Button removeButton = new Button("Remove all");
			removeButton.getStyleClass().add("std-button");
			EventHandler<MouseEvent> removeHandler = new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					// Remove Components
					removeButton.setDisable(true);
					c.removeAllComponentsOfCategory("Storage");
					event.consume();
					removeButton.setDisable(false);
					stage.setScene(ComputerPartsApp.getScenes().get(SceneName.BUILDPAGE).getScene());
				}
			};
			removeButton.setOnMouseClicked(removeHandler);
			
			AnchorPane.setRightAnchor(removeButton, 45.0);
			AnchorPane.setTopAnchor(removeButton, 20.0);
			AnchorPane.setBottomAnchor(removeButton, 20.0);
			box.getChildren().add(removeButton);
		}
			
		box.getStyleClass().add("category-box");
		EventHandler<MouseEvent> selectionPageHandler = new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent event) {
		        // Let the user choose a component
		    	chooseComponent(Component.COMPONENT_LIST.get("Storage"));
		        event.consume();
		    }
		};
		box.setOnMouseClicked(selectionPageHandler);
		this.buildpage_vbox.getChildren().add(box);
	}
	
	public void saveName() {
		c.rename(buildName.getText());
	}
	
	private void chooseComponent(String category) {
		FxmlData fxml = ComputerPartsApp.getScenes().get(SceneName.CATEGORYLIST);
		fxml.resetOptions();
		fxml.setOption("Category", category);
		fxml.setOption("IdComputer", ""+c.getId());
		fxml.setLastSceneName(sceneName);
		stage.setScene(ComputerPartsApp.getScenes().get(SceneName.CATEGORYLIST).getScene());
	}
}
