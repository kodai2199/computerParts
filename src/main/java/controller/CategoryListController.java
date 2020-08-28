package main.java.controller;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import main.java.app.ComputerPartsApp;
import main.java.app.FxmlData;
import main.java.app.SceneName;
import main.java.component.*;
import main.java.db.Connect;

public class CategoryListController extends StdMenuBarController {
	
	private Connect db;
	
	@FXML
	private TilePane categorylist_tilepane;
	// TODO: RENAME ALL BRANDS IMAGES

	
	public void initialize() {
        FxmlData fxml = ComputerPartsApp.getScenes().get(SceneName.CATEGORYLIST);
        String category = fxml.getOptions().get("Category");
        try {
        	db = new Connect();
        	switch (category) {
        		case "CPUs":
        			displayCPUs(ComputerPartsApp.getCPUs());
        			break;
        		default:
        			System.out.println("No valid category specified.");
        			break;
        	}
        } catch (SQLException e) {
        	e.printStackTrace();
        	System.out.println("There was a problem with the database");
        } catch (ClassNotFoundException e) {
        	e.printStackTrace();
        	System.out.println("There was a problem while loading the database classes");
        }
        System.out.println("Category "+category);	
	}
	
	
	
	private void displayCPUs(ArrayList<CPU> cpus) {
		for (CPU c:cpus) {
			InputStream res = CategoryListController.class.getClassLoader().getResourceAsStream("img/"+c.getBrand()+".png");
			Image img = new Image(res);
			ImageView i = new ImageView(img);
			i.setPreserveRatio(true);
			i.setFitHeight(150.0);
			i.setFitWidth(200.0);
			i.setPickOnBounds(true);
			Label name = new Label(c.getName());
			name.getStyleClass().add("item-title");
			Label socket = new Label("Socket "+c.getSocket());
			Label cores = new Label(c.getCores() + " Cores");
			Label frequency = new Label((double)c.getFrequency()/1000d + "GHz");
			Label price = new Label(c.getPrice()+ "€");
			Label tdp = new Label(c.getWattage()+ "W TDP");
			VBox box = new VBox(i, name, socket, cores, frequency, price, tdp);
			box.getStyleClass().add("category-box");
			this.categorylist_tilepane.getChildren().add(box);
		}
	}
}
