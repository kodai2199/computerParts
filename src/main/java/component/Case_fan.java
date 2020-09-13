package main.java.component;

public class Case_fan extends Component {
	private String lighting;
	private String type;
	private int airflow;
	private int size;
	protected static final String printable_name = "Case fans";
	
	public Case_fan(int id, String name, double price, String brand, String lighting, String type, int airflow, int size) {
		super(id, name, price, brand);
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
