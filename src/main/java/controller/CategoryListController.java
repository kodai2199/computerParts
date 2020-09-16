package main.java.controller;


import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashSet;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import main.java.app.ComputerPartsApp;
import main.java.app.FxmlData;
import main.java.app.SceneName;
import main.java.component.*;
import main.java.users.Computer;

public class CategoryListController extends StdMenuBarController {
	
	@FXML
	private TilePane categorylist_tilepane;
	private NumberFormat nf = new DecimalFormat("#0.00");
	private Computer build = null;
	private String category;
	
	public void initialize() {
        FxmlData fxml = ComputerPartsApp.getScenes().get(SceneName.CATEGORYLIST);
        
        /* If IdComputer is set, then the page must include the "addButton"s
         * under each Component, so that the user can add a Component to 
         * a build.
         * */
        if (fxml.isOptionSet("IdComputer")) {
        	int id = Integer.valueOf(fxml.getOptions().get("IdComputer"));
        	try {
				build = ComputerPartsApp.getUser().getComputer(id);
			} catch (FileNotFoundException e) {
				// Error loading the build
				e.printStackTrace();
			}
        }
        
        category = fxml.getOptions().get("Category");
        switch (category) {
        	case "Cases":
        		displayCases(ComputerPartsApp.getCases());
        		break;
        	case "CPU coolers":
        		displayCPU_coolers(ComputerPartsApp.getCPU_coolers());
        		break;
        	case "CPUs":
        		displayCPUs(ComputerPartsApp.getCPUs());
        		break;
        	case "Graphic cards":
        		displayGPUs(ComputerPartsApp.getGPUs());
        		break;
        	case "Memory":
        		displayMemory(ComputerPartsApp.getMemory());
        		break;
        	case "Motherboards":
        		displayMotherboards(ComputerPartsApp.getMotherboards());
        		break;
        	case "Power supplies":
        		displayPSUs(ComputerPartsApp.getPSUs());
        		break;
        	case "Storage":
        		displayStorages(ComputerPartsApp.getStorages());
        		break;
        	default:
        		System.out.println("No valid category specified.");
        		break;
        }
        if (categorylist_tilepane.getChildren().size() == 0) {
            displayNoItemsMessage();	
        }
        super.initalize(fxml);
	}
	
	private void displayNoItemsMessage() {
		Label noBuilds = new Label("There are no compatible items in this category!");
		noBuilds.getStyleClass().add("item-title");
		VBox box = new VBox(noBuilds);
		box.getStyleClass().add("category-box");
		this.categorylist_tilepane.getChildren().add(box);
	}
	
	private void displayCases(ArrayList<Cases> cases) {
		for (Cases c:cases) {
			ImageView i = new ImageView(ComputerPartsApp.getLogo(c.getBrand()));
			i.setPreserveRatio(true);
			i.setFitHeight(150.0);
			i.setFitWidth(200.0);
			i.setPickOnBounds(true);
			Label name = new Label(c.getName());
			name.getStyleClass().add("item-title");
			Label size;
			HashSet<String> motherboards = c.getMotherboards();
			StringBuffer sb = new StringBuffer();
			int j = 1; 
			for (String s:motherboards) {
				sb.append(s);
				if (j < motherboards.size())
					sb.append(", ");
				j++;
			}
			Label supported_motherboards = new Label("Supported motherboards: "+sb.toString());
			Label psu_size = new Label("Supported PSU: " + c.getPsu_size());
			Label max_psu_length = new Label("Max PSU length: "+c.getMax_psu_length()+"mm");
			Label max_gpu_length = new Label("Max GPU length: "+c.getMax_gpu_length()+"mm");
			Label max_cpu_fan_height = new Label("Max CPU cooler height: "+c.getMax_cpu_fan_height()+"mm");
			Label price = new Label(nf.format(c.getPrice())+ "€");
			
			/* If a build is set (so addButtons need to be displayed) and this
			 * component is compatible then show the button.
			 * Else, if a build is set but this component is not compatible
			 * continue to the next component.
			 * 
			 * If no build is set then just add the item normally without the addButton
			 */
			Button addButton;
			VBox box;
			if (build != null && build.checkCompatibility(c)) {
				addButton = createAddButton(c);
				box = new VBox(i, name, supported_motherboards, psu_size, max_psu_length, max_gpu_length, max_cpu_fan_height, price, addButton);
			} else if (build != null) {
				continue;
			} else {
				box = new VBox(i, name, supported_motherboards, psu_size, max_psu_length, max_gpu_length, max_cpu_fan_height, price);
			}
			
			box.getStyleClass().add("category-box");
			this.categorylist_tilepane.getChildren().add(box);
		}
		
	}
	
	private void displayCPU_coolers(ArrayList<CPU_Cooling> cpu_coolers) {
		// TODO Optimize supported socket label (too long now)
		for (CPU_Cooling c:cpu_coolers) {
			ImageView i = new ImageView(ComputerPartsApp.getLogo(c.getBrand()));
			i.setPreserveRatio(true);
			i.setFitHeight(150.0);
			i.setFitWidth(200.0);
			i.setPickOnBounds(true);
			Label name = new Label(c.getName());
			name.getStyleClass().add("item-title");
			Label lighting = new Label("Lighting: "+c.getLighting());
			Label type = new Label("Type: "+c.getType());
			Label airflow = new Label("Airflow: "+c.getAirflow());
			Label socket = new Label("Supported sockets: " + c.getSocket());
			Label height = new Label("Height: "+c.getHeight());
			Label price = new Label(nf.format(c.getPrice())+ "€");
			
			/* If a build is set (so addButtons need to be displayed) and this
			 * component is compatible then show the button.
			 * Else, if a build is set but this component is not compatible
			 * continue to the next component.
			 * 
			 * If no build is set then just add the item normally without the addButton
			 */
			Button addButton;
			VBox box;
			if (build != null && build.checkCompatibility(c)) {
				addButton = createAddButton(c);
				box = new VBox(i, name, socket, height, airflow, type, lighting, price, addButton);
			} else if (build != null) {
				continue;
			} else {
				box = new VBox(i, name, socket, height, airflow, type, lighting, price);
			}
			
			box.getStyleClass().add("category-box");
			this.categorylist_tilepane.getChildren().add(box);
		}
	}
	
	private void displayCPUs(ArrayList<CPU> cpus) {
		for (CPU c:cpus) {
			ImageView i = new ImageView(ComputerPartsApp.getLogo(c.getBrand()));
			i.setPreserveRatio(true);
			i.setFitHeight(150.0);
			i.setFitWidth(200.0);
			i.setPickOnBounds(true);
			Label name = new Label(c.getName());
			name.getStyleClass().add("item-title");
			Label socket = new Label("Socket "+c.getSocket());
			Label cores = new Label(c.getCores() + " Cores");
			Label frequency = new Label((double)c.getFrequency()/1000d + "GHz");
			Label price = new Label(nf.format(c.getPrice())+ "€");
			Label tdp = new Label(c.getWattage()+ "W TDP");
			
			/* If a build is set (so addButtons need to be displayed) and this
			 * component is compatible then show the button.
			 * Else, if a build is set but this component is not compatible
			 * continue to the next component.
			 * 
			 * If no build is set then just add the item normally without the addButton
			 */
			Button addButton;
			VBox box;
			if (build != null && build.checkCompatibility(c)) {
				addButton = createAddButton(c);
				box = new VBox(i, name, socket, cores, frequency, price, tdp, addButton);
			} else if (build != null) {
				continue;
			} else {
				box = new VBox(i, name, socket, cores, frequency, price, tdp);
			}
			
			box.getStyleClass().add("category-box");
			this.categorylist_tilepane.getChildren().add(box);
		}
	}
	
	private void displayGPUs(ArrayList<Graphic_Cards> gpus) {
		for (Graphic_Cards g:gpus) {
			ImageView i = new ImageView(ComputerPartsApp.getLogo(g.getBrand()));
			i.setPreserveRatio(true);
			i.setFitHeight(150.0);
			i.setFitWidth(200.0);
			i.setPickOnBounds(true);
			Label name = new Label(g.getName());
			name.getStyleClass().add("item-title");
			Label core_frequency = new Label("Core frequency: " + g.getCore_frequency() + "MHz");
			Label memory_frequency = new Label("Memory frequency: " + g.getMemory_frequency() + "MHz");
			Label multi_gpu = new Label("Multi GPU support: " + g.getMulti_GPU());
			Label price = new Label(nf.format(g.getPrice())+ "€");
			Label tdp = new Label(g.getWattage()+ "W TDP");
			
			/* If a build is set (so addButtons need to be displayed) and this
			 * component is compatible then show the button.
			 * Else, if a build is set but this component is not compatible
			 * continue to the next component.
			 * 
			 * If no build is set then just add the item normally without the addButton
			 */
			Button addButton;
			VBox box;
			if (build != null && build.checkCompatibility(g)) {
				addButton = createAddButton(g);
				box = new VBox(i, name, core_frequency, memory_frequency, multi_gpu, price, tdp, addButton);
			} else if (build != null) {
				continue;
			} else {
				box = new VBox(i, name, core_frequency, memory_frequency, multi_gpu, price, tdp);
			}
			box.getStyleClass().add("category-box");
			this.categorylist_tilepane.getChildren().add(box);
		}
	}
	
	private void displayMemory(ArrayList<Memory> memory) {
		for (Memory m:memory) {
			ImageView i = new ImageView(ComputerPartsApp.getLogo(m.getBrand()));
			i.setPreserveRatio(true);
			i.setFitHeight(150.0);
			i.setFitWidth(200.0);
			i.setPickOnBounds(true);
			Label name = new Label(m.getName());
			name.getStyleClass().add("item-title");
			Label type = new Label("Type: " + m.getRAM_type());
			Label capacity = new Label("Capacity: " + m.getSize() + "GB");
			Label frequency = new Label(m.getFrequency() + "MHz");
			Label lighting = new Label("Lighting: " + m.getLighting());
			Label price = new Label(nf.format(m.getPrice())+ "€");
			
			/* If a build is set (so addButtons need to be displayed) and this
			 * component is compatible then show the button.
			 * Else, if a build is set but this component is not compatible
			 * continue to the next component.
			 * 
			 * If no build is set then just add the item normally without the addButton
			 */
			Button addButton;
			VBox box;
			if (build != null && build.checkCompatibility(m)) {
				addButton = createAddButton(m);
				box = new VBox(i, name, type, capacity, frequency, lighting, price, addButton);
			} else if (build != null) {
				continue;
			} else {
				box = new VBox(i, name, type, capacity, frequency, lighting, price);
			}
			box.getStyleClass().add("category-box");
			this.categorylist_tilepane.getChildren().add(box);
		}
	}
	
	private void displayMotherboards(ArrayList<Motherboards> motherboards) {
		for (Motherboards m:motherboards) {
			ImageView i = new ImageView(ComputerPartsApp.getLogo(m.getBrand()));
			i.setPreserveRatio(true);
			i.setFitHeight(150.0);
			i.setFitWidth(200.0);
			i.setPickOnBounds(true);
			Label name = new Label(m.getName());
			name.getStyleClass().add("item-title");
			Label socket = new Label(m.getSocket() + " Socket");
			Label chipset = new Label("Chipset " + m.getChipset());
			Label ff = new Label(m.getSize() + " Form factor");
			Label memory_slots = new Label(m.getRAM_slots() + " Memory slots");
			Label memory_type = new Label("Supported memory type: " + m.getRAM_type());
			Label memory_speed = new Label("Maximum memory speed: " + m.getRAM_speed() + "MHz");
			Label memory_capacity = new Label("Maximum memory supported: " + m.getMax_memory() + "GB");
			Label m_2 = new Label( m.getMax_M_2() + " M.2 slots");
			Label multi_gpu = new Label("Multi GPU: " + m.getMulti_GPU());
			Label lighting = new Label("Lighting: " + m.getLighting());
			Label price = new Label(nf.format(m.getPrice())+ "€");
			
			/* If a build is set (so addButtons need to be displayed) and this
			 * component is compatible then show the button.
			 * Else, if a build is set but this component is not compatible
			 * continue to the next component.
			 * 
			 * If no build is set then just add the item normally without the addButton
			 */
			Button addButton;
			VBox box;
			if (build != null && build.checkCompatibility(m)) {
				addButton = createAddButton(m);
				box = new VBox(i, name, socket, chipset, ff, memory_slots, memory_type, memory_speed, memory_capacity, m_2, multi_gpu, lighting, price, addButton);
			} else if (build != null) {
				continue;
			} else {
				box = new VBox(i, name, socket, chipset, ff, memory_slots, memory_type, memory_speed, memory_capacity, m_2, multi_gpu, lighting, price);
			}
			box.getStyleClass().add("category-box");
			this.categorylist_tilepane.getChildren().add(box);
		}
	}
	
	private void displayPSUs(ArrayList<Power_supplies> psus) {
		for (Power_supplies p:psus) {
			ImageView i = new ImageView(ComputerPartsApp.getLogo(p.getBrand()));
			i.setPreserveRatio(true);
			i.setFitHeight(150.0);
			i.setFitWidth(200.0);
			i.setPickOnBounds(true);
			Label name = new Label(p.getName());
			name.getStyleClass().add("item-title");
			Label wattage = new Label(p.getWattage() + "W");
			Label type = new Label(p.getType());
			Label length = new Label("Length: " + p.getLength() + "mm");
			Label ff = new Label(p.getSize() + " Form factor");
			Label price = new Label(nf.format(p.getPrice())+ "€");
			
			/* If a build is set (so addButtons need to be displayed) and this
			 * component is compatible then show the button.
			 * Else, if a build is set but this component is not compatible
			 * continue to the next component.
			 * 
			 * If no build is set then just add the item normally without the addButton
			 */
			Button addButton;
			VBox box;
			if (build != null && build.checkCompatibility(p)) {
				addButton = createAddButton(p);
				box = new VBox(i, name, wattage, type, length, ff, price, addButton);
			} else if (build != null) {
				continue;
			} else {
				box = new VBox(i, name, wattage, type, length, ff, price);
			}			
			box.getStyleClass().add("category-box");
			this.categorylist_tilepane.getChildren().add(box);
		}
	}
	
	private void displayStorages(ArrayList<Storage> storages) {
		for (Storage s:storages) {
			ImageView i = new ImageView(ComputerPartsApp.getLogo(s.getBrand()));
			i.setPreserveRatio(true);
			i.setFitHeight(150.0);
			i.setFitWidth(200.0);
			i.setPickOnBounds(true);
			Label name = new Label(s.getName());	
			name.getStyleClass().add("item-title");
			Label capacity = new Label(s.getSize() + "GB");
			Label type = new Label("Storage type: " + s.getType());
			Label speed = new Label(s.getTransfer_speed() + "MB/s Peak transfer speed");
			Label price = new Label(nf.format(s.getPrice())+ "€");
			
			/* If a build is set (so addButtons need to be displayed) and this
			 * component is compatible then show the button.
			 * Else, if a build is set but this component is not compatible
			 * continue to the next component.
			 * 
			 * If no build is set then just add the item normally without the addButton
			 */
			Button addButton;
			VBox box;
			if (build != null && build.checkCompatibility(s)) {
				addButton = createAddButton(s);
				if (!s.getType().equals("M.2")) {
					Label format = new Label(s.getFormat() + "\" drive");
					box = new VBox(i, name, capacity, type, format, speed, price, addButton);
				} else {
					box = new VBox(i, name, capacity, type, speed, price, addButton);
				}
			} else if (build != null) {
				continue;
			} else {
				if (!s.getType().equals("M.2")) {
					Label format = new Label(s.getFormat() + "\" drive");
					box = new VBox(i, name, capacity, type, format, speed, price);
				} else {
					box = new VBox(i, name, capacity, type, speed, price);
				}
			}
			box.getStyleClass().add("category-box");
			this.categorylist_tilepane.getChildren().add(box);
		}
	}
	
	private Button createAddButton(Component component) {
		Button addButton = new Button("Add to the build");
		EventHandler<MouseEvent> addHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				// Add component
				build.addComponent(component);
				// Return to the build page
				FxmlData fxml = ComputerPartsApp.getScenes().get(SceneName.BUILDPAGE);
				fxml.setLastSceneName(sceneName);
				stage.setScene(ComputerPartsApp.getScenes().get(SceneName.BUILDPAGE).getScene());
				event.consume();
			}
		};
		addButton.setOnMouseClicked(addHandler);
		addButton.getStyleClass().add("std-button");
		return addButton;
	}
}
