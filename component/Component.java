package component;

public class Component {
	private String name;
	private double price;
	private String brand;
	
	public Component (String name, double price, String brand) {
		this.name=name;
		this.price=price;
		this.brand=brand;
	}
	public String getName() {
		return name;
	}
	 public double getPrice() {
		 return price;
	 }
	 
	 public String getBrand() {
		 return brand;
	 }
}
