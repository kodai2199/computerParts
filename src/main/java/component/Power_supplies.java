package main.java.component;

public class Power_supplies extends Component {
	private int wattage;
	private int length;
	private String type;		//Modular or other
	private String size;		//ATX or other
	protected static final String category_name = "Power_supplies";
	
	public Power_supplies(int id, String name, double price, String brand, int wattage, int length, String type, String size) {
		super(id, name, price, brand);
		this.wattage = wattage;
		this.length = length;
		this.type = type;
		this.size = size;
	}

	public int getWattage() {
		return wattage;
	}

	public int getLength() {
		return length;
	}

	public String getType() {
		return type;
	}

	public String getSize() {
		return size;
	}

	public String getCategory() {
		 return category_name;
	}
	
}
