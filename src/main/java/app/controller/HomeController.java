package main.java.app.controller;

import main.java.component.Component;

import java.io.InputStream;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;


public class HomeController extends GenericController {
	
	// Home widgets
	@FXML
	private TilePane home_tilepane;
	
	public void initialize(){
		for(String category:Component.COMPONENT_LIST.keySet()) {
			System.out.println("icons/"+category+".png");
			InputStream res = HomeController.class.getClassLoader().getResourceAsStream("icons/"+category+".png");
			Image img = new Image(res);
			ImageView i = new ImageView(img);
			i.setPreserveRatio(true);
			i.setFitHeight(150.0);
			i.setFitWidth(200.0);
			i.setPickOnBounds(true);
			System.out.println(category);
			System.out.println(Component.COMPONENT_LIST.get(category));
			Label l = new Label(Component.COMPONENT_LIST.get(category));
			HBox box = new HBox(i, l);
			box.getStyleClass().add("category-box");
			System.out.println(this.home_tilepane.getChildren().add(box));
		}
		System.out.println("Initialized Home Controller");
	}
	
}
