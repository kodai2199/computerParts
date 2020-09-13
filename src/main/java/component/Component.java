package main.java.component;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class Component {
	private String name;
	private int id;
	private double price;
	private String brand;
	private static final String printable_name = "";
	public static final Map<String, String> COMPONENT_LIST;
	
	static {
		Map<String, String> m = new LinkedHashMap<String, String>();
		//m.put("Case_fan", "Case fans");
		m.put("Cases", "Cases");
		m.put("CPU_Cooling", "CPU coolers");
		m.put("CPU", "CPUs");
		m.put("Graphic_Cards", "Graphic cards");
		m.put("Memory", "Memory");
		m.put("Motherboards", "Motherboards");
		m.put("Power_supplies", "Power supplies");
		m.put("Storage", "Storage");
		COMPONENT_LIST = Collections.unmodifiableMap(m);
	}
	
	public Component (int id, String name, double price, String brand) {
		this.id = id;
		this.name=name;
		this.price=price;
		this.brand=brand;
	}
	public String getName() {
		return name;
	}
	 public double getPrice() {
		 return price;
	 }
	 
	 public String getBrand() {
		 return brand;
	 }
	 
	 public int getId() {
		 return id;
	 }
	 
	 public static String getPrintableName() {
		 return printable_name;
	 }	
}
