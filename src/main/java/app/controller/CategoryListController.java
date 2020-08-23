package main.java.app.controller;

import main.java.app.ComputerPartsApp;
import main.java.app.FxmlData;
import main.java.app.SceneName;

public class CategoryListController extends GenericController {
	
	public void initialize() {
		
        FxmlData fxml = ComputerPartsApp.getScenes().get(SceneName.CATEGORYLIST);
        String category = fxml.getOptions().get("Category");
        System.out.println("Category "+category);
		
	}

}
