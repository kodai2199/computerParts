package main.java.app;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import main.java.component.*;
import main.java.controller.CategoryListController;
import main.java.db.Connect;
import main.java.users.User;

/**
 * 
 * @author ganze
 *	Puts all the scene information into a Map, and
 *	then shows the login scene.
 */
public class ComputerPartsApp extends Application {
	
	private static final String LOGIN_FXML = "fxml/login.fxml";
	private static final String HOME_FXML = "fxml/home.fxml";
	private static final String SIGNUP_FXML = "fxml/signup.fxml";
	private static final String CATEGORYLIST_FXML = "fxml/categorylist.fxml";
	private static final String BUILDLIST_FXML = "fxml/buildlist.fxml";
	private static final String BUILDPAGE_FXML = "fxml/buildpage.fxml";
	
	// This is used to keep track of who did the login
	private static User user;
	
	/* A map of all the brands' logos. Loading the Images object statically
	 * here greatly reduces the in-app loading times.
	 * */ 
	private static Map<String, Image> brand_logos = new HashMap<>();
	
	/* The map of all the scenes. This allows easy switching 
	 * between controllers and scenes.  
	 * */
	private static Map<SceneName, FxmlData> scenes = new HashMap<>();
	
	/* Arraylists of all the components. 
	 * If in-app reloading will be required, it may be done
	 * with and additional thread.
	 * */
	
	private static ArrayList<Cases> cases;
	private static ArrayList<CPU_Cooling> cpu_coolers;
	private static ArrayList<CPU> cpus;
	private static ArrayList<Graphic_Cards> gpus;
	private static ArrayList<Memory> memory;
	private static ArrayList<Motherboards> motherboards;
	private static ArrayList<Power_supplies> psus;
	private static ArrayList<Storage> storages;
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	/*
	 * This method must be implemented in order for JavaFX to run.
	 * It prepares the scenes Map with all the scenes and then
	 * shows the login scene on the stage.
	 * */
	public void start(Stage stage) {
		loadComponentsLists();

	    Screen screen = Screen.getPrimary();
	    Rectangle2D bounds = screen.getVisualBounds();
	    stage.setX(0);
	    stage.setY(0);
	    stage.setWidth(bounds.getWidth() / 2);
	    stage.setHeight(bounds.getHeight() / 2);
		
		scenes.put(SceneName.LOGIN, new FxmlData(LOGIN_FXML, SceneName.LOGIN, stage));
		scenes.put(SceneName.HOME, new FxmlData(HOME_FXML, SceneName.HOME, stage));
		scenes.put(SceneName.SIGNUP, new FxmlData(SIGNUP_FXML, SceneName.SIGNUP, stage));
		scenes.put(SceneName.CATEGORYLIST, new FxmlData(CATEGORYLIST_FXML, SceneName.CATEGORYLIST, stage));
		scenes.put(SceneName.BUILDLIST, new FxmlData(BUILDLIST_FXML, SceneName.BUILDLIST, stage));
		scenes.put(SceneName.BUILDPAGE, new FxmlData(BUILDPAGE_FXML, SceneName.BUILDPAGE, stage));
		
		InputStream res = CategoryListController.class.getClassLoader().getResourceAsStream("icons/Store.png");
		Image cpLogo = new Image(res);
		stage.getIcons().add(cpLogo);
		stage.setScene(scenes.get(SceneName.LOGIN).getScene());
		stage.setTitle("Build your computer");
		stage.show();

	}	
	
	/* 
	 * Preload all components to make the software overall more responsive.
	 * For every component, the method will try to load the logo. This 
	 * significantly reduces the time needed to display pages with lots of 
	 * brands logos, as they are only loaded once. 
	 * */
	private void loadComponentsLists() {
		// Preload all Components lists to make the software overall more responsive
		// For every component, load the logo
		try {
			Connect db = new Connect();
			
			cases = db.loadCases();
			for (Cases c:cases) {
				loadBrandLogos(c.getBrand());
			}
			
			cpu_coolers = db.loadCPU_coolers();
			for (CPU_Cooling c:cpu_coolers) {
				loadBrandLogos(c.getBrand());
			}
			
			cpus = db.loadCPUs();
			for (CPU c:cpus) {
				loadBrandLogos(c.getBrand());
			}
			
			gpus = db.loadGPUs();
			for (Graphic_Cards g:gpus) {
				loadBrandLogos(g.getBrand());
			}
				
			motherboards = db.loadMotherBoards();
			for (Motherboards m:motherboards) {
				loadBrandLogos(m.getBrand());
			}
			
			memory = db.loadRAMs();
			for (Memory m:memory) {
				loadBrandLogos(m.getBrand());
			}
			psus = db.loadPowerSupplies();
			for (Power_supplies p:psus) {
				loadBrandLogos(p.getBrand());
			}
			
			storages = db.loadStorages();
			for (Storage s:storages) {
				loadBrandLogos(s.getBrand());
			}

		} catch (Exception e) {
			System.out.println("There was an error while loading the components' lists.");
			e.printStackTrace();
		}
		System.out.println("Components loaded");
	}
	
	/* 
	 * The method checks if the Image object of the
	 * provided brand has already been loaded. If it hasn't, 
	 * then the method will load it. 
	 * @param brand The brand name
	 * */
	private void loadBrandLogos(String brand) {
		if (brand_logos.containsKey(brand)) {
			return;
		} else {
			InputStream res = CategoryListController.class.getClassLoader().getResourceAsStream("img/"+brand+".png");
			Image img = new Image(res);
			brand_logos.put(brand, img);
		}
	}
	
	
	/*
	 * Getters & Setters
	 * */
	public static Map<SceneName, FxmlData> getScenes(){
		return scenes;
	}
	
	public static void updateScenes(SceneName name, FxmlData info) {
		scenes.put(name, info);
	}
	
	public static User getUser() {
		return user;
	}
	
	public static void setUser(User u) {
		user = u;
	}
	
	public static Image getLogo(String brand) {
		return brand_logos.get(brand);
	}
	
	public static ArrayList<Cases> getCases(){
		return cases;
	}
	
	public static ArrayList<CPU_Cooling> getCPU_coolers(){
		return cpu_coolers;
	}
	
	public static ArrayList<CPU> getCPUs() {
		return cpus;
	}
	
	public static ArrayList<Graphic_Cards> getGPUs() {
		return gpus;
	}
	
	public static ArrayList<Memory> getMemory() {
		return memory;
	}
	
	public static ArrayList<Motherboards> getMotherboards() {
		return motherboards;
	}
	
	public static ArrayList<Power_supplies> getPSUs() {
		return psus;
	}
	
	public static ArrayList<Storage> getStorages() {
		return storages;
	}
	
	
}
