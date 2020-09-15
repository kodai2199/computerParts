package main.java.component;

public class Storage extends Component {
	private String type;
	private double format;
	private int size;
	private int transfer_speed;
	protected static final String category_name = "Storage";

	public Storage(int id, String name, double price, String brand, String type, double format, int size, int transfer_speed) {
		super(id, name, price, brand);
		this.type = type;
		this.format=format;
		this.size = size;
		this.transfer_speed = transfer_speed;
	}

	public String getType() {
		return this.type;
	}

	public double getFormat() {
		return this.format;
	}
	
	public int getSize() {
		return this.size;
	}

	public int getTransfer_speed() {
		return this.transfer_speed;
	}

	public String getCategory() {
		 return category_name;
	}
}