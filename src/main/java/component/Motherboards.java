package main.java.component;

public class Motherboards extends Component {
	private String lighting;
	private String chipset;
	private String socket;
	private String ram_type;
	private int ram_slots;
	private String size;
	private int ram_speed;
	private String multi_GPU;
	private int max_memory;
	private int max_M_2;
	protected static final String category_name = "Motherboards";
	
	public Motherboards(int id, String name, double price, String brand, String lighting, String chipset, String socket, String ram_type, int ram_slots, String size,
			int ram_speed, String multi_GPU,int max_memory, int max_M_2) {
		super(id, name, price, brand);
		this.lighting = lighting;
		this.chipset = chipset;
		this.socket = socket;
		this.ram_type = ram_type;
		this.ram_slots = ram_slots;
		this.size = size;
		this.ram_speed = ram_speed;
		this.multi_GPU = multi_GPU;
		this.max_memory=max_memory;
		this.max_M_2=max_M_2;
	}
	
	public int getMax_M_2() {
		return max_M_2;
	}

	public String getLighting() {
		return lighting;
	}
	public String getChipset() {
		return chipset;
	}
	public String getSocket() {
		return socket;
	}
	public String getRAM_type() {
		return ram_type;
	}
	public int getRAM_slots() {
		return ram_slots;
	}
	public String getSize() {
		return size;
	}
	public int getRAM_speed() {
		return ram_speed;
	}
	public String getMulti_GPU() {
		return multi_GPU;
	}

	public int getMax_memory() {
		return max_memory;
	}
	
	public String getCategory() {
		 return category_name;
	}	
}
