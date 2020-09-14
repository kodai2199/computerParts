package main.java.users;

import main.java.component.*;
import main.java.db.Connect;
import java.sql.ResultSet;
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
					case "Case":
						chassis = db.loadCase(idComponent);
						computer.add(chassis);
						break;
					case "CPU_cooling":
						cpu_cooler = db.loadCPU_cooler(idComponent);
						computer.add(cpu_cooler);
						break;
					case "CPU":
						cpu = db.loadCPU(idComponent);
						computer.add(cpu);
						break;
					case "Graphics_card":
						break;
					case "Memory":
						break;
					case "Motherboard":
						break;
					case "Power_supply":
						break;
					case "Storage":
						break;
				}
			}
			computerComponents.first();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean addComponent(Component c) {
		if(checkCompatibility(c)) { 
			computer.add(c);
			return true;
		}
		else 
			return false;
	}
	
	public boolean removeComponent(Component c) {
		boolean b=computer.remove(c);
		return b;
	}
	
	private boolean checkCompatibility(Component c) {
		if (computer.size() == 0)														//If there isn't any component, return true 
			return true;
		Class<? extends Component> tmp = c.getClass();
		if(tmp.getSimpleName().equalsIgnoreCase("Motherboards")) {					//Check if the component is a motherboard
			if(motherboard != null) 
				return false;
			else {
				motherboard = (Motherboards)c;
				if(checkMotherboards(motherboard)) 
					return true;
				else {
					motherboard = null;
					return false;
				}
			}
		}																		
		else if(tmp.getSimpleName().equalsIgnoreCase("CPU")) {						//Check if the component is a CPU
			if(cpu != null)
				return false;
			else {
				cpu=(CPU)c;
				if(checkCPU(cpu))
					return true;
				else {
					cpu = null;
					return false;
				}
			}
		}
		else if(tmp.getSimpleName().equalsIgnoreCase("Memory")) {					//Check if the component is a RAM
			// Different RAMs are not considered compatible
			if (nram > 0) {
				if(!ram.equals(c))
					return false;
			}
			if(nram == 8) 
				return false;
			else 
				nram+=1;
			ram = (Memory)c;
			if(checkMemory(ram)) 
				return true;
			else {
				nram-=1;
				return false;
			}
		}
		else if(tmp.getSimpleName().equalsIgnoreCase("CPU_Cooling")) {				//Check if the component is a cooler
			if(cpu_cooler != null)
				return false;
			else {
				cpu_cooler = (CPU_Cooling)c;
				if(checkCooler(cpu_cooler))
					return true;
				else {
					cpu_cooler = null;
					return false;
				}
			}
		}
		else if(tmp.getSimpleName().equalsIgnoreCase("Cases")) {					//Check if the component is a case
			if(chassis != null)
				return false;
			else {
				chassis = (Cases)c;
				if(checkCases(chassis))
					return true;
				else {
					chassis = null;
					return false;
				}
			}
		}
		else if(tmp.getSimpleName().equalsIgnoreCase("Graphic_Cards")) {			//Check if the component is a GPU
			// Different GPUs are not considered compatible
			if (ngpu > 0) {
				if(!gpu.equals(c))
					return false;
			}
			if(ngpu==2)
				return false;
			else 
				ngpu += 1;
			gpu = (Graphic_Cards)c;
			if(checkGPU(gpu)) 
				return true;
			else {
				ngpu -= 1;
				return false;
			}
		}
		else if(tmp.getSimpleName().equalsIgnoreCase("Power_supplies")) {			//Check if the component is a PSU
			if(psu != null)
				return false;
			else {
				psu = (Power_supplies)c;
				if(checkPSU(psu))
					return true;
				else {
					psu = null;
					return false;
				}
			}
		}
		else if(tmp.getSimpleName().equalsIgnoreCase("Storage")) {					//Check if the component is a storage
			Storage st=(Storage)c;
			if(checkStorage(st))
				return true;
			else 
				return false;
		}
		return true;
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
	
	private boolean checkCPU(CPU c) {												//Check the CPU compatibility with the others component. Return true if the CPU can be added, else return false.
		for (Component t:computer) {
			Class<? extends Component> tmp = t.getClass();
			if(tmp.getSimpleName().equalsIgnoreCase("Motherboards")) {
				Motherboards motherboard =(Motherboards)t;
				if(!c.getSocket().equalsIgnoreCase(motherboard.getSocket()))
					return false;
			}
			if(tmp.getSimpleName().equalsIgnoreCase("CPU_Cooling")) {			
				CPU_Cooling cool=(CPU_Cooling)t;
				if(!cool.getSocket().equalsIgnoreCase(c.getSocket()))
					return false;
			}
			if(tmp.getSimpleName().equalsIgnoreCase("Memory")) {
				Memory ram=(Memory) t;
				if(c.getFrequency()<ram.getFrequency())
					return false;
			}
		}
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

	private boolean checkGPU(Graphic_Cards gpu) {									//Check the GPU compatibility with the others component. Return true if the GPU can be added, else return false.
		for(Component t:computer) {
			Class<? extends Component> tmp = t.getClass();
			if(tmp.getSimpleName().equalsIgnoreCase("Cases")) {
				Cases cs=(Cases)t;
				if(cs.getMax_gpu_length()<gpu.getLength())
					return false;
			}
			else if(tmp.getSimpleName().equalsIgnoreCase("Graphic_Cards")) {
				Graphic_Cards gc=(Graphic_Cards)t;
				if(!gpu.getMulti_GPU().contains(gc.getMulti_GPU())) 
					return false;
				if(!gpu.getType().equalsIgnoreCase(gc.getType()))
					return false;
			}
			else if(tmp.getSimpleName().equalsIgnoreCase("Motherboards")) {
				Motherboards m=(Motherboards)t;
				if(ngpu==2) {
					if(!m.getMulti_GPU().equalsIgnoreCase(gpu.getMulti_GPU())) 
						return false;
				}
			}
		}
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
	
 	public String getName() {														//Get the computer's name.
		return this.name;
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
 	
	public void rename(String name) {												//Change the computer's name.
		this.name=name;
	}
	
	public double getTotalPrice() {													//Get the total price.
		double tot=0;
		for(Component i:computer) {
			tot+=i.getPrice();
		}
		return tot;
	}
}