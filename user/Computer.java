package user;
import component.*;
import java.util.*;
public class Computer {
	private ArrayList<Component> computer;
	private String name;
	private int nram;
	private int ngpu;
	
	public Computer(String name) {
		computer=new ArrayList<Component>();
		this.name=name;
		this.nram=0;
		this.ngpu=0;
	}
	
	public boolean addComponent(Component c) {
		Class<? extends Component> tmp = c.getClass();
		if(tmp.getSimpleName().equalsIgnoreCase("Memory")) { //Check if the component is a Memory and increment nram
			if(nram==8) return false;
			else nram+=1;
		}
		else if(tmp.getSimpleName().equalsIgnoreCase("Graphic_Cards")) { //Check if the component is a GPU and increment ngpu
			if(ngpu==2) return false;
			else ngpu+=1;
		}
		if(checkCompatibility(c)) { 
			computer.add(c);
			return true;
		}
		else return false;
	}
	
	public boolean removeComponent(Component c) {
		boolean b=computer.remove(c);
		return b;
	}
	
	private boolean checkCompatibility(Component c) {
		return true;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void rename(String name) {
		this.name=name;
	}
	
	public double getTotalPrice() {
		double tot=0;
		for(Component i:computer) {
			tot+=i.getPrice();
		}
		return tot;
	}
}