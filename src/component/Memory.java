package component;

public class Memory extends Component {
	private int size;
	private String ram_type;
	private int frequency;
	private String lighting;
	
	public Memory(String name, double price, String brand, String ram_type, int frequency,String lighting, int size) {
		super(name,price,brand);
		this.ram_type = ram_type;
		this.frequency = frequency;
		this.lighting=lighting;
		this.size=size;
	}

	public String getRAM_type() {
		return ram_type;
	}

	public int getFrequency() {
		return frequency;
	}

	public String getLighting() {
		return lighting;
	}

	public int getSize() {
		return size;
	}
}
