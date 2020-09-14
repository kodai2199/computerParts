package main.java.db;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;
import main.java.component.*;
import main.java.users.Computer;
import main.java.users.User;

/**
 *  This class manages the connection to the DB
 *	and information exchange
 */
public class Connect {
	private final String readOnlyUser="computerPartsReader";
	private final String readOnlyPassword="w64YyHswZ36xE8J8";
	private final String writeUser="cParts";
	private final String writePassword="u^8e7p6J6anG3Wo%";
	private final String url="jdbc:mysql://ggh.zapto.org:3306/computerParts?useSSL=false";
	private final String driver="com.mysql.jdbc.Driver";
	private Connection conn;
	private Connection writeConnection;
	private Statement st;
	
	public Connect() throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		conn = DriverManager.getConnection(url, readOnlyUser, readOnlyPassword);
		writeConnection = DriverManager.getConnection(url, writeUser, writePassword);
		st = conn.createStatement(ResultSet.CONCUR_READ_ONLY, ResultSet.TYPE_SCROLL_INSENSITIVE);
		//st.setQueryTimeout(10);
	}
	
	public ArrayList<CPU> loadCPUs() throws SQLException {
		ArrayList<CPU> list=new ArrayList<CPU>();
		Statement statement1=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		String query1="SELECT * FROM CPU";
		ResultSet rs=st.executeQuery(query1);
		while (rs.next()) {
			CPU cpu;
			int fr=rs.getInt(2);
			int cores=rs.getInt(3);
			String socket=rs.getString(4);
			int w=rs.getInt(5);
			int i=rs.getInt(1);
			String query2="SELECT * FROM Component WHERE IdComponent = '"+i+"'";
			ResultSet rs2=statement1.executeQuery(query2);
			rs2.next();
			String name=rs2.getString(2);
			BigDecimal n=rs2.getBigDecimal(3);
			String brand=rs2.getString(4);
			cpu=new CPU(i, name, n.doubleValue(), brand, fr, cores, socket, w);
			list.add(cpu);
		}
		statement1.close();
		return list;
	}
	
	public CPU loadCPU(int id) throws SQLException {
		CPU c;
		String query = "SELECT * FROM CPU JOIN Component ON CPU.IdComponent = Component.IdComponent WHERE CPU.IdComponent = '" + id + "'";
		ResultSet rs=st.executeQuery(query);
		if (rs.next()) {
			String name = rs.getString("Name");
			double price = rs.getBigDecimal("Price").doubleValue();
			String brand = rs.getString("BrandName");
			int frequency = rs.getInt("Frequency");
			int cores = rs.getInt("Cores");
			String socket = rs.getString("Socket");
			int wattage = rs.getInt("Wattage");
			c = new CPU(id, name, price, brand, frequency, cores, socket, wattage);
		} else { 
			throw(new SQLException());
		}
		return c;
	}
	
	public ArrayList<Motherboards> loadMotherBoards() throws SQLException {
		ArrayList<Motherboards> list=new ArrayList<Motherboards>();
		Statement statement1=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		String query1="SELECT * FROM Motherboard";
		ResultSet rs=st.executeQuery(query1);
		while (rs.next()) {
			Motherboards mb;
			String lighting=rs.getString(2);
			if (lighting.equals("NO")) {
				lighting = "None";
			}
			String chipset=rs.getString(3);
			String socket=rs.getString(4);
			String ramType=rs.getString(5);
			int ramSlots=rs.getInt(6);
			String size=rs.getString(7);
			int ramSpeed=rs.getInt(8);
			String multiGPU=rs.getString(9);
			int maxMemory=rs.getInt(10);
			int maxM2=rs.getInt(11);
			int i=rs.getInt(1);
			String query2="SELECT * FROM Component WHERE IdComponent = '"+i+"'";
			ResultSet rs2=statement1.executeQuery(query2);
			rs2.next();
			String name=rs2.getString(2);
			BigDecimal n=rs2.getBigDecimal(3);
			String brand=rs2.getString(4);
			mb=new Motherboards(i, name, n.doubleValue(), brand, lighting, chipset, socket, ramType, ramSlots, size, ramSpeed, multiGPU, maxMemory, maxM2);
			list.add(mb);
		}
		statement1.close();
		return list;
	}
	
	public ArrayList<Memory> loadRAMs() throws SQLException {
		ArrayList<Memory> list=new ArrayList<Memory>();
		Statement statement1=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		String query1="SELECT * FROM Memory";
		ResultSet rs=st.executeQuery(query1);
		while (rs.next()) {
			Memory m;
			int size=rs.getInt(2);
			String type=rs.getString(3);
			int frequency=rs.getInt(4);
			String lighting=rs.getString(5);
			if (lighting.equals("0")) {
				lighting = "None";
			}
			int i=rs.getInt(1);
			String query2="SELECT * FROM Component WHERE IdComponent = '"+i+"'";
			ResultSet rs2=statement1.executeQuery(query2);
			rs2.next();
			String name=rs2.getString(2);
			BigDecimal n=rs2.getBigDecimal(3);
			String brand=rs2.getString(4);
			m=new Memory(i, name, n.doubleValue(), brand, type, frequency, lighting, size);
			list.add(m);
		}
		statement1.close();
		return list;
	}
	
	public ArrayList<Power_supplies> loadPowerSupplies() throws SQLException{
		ArrayList<Power_supplies> list=new ArrayList<Power_supplies>();
		Statement statement1=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		String query1="SELECT * FROM Power_supply";
		ResultSet rs=st.executeQuery(query1);
		while (rs.next()) {
			Power_supplies ps;
			int wattage=rs.getInt(2);
			int length=rs.getInt(3);
			String type=rs.getString(4);
			String size=rs.getString(5);
			int i=rs.getInt(1);
			String query2="SELECT * FROM Component WHERE IdComponent = '"+i+"'";
			ResultSet rs2=statement1.executeQuery(query2);
			rs2.next();
			String name=rs2.getString(2);
			BigDecimal n=rs2.getBigDecimal(3);
			String brand=rs2.getString(4);
			ps=new Power_supplies(i, name, n.doubleValue(), brand, wattage, length, type, size);
			list.add(ps);
		}
		statement1.close();
		return list;
	}
	
	public ArrayList<Graphic_Cards> loadGPUs() throws SQLException {
		ArrayList<Graphic_Cards> list=new ArrayList<Graphic_Cards>();
		Statement statement1=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		String query1="SELECT * FROM Graphics_card";
		ResultSet rs=st.executeQuery(query1);
		while (rs.next()) {
			Graphic_Cards gc;
			String lighting=rs.getString(2);
			int vram=rs.getInt(3);
			int cf=rs.getInt(4);
			int mf=rs.getInt(5);
			int length=rs.getInt(6);
			int wattage=rs.getInt(7);
			String multigpu=rs.getString(8);
			String type=rs.getString(9);
			int i=rs.getInt(1);
			String query2="SELECT * FROM Component WHERE IdComponent = '"+i+"'";
			ResultSet rs2=statement1.executeQuery(query2);
			rs2.next();
			String name=rs2.getString(2);
			BigDecimal n=rs2.getBigDecimal(3);
			String brand=rs2.getString(4);
			gc=new Graphic_Cards(i, name, n.doubleValue(), brand, lighting, vram, cf, mf, length, wattage, multigpu, type);
			list.add(gc);
		}
		statement1.close();
		return list;
	}
	
	public ArrayList<Cases> loadCases() throws SQLException {
		ArrayList<Cases> list=new ArrayList<Cases>();
		Statement statement1=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		String query="SELECT * FROM Case";
		ResultSet rs=st.executeQuery(query);
		while (rs.next()) {
			Cases cs;
			String size=rs.getString(2);
			String psuSize=rs.getString(3);
			int maxpsuL=rs.getInt(4);
			int maxgpuL=rs.getInt(5);
			int maxcpuFanHeight=rs.getInt(6);
			HashSet<String> h=new HashSet<String>();
			int i=rs.getInt(1);
			String query1="SELECT * FROM Component WHERE IdComponent = '"+i+"'";
			ResultSet rs1=statement1.executeQuery(query1);
			rs1.next();
			String name=rs1.getString(2);
			BigDecimal n=rs1.getBigDecimal(3);
			String brand=rs1.getString(4);
			cs=new Cases(i, name, n.doubleValue(), brand,size, h, psuSize,maxpsuL,maxgpuL, maxcpuFanHeight);
			list.add(cs);
		}
		statement1.close();
		return list;
	}
	
	public Cases loadCase(int id) throws SQLException {
		Cases c;
		String query = "SELECT * FROM Case JOIN Component ON Case.IdComponent = Component.IdComponent WHERE Case.IdComponent = '" + id + "'";
		ResultSet rs=st.executeQuery(query);
		if (rs.next()) {
			String name = rs.getString("Name");
			double price = rs.getBigDecimal("Price").doubleValue();
			String brand = rs.getString("BrandName");
			String size = rs.getString("Size");
			HashSet<String> motherboards = new HashSet<String>();
			Statement st2 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String query2 = "SELECT * FROM Case_supports_ff WHERE IdComponent = '" + id + "'";
			ResultSet rs2=st2.executeQuery(query2);
			while (rs2.next()) {
				motherboards.add(rs2.getString("FormFactor"));
			}
			String psu_size = rs.getString("PsuSize");
			int max_psu_length = rs.getInt("MaxPsuLength");
			int max_gpu_length = rs.getInt("MaxGpuLength");
			int max_cpu_fan_height = rs.getInt("MaxCpuFanHeight");
			c = new Cases(id, name, price, brand, size, motherboards, psu_size, max_psu_length, max_gpu_length, max_cpu_fan_height);
		} else { 
			throw(new SQLException());
		}
		return c;
	}
	
	public ArrayList<Storage> loadStorages() throws SQLException {
		ArrayList<Storage> list=new ArrayList<Storage>();
		Statement statement1=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		String query1="SELECT * FROM Storage";
		ResultSet rs=st.executeQuery(query1);
		while (rs.next()) {
			Storage st;
			String type=rs.getString(2);
			BigDecimal format=rs.getBigDecimal(3);
			int size=rs.getInt(4);
			int ts=rs.getInt(5);
			int i=rs.getInt(1);
			String query2="SELECT * FROM Component WHERE IdComponent = '"+i+"'";
			ResultSet rs2=statement1.executeQuery(query2);
			rs2.next();
			String name=rs2.getString(2);
			BigDecimal n=rs2.getBigDecimal(3);
			String brand=rs2.getString(4);
			if (type.equals("M.2")) {
				format = new BigDecimal(0);
			}
			st=new Storage(i, name, n.doubleValue(), brand, type, format.doubleValue(), size, ts);
			list.add(st);
		}
		statement1.close();
		return list;
	}
	
	public ArrayList<CPU_Cooling> loadCPU_coolers() throws SQLException {
		ArrayList<CPU_Cooling> list=new ArrayList<CPU_Cooling>();
		Statement statement1=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		String query1="SELECT * FROM CPU_cooling";
		ResultSet rs=st.executeQuery(query1);
		while (rs.next()) {
			CPU_Cooling cc;
			String lighting=rs.getString(2);
			String type=rs.getString(3);
			int airflow=rs.getInt(4);
			String socket=rs.getString(5);
			int height=rs.getInt(6);
			int i=rs.getInt(1);
			String query2="SELECT * FROM Component WHERE IdComponent = '"+i+"'";
			ResultSet rs2=statement1.executeQuery(query2);
			rs2.next();
			String name=rs2.getString(2);
			BigDecimal n=rs2.getBigDecimal(3);
			String brand=rs2.getString(4);
			cc=new CPU_Cooling(i, name, n.doubleValue(), brand, lighting,type , airflow, socket, height);
			list.add(cc);
		}
		statement1.close();
		return list;
	}
	
	public CPU_Cooling loadCPU_cooler(int id) throws SQLException {
		CPU_Cooling c;
		String query = "SELECT * FROM CPU_cooling JOIN Component ON CPU_cooling.IdComponent = Component.IdComponent WHERE CPU_cooling.IdComponent = '" + id + "'";
		ResultSet rs=st.executeQuery(query);
		if (rs.next()) {
			String name = rs.getString("Name");
			double price = rs.getBigDecimal("Price").doubleValue();
			String brand = rs.getString("BrandName");
			String lighting = rs.getString("Lighting");
			String type = rs.getString("Type");
			int airflow = rs.getInt("Airflow");
			String socket = rs.getString("Socket");
			int height = rs.getInt("Height");
			c = new CPU_Cooling(id, name, price, brand, lighting, type, airflow, socket, height);
		} else { 
			throw(new SQLException());
		}
		return c;
	}
	
	
	public String getSalt(String username) throws SQLException {
		String query="SELECT * FROM User WHERE Username = '"+username+"'";
		String s=null;
		ResultSet rs=st.executeQuery(query);
		if(rs.next()) {
			s=rs.getString(3);
		}
		else 
			return "Salt not found";
		return s;
	}
	
	public ResultSet getUser(String username, String password) throws SQLException, SecurityException {
		Statement st = writeConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		String query = "select * from User where username ='"+username+"' and password = '"+password+"'";
		ResultSet rs = st.executeQuery(query);
		if(rs.next()) {
			return rs;
		} 
		else
		{
			throw(new SecurityException());
		}
	}
	
	
	public ArrayList<Computer> getComputers(User u) throws SQLException {
		ArrayList<Computer> computers = new ArrayList<Computer>();
		Statement st = writeConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		String username = u.getUsername();
		String query = "select * from Computer where username ='"+username+"'";
		ResultSet rs = st.executeQuery(query);
		if(rs.next()) {
			int id = rs.getInt("IdComputer");
			ResultSet computerComponents = getComputerComponentResultSet(id);
			String name = rs.getString("Name");
			computers.add(new Computer(id, name, computerComponents));
		} 
		return computers;
	}
	
	/*
	 * This method returns a ResultSet containing all the IdComponents
	 * and Categories corresponding to a certain computer. The ResultSet
	 * is updatable.
	 * @param id The IdComputer of the computer
	 * */
	public ResultSet getComputerComponentResultSet(int id) throws SQLException {
		Statement st = writeConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		String query = "select * from Made_of where IdComputer = '"+id+"'";
		ResultSet rs = st.executeQuery(query);
		return rs;
	}

	public Computer createComputer(String name, User u) throws SQLException{
		String username = u.getUsername();
		String query = "INSERT INTO Computer(Name, Username) VALUES ('"+name+"', '"+username+"')";
		st.executeUpdate(query);
		query = "SELECT IdComputer FROM Computer WHERE Name = '"+name+"' and Username = '"+username+"'";
		ResultSet rs = st.executeQuery(query);
		if(rs.next()) {
			int id = rs.getInt("IdComputer");
			ResultSet computerComponents = getComputerComponentResultSet(id); 
			return new Computer(id, name, computerComponents);
		} else {
			throw(new SQLException());
		}
	}
	
	public boolean insertUser(String username, String password, String salt, String question, String answer) {
		String query="INSERT INTO User(Username, Password, Salt, Privileges, secretQuestion, secretAnswer) Values ('"+username+"', '"+password+"', '"+salt+"', '0', '"+question+"', '"+answer+"')";
		try {
			st.executeUpdate(query);
		}catch(SQLException e) {
			return false;
		}
		return true;
	}
	
	public void closeConnection() throws SQLException {
		conn.close();
		st.close();
	}
}