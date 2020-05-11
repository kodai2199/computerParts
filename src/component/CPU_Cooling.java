package component;

public class CPU_Cooling extends Component {
	private String lighting;
	private String type;
	private int airflow;
	private String socket;
	private int height;


	public CPU_Cooling(String name, double price, String brand, String lighting, String type, int airflow,
			String socket, int height) {
		super(name, price, brand);
		this.lighting = lighting;
		this.type = type;
		this.airflow = airflow;
		this.socket = socket;
		this.height = height;
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

	public String getSocket() {
		return socket;
	}

	public int getHeight() {
		return height;
	}
}
