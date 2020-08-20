package main.java.component;

public class Graphic_Cards extends Component {
	private String lighting;
	private int vram;
	private int core_frequency;
	private int memory_frequency;
	private int length;
	private int wattage;
	private String multi_GPU;				//SLI or other
	private String type;					//GTX 1080, etc...
	protected static final String printable_name = "Graphic cards";
	
	public Graphic_Cards(String name, double price, String brand, String lighting, int vram, int core_frequency,
			int memory_frequency, int length, int wattage, String multi_GPU,String type) {
		super(name, price, brand);
		this.lighting = lighting;
		this.vram = vram;
		this.core_frequency = core_frequency;
		this.memory_frequency = memory_frequency;
		this.length = length;
		this.wattage = wattage;
		this.multi_GPU = multi_GPU;
		this.type=type;
	}

	public String getLighting() {
		return lighting;
	}

	public int getVram() {
		return vram;
	}

	public int getCore_frequency() {
		return core_frequency;
	}

	public int getMemory_frequency() {
		return memory_frequency;
	}

	public int getLength() {
		return length;
	}

	public int getWattage() {
		return wattage;
	}

	public String getMulti_GPU() {
		return multi_GPU;
	}

	public String getType() {
		return type;
	}

}
