package component;

public class Storage extends Component {
	private String type;
	private int size;
	private int transfer_speed;

	public Storage(String name, double price, String brand, String type, int size, int transfer_speed) {
		super(name, price, brand);
		this.type = type;
		this.size = size;
		this.transfer_speed = transfer_speed;
	}

	public String getType() {
		return type;
	}

	public int getSize() {
		return size;
	}

	public int getTransfer_speed() {
		return transfer_speed;
	}

}
