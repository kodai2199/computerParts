package main.java.users;

import main.java.component.*;
import main.java.db.Connect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/*
 * TODO all method must be edited to support direct resultset r/w;
 * */
public class Computer {
	/*
	 * IdComputer
	 * */
	private int id;
	private String name;
	private ResultSet computerComponents;
	
	/*
	 * Arraylist of all the components
	 * */
	private ArrayList<Component> computer;
	
	/*
	 * Components
	 * For simplicity, a single type of RAM and a single type of GPU
	 * can be used at a time.
	 */
	private Cases chassis = null;
	private CPU_Cooling cpu_cooler = null;
	private CPU cpu = null;
	private Graphic_Cards gpu = null;
	private int ngpu = 0;
	private Memory ram = null;
	private int nram = 0;
	private Motherboards motherboard = null;
	private Power_supplies psu = null;
	
	public Computer(int id, String name, ResultSet computerComponents) {
		this.id = id;
		computer = new ArrayList<Component>();
		this.name = name;
		this.computerComponents = computerComponents;
		loadComponents();
	}
	
	/*
	 * Tries to load from the database all the components in this build,
	 * by reading the IdComponent from the computerComponents ResultSet.
	 * This must be called at class construction in order to fill all the
	 * relevant data before the class can be used.
	 * */
	public void loadComponents() {
		try {
			Connect db = new Connect();
			while(computerComponents.next()) {
				int idComponent = computerComponents.getInt("IdComponent");
				switch (computerComponents.getString("Category")){
					case "Cases":
						chassis = db.loadCase(idComponent);
						computer.add(chassis);
						break;
					case "CPU_Cooling":
						cpu_cooler = db.loadCPU_cooler(idComponent);
						computer.add(cpu_cooler);
						break;
					case "CPU":
						cpu = db.loadCPU(idComponent);
						computer.add(cpu);
						break;
					case "Graphic_Cards":
						gpu = db.loadGPU(idComponent);
						ngpu = computerComponents.getInt("Quantity");
						for (int i = 0; i < ngpu; i++) {
							computer.add(gpu);
						}
						break;
					case "Memory":
						ram = db.loadMemory(idComponent);
						nram = computerComponents.getInt("Quantity");
						for (int i = 0; i < nram; i++) {
							computer.add(ram);
						}
						break;
					case "Motherboards":
						motherboard = db.loadMotherboard(idComponent);
						computer.add(motherboard);
						break;
					case "Power_supplies":
						psu = db.loadPowerSupply(idComponent);
						computer.add(psu);
						break;
					case "Storage":
						int quantity = computerComponents.getInt("Quantity");
						for(int i = 0; i < quantity; i++) {
							computer.add(db.loadStorage(idComponent));
						}
						break;
				}
			}
			computerComponents.beforeFirst();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean addComponent(Component c) {
		if(checkCompatibility(c, true)) { 
			try {
				// Just increase quantity for gpus and rams
				if (c.getCategory().equals("Graphic_Cards") && ngpu > 1) {
					while(computerComponents.next()) {
						if(computerComponents.getInt("IdComponent") == gpu.getId()) {
							computerComponents.updateInt("Quantity", ngpu);
							computerComponents.updateRow();
							break;
						}
					}
					computerComponents.beforeFirst();
				} else if (c.getCategory().equals("Memory") && nram > 1){
					while(computerComponents.next()) {
						if(computerComponents.getInt("IdComponent") == ram.getId()) {
							computerComponents.updateInt("Quantity", nram);
							computerComponents.updateRow();
							break;
						}
					}
					computerComponents.beforeFirst();
				} else if(c.getCategory().equals("Storage") && hasComponent(c)){
					while(computerComponents.next()) {
						if(computerComponents.getInt("IdComponent") == c.getId()) {
							int old_quantity = computerComponents.getInt("Quantity");
							computerComponents.updateInt("Quantity", old_quantity+1);
							computerComponents.updateRow();
							break;
						}
					}
					computerComponents.beforeFirst();
				} else {
					computerComponents.moveToInsertRow();
					computerComponents.updateInt("IdComputer", id);
					computerComponents.updateInt("IdComponent", c.getId());
					computerComponents.updateInt("Quantity", 1);
					computerComponents.updateString("Category", c.getCategory());
					computerComponents.insertRow();
					computerComponents.beforeFirst();
				}
				computer.add(c);
			} catch (SQLException e) {
				// ERROR while inserting component
				e.printStackTrace();
			}
			return true;
		}
		else 
			return false;
	}
	
	public void removeComponent(Component c) {
		if (getComponentById(c.getId()) == null) {
			System.out.println("No such component to be removed");
			return;
		}
		for (int i = 0; i < computer.size(); i++) {
			if (computer.get(i).getId() == c.getId()) {
				computer.remove(i);
			}
		}
		switch (c.getCategory()) {
			case "Cases":
				chassis = null;
				break;
			case "CPU_Cooling":
				cpu_cooler = null;
				break;
			case "CPU":
				cpu = null;
				break;
			case "Graphic_Cards":
				gpu = null;
				ngpu = 0;
				break;
			case "Memory":
				ram = null;
				nram = 0;
				break;
			case "Motherboards":
				motherboard = null;
				break;
			case "Power_supplies":
				psu = null;
				break;
		}
		try {
			while (computerComponents.next()) {
				if(computerComponents.getInt("IdComponent") == c.getId()) {
					computerComponents.deleteRow();
				}			
			}
			computerComponents.beforeFirst();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void removeAllComponentsOfCategory(String category) {
		ArrayList<Component> toRemove = new ArrayList<Component>();
		for (Component c:computer) {
			if (c.getCategory() == category) {
				toRemove.add(c);
			}
		}
		System.out.println("TO REMOVE: "+toRemove.size());
		for (Component c:toRemove) {
			removeComponent(c);
		}
	}
	
	/*
	 * Checks the compatibility of a given Component c
	 * with the rest of the build. If updateVariables is set to true
	 * and the component is compatible, the relevant variable will
	 * be updated.
	 * */
	private boolean checkCompatibility(Component c, boolean updateVariables) {
		String category = c.getCategory();
		if(category.equalsIgnoreCase("Motherboards")) {					//Check if the component is a motherboard
			if(motherboard != null) 
				return false;
			else {
				Motherboards tmp = (Motherboards)c;
				if(checkMotherboards(tmp)) {
					if (updateVariables) {
						motherboard = tmp;
					}
					return true;
				}
				else {
					return false;
				}
			}
		}																		
		else if(category.equalsIgnoreCase("CPU")) {						//Check if the component is a CPU
			if(cpu != null)
				return false;
			else {
				CPU tmp = (CPU)c;
				if(checkCPU(tmp)) {
					if (updateVariables) {
						cpu = tmp;
					}
					return true;
				}
				else {
					return false;
				}
			}
		}
		else if(category.equalsIgnoreCase("Memory")) {					//Check if the component is a RAM
			// Different RAMs are not considered compatible
			if (nram > 0) {
				if(!ram.equals(c))
					return false;
			}
			if(nram == 8) 
				return false;
			else {
				Memory tmp = (Memory)c;
				if(checkMemory(tmp)) {
					if (updateVariables) {
						ram = tmp;
						nram++;
					}
					return true;
				}
				else {
					return false;
				}
			}
		}
		else if(category.equalsIgnoreCase("CPU_Cooling")) {				//Check if the component is a cooler
			if(cpu_cooler != null)
				return false;
			else {
				CPU_Cooling tmp = (CPU_Cooling)c;
				if(checkCooler(tmp)) {
					if (updateVariables) {
						cpu_cooler = tmp;
					}
					return true;
				}
				else {
					return false;
				}
			}
		}
		else if(category.equalsIgnoreCase("Cases")) {					//Check if the component is a case
			if(chassis != null)
				return false;
			else {
				Cases tmp = (Cases)c;
				if(checkCases(tmp)) {
					if (updateVariables) {
						chassis = tmp;
					}
					return true;
				}
				else {
					return false;
				}
			}
		}
		else if(category.equalsIgnoreCase("Graphic_Cards")) {			//Check if the component is a GPU
			// Different GPUs are not considered compatible
			if (ngpu > 0) {
				if(!gpu.equals(c))
					return false;
			}
			if(ngpu==2)
				return false;
			else {
				Graphic_Cards tmp = (Graphic_Cards)c;
				if(checkGPU(gpu)) {
					if (updateVariables) {
						gpu = tmp;
						ngpu++;
					}
					return true;
				}
				else {
					return false;
				}
			}
		}
		else if(category.equalsIgnoreCase("Power_supplies")) {			//Check if the component is a PSU
			if(psu != null)
				return false;
			else {
				Power_supplies tmp = (Power_supplies)c;
				if(checkPSU(tmp)) {
					if (updateVariables) {
						psu = tmp;
					}
					return true;
				}
				else {
					return false;
				}
			}
		}
		else if(category.equalsIgnoreCase("Storage")) {					//Check if the component is a storage
			Storage st=(Storage)c;
			if(checkStorage(st))
				return true;
			else 
				return false;
		}
		return true;
	}
	
	/*
	 * Public wrapper for the checkcompatiblity method.
	 * Allows to check the compatibility without updating
	 * the variables.
	 * */
	public boolean checkCompatibility(Component c) {
		return checkCompatibility(c, false);
	}
	
	private boolean checkMotherboards(Motherboards m) {								//Check the motherboard compatibility with the others component. Return true if the motherboard can be added, else return false.
		for (Component t:computer) {
			Class<? extends Component> tmp = t.getClass();
			if(tmp.getSimpleName().equalsIgnoreCase("CPU")) {					
				CPU cpu=(CPU)t;
				if(!m.getSocket().equalsIgnoreCase(cpu.getSocket()))
					return false;
			}
			else if(tmp.getSimpleName().equalsIgnoreCase("Memory")) {				
				Memory ram=(Memory)t;
				if(m.getRAM_slots()<nram)
					return false;
				if(m.getRAM_type().equalsIgnoreCase(ram.getRAM_type())) {
					if (m.getRAM_speed()<ram.getFrequency())
						return false;
				}
				else 
					return false;
			}
			else if(tmp.getSimpleName().equalsIgnoreCase("Cases")) {					
				Cases cases = (Cases)t;
				for (String size:cases.getMotherboards()) {
					if (size.equals(m.getSize())) {
						return true;
					}
				}
				return false;
			}
			else if(tmp.getSimpleName().equalsIgnoreCase("CPU_Cooling")) {			
				CPU_Cooling cool=(CPU_Cooling)t;
				if(!cool.getSocket().contains(m.getSocket()))
					return false;
			}
			else if(tmp.getSimpleName().equalsIgnoreCase("Graphic_Cards")) {
				Graphic_Cards gpu=(Graphic_Cards)t;
				if(ngpu==2) {
					if(!gpu.getMulti_GPU().contains(m.getMulti_GPU()))
						return false;
				}
			}
			else if(tmp.getSimpleName().equalsIgnoreCase("Storage")) {
				Storage st=(Storage)t;
				if(st.getType().equalsIgnoreCase("M.2")) {
					if(m.getMax_M_2()<(getM_2())) 
						return false;
				}
			}
		}
		if(m.getMax_memory()<getMemoryCapacity())
			return false;
		return true;
	}
	
	private boolean checkCPU(CPU newCPU) {											//Check the CPU compatibility with the others component. Return true if the CPU can be added, else return false.
		if (motherboard != null && !newCPU.getSocket().equalsIgnoreCase(motherboard.getSocket()))
			return false;
		if (cpu_cooler != null && !cpu_cooler.getSocket().contains(newCPU.getSocket()))
			return false;
		int total_wattage = getWattage()+newCPU.getWattage();
		if (psu != null && psu.getWattage()<total_wattage)
			return false;
		return true;
	}
	
	private boolean checkMemory(Memory ram) {										//Check the RAM compatibility with the others component. Return true if the RAM can be added, else return false.
		for (Component t:computer) {
			Class<? extends Component> tmp = t.getClass();
			if(tmp.getSimpleName().equalsIgnoreCase("Motherboards")) {
				Motherboards m=(Motherboards)t;
				if(m.getRAM_slots()<nram)
					return false;
				if(m.getRAM_type().equalsIgnoreCase(ram.getRAM_type())) {
					if (m.getRAM_speed()<ram.getFrequency())
						return false;
				}
				else 
					return false;
				if(m.getMax_memory()<(getMemoryCapacity()+ram.getSize()))
					return false;
			}
			else if(tmp.getSimpleName().equalsIgnoreCase("CPU")) {
				CPU cpu=(CPU)t;
				if (cpu.getFrequency()<ram.getFrequency())
					return false;
			}
			else if(tmp.getSimpleName().equalsIgnoreCase("Memory")) {
				Memory m=(Memory)t;
				if(!m.getRAM_type().equalsIgnoreCase(ram.getRAM_type()))
					return false;
				if(m.getFrequency()!=ram.getFrequency())
					return false;
				if(ram.getSize()!=m.getSize())
					return false;
			}
		}
		return true;
	}
	
	private boolean checkCooler(CPU_Cooling cc) {									//Check the CPU cooler compatibility with the others component. Return true if the CPU cooler can be added, else return false.
		for (Component t:computer) {
			Class<? extends Component> tmp=t.getClass();
			if(tmp.getSimpleName().equalsIgnoreCase("CPU")) {
				CPU cpu=(CPU) t;
				if(!cc.getSocket().contains(cpu.getSocket()))
					return false;
			}
			else if(tmp.getSimpleName().equalsIgnoreCase("Motherboards")) {
				Motherboards m=(Motherboards)t;
				if (!cc.getSocket().contains(m.getSocket()))
					return false;
			}
			else if(tmp.getSimpleName().equalsIgnoreCase("Cases")) {
				Cases cases=(Cases)t;
				if(cc.getHeight()>cases.getMax_cpu_fan_height())
					return false;
			}
		}
		return true;
	}
	
	private boolean checkCases(Cases cs) {											//Check the Case compatibility with the others component. Return true if the case can be added, else return false.
		for(Component t:computer) {
			Class<? extends Component> tmp=t.getClass();
			if(tmp.getSimpleName().equalsIgnoreCase("Motherboards")) {
				Motherboards m=(Motherboards)t;
				if(!cs.getMotherboards().contains(m.getSize()))
					return false;
			}
			else if(tmp.getSimpleName().equalsIgnoreCase("CPU_Cooling")) {
				CPU_Cooling cc=(CPU_Cooling)t;
				if(cc.getHeight()>cs.getMax_cpu_fan_height())
					return false;
			}
			else if(tmp.getSimpleName().equalsIgnoreCase("Power_supplies")) {
				Power_supplies p=(Power_supplies)t;
				if(p.getLength()>cs.getMax_psu_length())
					return false;
				if(!p.getSize().equalsIgnoreCase(cs.getPsu_size()))
					return false;
			}
			else if(tmp.getSimpleName().equalsIgnoreCase("Graphic_Cards")) {
				Graphic_Cards gpu=(Graphic_Cards)t;
				if(gpu.getLength()>cs.getMax_gpu_length())
					return false;
			}
		}
		return true;
	}

	private boolean checkGPU(Graphic_Cards newGPU) {		
		//Check the GPU compatibility with the others component. Return true if the GPU can be added, else return false.
		if (chassis != null && chassis.getMax_gpu_length() < newGPU.getLength())
				return false;
		if(gpu != null) {
				if(!gpu.getMulti_GPU().contains(newGPU.getMulti_GPU()))
					return false;
				if(gpu.getId() != newGPU.getId())
					return false;
		}
		if(motherboard != null) {
			if(ngpu == 1) {
				if(!motherboard.getMulti_GPU().contains(gpu.getMulti_GPU()))
					return false;
			}
		}
		int total_wattage = getWattage()+newGPU.getWattage();
		if (psu != null && psu.getWattage()<total_wattage)
			return false;
		return true;
	}
	
	private boolean checkPSU(Power_supplies psu) {									//Check the PSU compatibility with the others component. Return true if the PSU can be added, else return false.
		for(Component t:computer) {
			Class<?extends Component> tmp=t.getClass();
			if(tmp.getSimpleName().equalsIgnoreCase("Cases")) {
				Cases cs=(Cases)t;
				if(cs.getMax_psu_length()<psu.getLength())
					return false;
				if(!psu.getSize().equalsIgnoreCase(cs.getPsu_size()))
					return false;
			}
		}
		if(getWattage()>psu.getWattage())
			return false;
		return true;
	}
	
	private boolean checkStorage(Storage st) {										//Check if the M.2 storage can be insert in the motherboard. Return true if the M.2 storage can be added, else return false.
		for(Component c:computer) {
			if(c.getClass().getSimpleName().equalsIgnoreCase("Motherboards")) {
				if(st.getType().equalsIgnoreCase("M.2")) {
					Motherboards m=(Motherboards)c;
					if(m.getMax_M_2()<(getM_2()+1))
						return false;
				}
			}
		}
		return true;
	}
	
	
	public Component getComponentById(int id) {
		for (Component c:computer) {
			if (c.getId() == id)
				return c;
		}
		return null;
	}
	
	
	public boolean hasComponent(int id) {
		for (Component c:computer) {
			if (c.getId() == id)
				return true;
		}
		return false;
	}
	
	public boolean hasComponent(Component c) {
		return hasComponent(c.getId());
	}
	
	public ArrayList<Component> getComponentsByCategory(String category) {
		ArrayList<Component> list = new ArrayList<Component>(); 
		for (Component c:computer) {
			if (c.getCategory().equals(category))
				list.add(c);
		}
		return list;
	}
	
	private int getM_2() {															//Get the number of the M.2 storage.
		int m2=0;
		for(Component c:computer) {
			if(c.getClass().getSimpleName().equalsIgnoreCase("Storage")) {
				Storage st=(Storage)c;
				if(st.getType().equalsIgnoreCase("M.2"))
					m2+=1;
			}
		}
		return m2;
	}
	
	public int getWattage() {														//Get the total wattage of the system.
		int totw=0;
		for(Component i:computer) {
			if(i.getClass().getSimpleName().equalsIgnoreCase("CPU")) {
				CPU tmp=(CPU)i;
				totw+=tmp.getWattage();
			}
			if(i.getClass().getSimpleName().equalsIgnoreCase("Graphic_Cards")) {
				Graphic_Cards tmp=(Graphic_Cards)i;
				totw+=tmp.getWattage();
			}
		}
		return totw;
	}
	
	public int getStorage() {														//Get the total storage of the system.
		int st=0;
		for(Component c:computer) {
			if(c.getClass().getSimpleName().equalsIgnoreCase("Storage")) {
				Storage s=(Storage)c;
				st+=s.getSize();
			}
		}
		return st;
	}
	
	//Get the total memory capacity of the system.
	public int getMemoryCapacity() {														
		int m=0;
		for(Component c:computer) {
			if(c.getClass().getSimpleName().equalsIgnoreCase("Memory")) {
				Memory tmp=(Memory)c;
				m+=tmp.getSize();
			}
		}
		return m;
	}
	
	public int getId() {
		return id;
	}
	
 	public String getName() {														//Get the computer's name.
		return name;
	}
	
	public Cases getCase() {
		return chassis;
	}
	
	public CPU_Cooling getCPU_cooler() {
		return cpu_cooler;
	}
	
 	public CPU getCPU() {
		return cpu;
	}
	
 	public Graphic_Cards getGPU() {
 		return gpu;
 	}
 	
 	public int getGPUNumber() {
 		return ngpu;
 	}
 	
 	public Memory getMemory() {
 		return ram;
 	}
 	
 	public int getRAMnumber() {
 		return nram;
 	}
 	
 	public Motherboards getMotherboard() {
 		return motherboard;
 	}
 	
 	public Power_supplies getPSU() {
 		return psu;
 	}
 	
	public void rename(String name) {
		this.name=name;
		Connect db;
		try {
			db = new Connect();
			db.setComputerName(id, name);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	public double getTotalPrice() {													//Get the total price.
		double tot=0;
		for(Component i:computer) {
			tot+=i.getPrice();
		}
		return tot;
	}

}