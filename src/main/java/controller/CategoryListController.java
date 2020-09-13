package main.java.controller;


import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import main.java.app.ComputerPartsApp;
import main.java.app.FxmlData;
import main.java.app.SceneName;
import main.java.component.*;

public class CategoryListController extends StdMenuBarController {
	
	@FXML
	private TilePane categorylist_tilepane;
	
	public void initialize() {
        FxmlData fxml = ComputerPartsApp.getScenes().get(SceneName.CATEGORYLIST);
        String category = fxml.getOptions().get("Category");
        fxml.consumeOption("Category");
        switch (category) {
        	case "Cases":
        		// TODO
        		//displayGPUs(ComputerPartsApp.getGPUs());
        		break;
        	case "CPU coolers":
        		// TODO
        		//displayGPUs(ComputerPartsApp.getGPUs());
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
        super.initalize(fxml);
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
			Label price = new Label(c.getPrice()+ "€");
			Label tdp = new Label(c.getWattage()+ "W TDP");
			VBox box = new VBox(i, name, socket, cores, frequency, price, tdp);
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
			Label price = new Label(g.getPrice()+ "€");
			Label tdp = new Label(g.getWattage()+ "W TDP");
			VBox box = new VBox(i, name, core_frequency, memory_frequency, multi_gpu, price, tdp);
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
			Label price = new Label(m.getPrice()+ "€");
			VBox box = new VBox(i, name, type, capacity, frequency, lighting, price);
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
			Label ff = new Label(m.getSize() + "Form factor");
			Label memory_slots = new Label(m.getRAM_slots() + " Memory slots");
			Label memory_type = new Label("Supported memory type: " + m.getRAM_type());
			Label memory_speed = new Label("Maximum memory speed: " + m.getRAM_speed() + "MHz");
			Label memory_capacity = new Label("Maximum memory supported: " + m.getMax_memory() + "GB");
			Label m_2 = new Label( m.getMax_M_2() + " M.2 slots");
			Label multi_gpu = new Label("Multi GPU: " + m.getMulti_GPU());
			Label lighting = new Label("Lighting: " + m.getLighting());
			Label price = new Label(m.getPrice()+ "€");
			VBox box = new VBox(i, name, socket, chipset, ff, memory_slots, memory_type, memory_speed, memory_capacity, m_2, multi_gpu, lighting,  price);
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
			Label price = new Label(p.getPrice()+ "€");
			VBox box = new VBox(i, name, wattage, type, length, ff, price);
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
			Label price = new Label(s.getPrice()+ "€");
			VBox box;
			if (!s.getType().equals("M.2")) {
				Label format = new Label(s.getFormat() + "\" drive");
				box = new VBox(i, name, capacity, type, format, speed, price);
			} else {
				box = new VBox(i, name, capacity, type, speed, price);
			}
			box.getStyleClass().add("category-box");
			this.categorylist_tilepane.getChildren().add(box);
		}
	}
}
