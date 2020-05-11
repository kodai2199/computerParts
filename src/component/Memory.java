package component;

public class Memory extends Component {
	private String ram_type;
	private int frequency;
	
	public Memory(String name, double price, String brand, String ram_type, int frequency) {
		super(name,price,brand);
		this.ram_type = ram_type;
		this.frequency = frequency;
	}

	public String getRAM_type() {
		return ram_type;
	}

	public int getFrequency() {
		return frequency;
	}
}
