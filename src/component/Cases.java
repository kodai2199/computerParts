package component;

public class Cases extends Component {
	private String size;
	private String motherboards;
	private String psu_size;
	private int max_psu_length;
	private int max_gpu_length;
	private int max_cpu_fan_height;
	
	public Cases(String name, double price, String brand, String size, String motherboards, String psu_size,
			int max_psu_length, int max_gpu_length, int max_cpu_fan_height) {
		super(name, price, brand);
		this.size = size;
		this.motherboards = motherboards;
		this.psu_size = psu_size;
		this.max_psu_length = max_psu_length;
		this.max_gpu_length = max_gpu_length;
		this.max_cpu_fan_height = max_cpu_fan_height;
	}

	public String getSize() {
		return size;
	}

	public String getMotherboards() {
		return motherboards;
	}

	public String getPsu_size() {
		return psu_size;
	}

	public int getMax_psu_length() {
		return max_psu_length;
	}

	public int getMax_gpu_length() {
		return max_gpu_length;
	}

	public int getMax_cpu_fan_height() {
		return max_cpu_fan_height;
	}
	
	
}
