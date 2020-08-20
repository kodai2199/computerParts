package main.java.component;

public class Power_supplies extends Component {
	private int wattage;
	private int length;
	private String type;		//Modular or other
	private String size;		//ATX or other
	protected static final String printable_name = "Power supplies";
	
	public Power_supplies(String name, double price, String brand, int wattage, int length, String type, String size) {
		super(name, price, brand);
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

	
}
