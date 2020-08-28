package main.java.controller;

import main.java.app.ComputerPartsApp;
import main.java.app.FxmlData;
import main.java.app.SceneName;
import main.java.component.Component;
import java.io.InputStream;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;


public class HomeController extends StdMenuBarController {
	
	// Home widgets
	@FXML
	private TilePane home_tilepane;
	
	public void initialize(){
		/*
		 * Categories are dynamically created before the scene is shown,
		 * and the order of COMPONENT_LIST is followed. Images
		 * are taken from /src/main/resources/icons/xxxx.png, where
		 * xxxx is the name of the category (for example, CPU).
		 * 
		 * Properties and classes are also added to the relevant widgets.
		 * */
		for(String category:Component.COMPONENT_LIST.keySet()) {
			InputStream res = HomeController.class.getClassLoader().getResourceAsStream("icons/"+category+".png");
			Image img = new Image(res);
			ImageView i = new ImageView(img);
			i.setPreserveRatio(true);
			i.setFitHeight(150.0);
			i.setFitWidth(200.0);
			i.setPickOnBounds(true);
			Label l = new Label(Component.COMPONENT_LIST.get(category));
			l.getStyleClass().add("item-title");
			HBox box = new HBox(i, l);
			box.getStyleClass().add("category-box");
			EventHandler<MouseEvent> boxHandler = new EventHandler<MouseEvent>() {
			    @Override
			    public void handle(MouseEvent event) {
			        openCategoryList(l.getText());
			        event.consume();
			    }
			};
			box.setOnMouseClicked(boxHandler);
			this.home_tilepane.getChildren().add(box);
		}
		System.out.println("Initialized Home Controller");
	}
	
	public void openCategoryList(String category) {
		/*
		 * Update the fxml object so that it will load the selected category.
		 * For this to work reliably the fxml and the controller have to be
		 * reloaded every time, so this is why the "single load protection"
		 * is commented in FxmlData and FxmlLoad.
		 * */
        FxmlData fxml = ComputerPartsApp.getScenes().get(SceneName.CATEGORYLIST);
        fxml.setOption("Category", category);
        stage.setScene(ComputerPartsApp.getScenes().get(SceneName.CATEGORYLIST).getScene());
	}
	
}
