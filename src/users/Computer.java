package users;
import component.*;
import java.util.*;
public class Computer {
	private ArrayList<Component> computer;
	private String name;
	private int nram;
	private int ngpu;
	private int nmotherboard;
	private int ncpu;
	private int npsu;
	private int ncool;
	private int ncase;
	
	public Computer(String name) {
		computer=new ArrayList<Component>();
		this.name=name;
		this.nram=0;
		this.ngpu=0;
		this.ncase=0;
		this.ncool=0;
		this.ncpu=0;
		this.nmotherboard=0;
		this.npsu=0;
		this.ncase=0;
	}
	
	public boolean addComponent(Component c) {
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
		if (computer.size()==0)														//If there isn't any component, return true 
			return true;
		Class<? extends Component> tmp = c.getClass();
		if(tmp.getSimpleName().equalsIgnoreCase("Motherboards")) {					//Check if the component is a motherboard
			if(this.nmotherboard==1) 
				return false;
			else 
				this.nmotherboard+=1;
			Motherboards m=(Motherboards)c;
			if(checkMotherboards(m)) 
				return true;
			else {
				this.nmotherboard-=1;
				return false;
			}
		}																		
		else if(tmp.getSimpleName().equalsIgnoreCase("CPU")) {						//Check if the component is a CPU
			if(ncpu==1)
				return false;
			else
				this.ncpu+=1;
			CPU cpu=(CPU)c;
			if(checkCPU(cpu))
				return true;
			else {
				this.ncpu-=1;
				return false;
			}
		}
		else if(tmp.getSimpleName().equalsIgnoreCase("Memory")) {					//Check if the component is a RAM
			if(nram==8) 
				return false;
			else 
				nram+=1;
			Memory ram=(Memory)c;
			if(checkMemory(ram)) 
				return true;
			else {
				nram-=1;
				return false;
			}
		}
		else if(tmp.getSimpleName().equalsIgnoreCase("CPU_Cooling")) {				//Check if the component is a cooler
			if(this.ncool==1)
				return false;
			else
				this.ncool+=1;
			CPU_Cooling cc=(CPU_Cooling)c;
			if(checkCooler(cc))
				return true;
			else {
				ncool-=1;
				return false;
			}
		}
		else if(tmp.getSimpleName().equalsIgnoreCase("Cases")) {					//Check if the component is a case
			if(this.ncase==1)
				return false;
			else
				ncase+=1;
			Cases cs=(Cases)c;
			if(checkCases(cs))
				return true;
			else {
				ncase-=1;
				return false;
			}
		}
		else if(tmp.getSimpleName().equalsIgnoreCase("Graphic_Cards")) {			//Check if the component is a GPU
			if(ngpu==2)
				return false;
			else 
				ngpu+=1;
			Graphic_Cards gpu=(Graphic_Cards)c;
			if(checkGPU(gpu)) 
				return true;
			else {
				ngpu-=1;
				return false;
			}
		}
		else if(tmp.getSimpleName().equalsIgnoreCase("Power_supplies")) {			//Check if the component is a PSU
			if(this.npsu==1)
				return false;
			else
				npsu+=1;
			Power_supplies psu=(Power_supplies)c;
			if(checkPSU(psu))
				return true;
			else {
				npsu-=1;
				return false;
			}
		}
		else if(tmp.getSimpleName().equalsIgnoreCase("Storage")) {
			Storage st=(Storage)c;
			if(checkStorage(st))
				return true;
			else 
				return false;
		}
		return true;
	}
	
	private boolean checkMotherboards(Motherboards m) {
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
				if(!cases.getMotherboards().contains(m.getSize()))
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
		if(m.getMax_memory()<getMemory())
			return false;
		return true;
	}
	
	private boolean checkCPU(CPU c) {
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
	
	private boolean checkMemory(Memory ram) {
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
				if(m.getMax_memory()<(getMemory()+ram.getSize()))
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
	
	private boolean checkCooler(CPU_Cooling cc) {
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
	
	private boolean checkCases(Cases cs) {
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

	private boolean checkGPU(Graphic_Cards gpu) {
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
	
	private boolean checkPSU(Power_supplies psu) {
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
	
	private boolean checkStorage(Storage st) {
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
	
	private int getM_2() {
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
	
	public int getWattage() {
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
	
	public int getStorage() {
		int st=0;
		for(Component c:computer) {
			if(c.getClass().getSimpleName().equalsIgnoreCase("Storage")) {
				Storage s=(Storage)c;
				st+=s.getSize();
			}
		}
		return st;
	}
	
	public int getMemory() {
		int m=0;
		for(Component c:computer) {
			if(c.getClass().getSimpleName().equalsIgnoreCase("Memory")) {
				Memory tmp=(Memory)c;
				m+=tmp.getSize();
			}
		}
		return m;
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