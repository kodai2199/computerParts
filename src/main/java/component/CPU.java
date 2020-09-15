package main.java.component;

public class CPU extends Component {
	private int frequency;
	private int cores;
	private String socket;
	private int wattage;
	protected static final String category_name = "CPU";
	
	public CPU (int id, String name, double price, String brand, int frequency, int cores, String socket, int wattage) {
		super(id, name, price,brand);
		this.frequency=frequency;
		this.cores=cores;
		this.socket=socket;
		this.wattage=wattage;
	}
	
	 public int getFrequency() {
		 return frequency;
	 }
	 
	 public int getCores () {
		 return cores;
	 }
	 
	 public String getSocket() {
		 return socket;
	 }
	 
	 public int getWattage() {
		 return wattage;
	 }
	 
	public String getCategory() {
		 return category_name;
	}	
}
