package component;

public class Case_fan extends Component {
	private String lighting;
	private String type;
	private int airflow;
	private int size;
	
	public Case_fan(String name, double price, String brand, String lighting, String type, int airflow, int size) {
		super(name, price, brand);
		this.lighting = lighting;
		this.type = type;
		this.airflow = airflow;
		this.size = size;
	}

	public String getLighting() {
		return lighting;
	}

	public String getType() {
		return type;
	}

	public int getAirflow() {
		return airflow;
	}

	public int getSize() {
		return size;
	}
	
}
