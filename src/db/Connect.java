package db;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;
import users.User;
import component.*;
public class Connect {
	private final String root="computerPartsReader";
	private final String pw="w64YyHswZ36xE8J8";
	private final String url="jdbc:mysql://ggh.zapto.org:3306/computerParts?useSSL=false";
	private final String driver="com.mysql.jdbc.Driver";
	private Connection conn;
	private Statement st;
	
	public Connect() throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		conn=DriverManager.getConnection(url,root,pw);
		st=conn.createStatement(ResultSet.CONCUR_READ_ONLY, ResultSet.TYPE_SCROLL_INSENSITIVE);
		st.setQueryTimeout(30);
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
			cpu=new CPU(name, n.doubleValue(), brand, fr, cores, socket, w);
			list.add(cpu);
		}
		statement1.close();
		return list;
	}
	
	public ArrayList<Motherboards> loadMotherBoards() throws SQLException {
		ArrayList<Motherboards> list=new ArrayList<Motherboards>();
		Statement statement1=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		String query1="SELECT * FROM Motherboard";
		ResultSet rs=st.executeQuery(query1);
		while (rs.next()) {
			Motherboards mb;
			String lighting=rs.getString(2);
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
			mb=new Motherboards(name, n.doubleValue(), brand, lighting, chipset, socket, ramType, ramSlots, size, ramSpeed, multiGPU, maxMemory, maxM2);
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
			int i=rs.getInt(1);
			String query2="SELECT * FROM Component WHERE IdComponent = '"+i+"'";
			ResultSet rs2=statement1.executeQuery(query2);
			rs2.next();
			String name=rs2.getString(2);
			BigDecimal n=rs2.getBigDecimal(3);
			String brand=rs2.getString(4);
			m=new Memory(name, n.doubleValue(), brand, type, frequency, lighting, size);
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
			ps=new Power_supplies(name, n.doubleValue(), brand, wattage, length, type, size);
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
			gc=new Graphic_Cards(name, n.doubleValue(), brand, lighting, vram, cf, mf, length, wattage, multigpu, type);
			list.add(gc);
		}
		statement1.close();
		return list;
	}
	
	public ArrayList<Cases> loadCases() throws SQLException {
		ArrayList<Cases> list=new ArrayList<Cases>();
		Statement statement1=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		Statement statement2=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
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
			String query2="SELECT * FROM Made_of WHERE IdComponent='"+i+"'";
			ResultSet rs2=statement2.executeQuery(query2);
			while(rs2.next()) {
				h.add(rs2.getString(2));
			}
			String query1="SELECT * FROM Component WHERE IdComponent = '"+i+"'";
			ResultSet rs1=statement1.executeQuery(query1);
			rs1.next();
			String name=rs1.getString(2);
			BigDecimal n=rs1.getBigDecimal(3);
			String brand=rs1.getString(4);
			cs=new Cases(name, n.doubleValue(), brand,size, h, psuSize,maxpsuL,maxgpuL, maxcpuFanHeight);
			list.add(cs);
		}
		statement1.close();
		return list;
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
			st=new Storage(name, n.doubleValue(), brand, type, format.doubleValue(), size, ts);
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
			cc=new CPU_Cooling(name, n.doubleValue(), brand, lighting,type , airflow, socket, height);
			list.add(cc);
		}
		statement1.close();
		return list;
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
	
	public boolean hasUser(String username, String password) {
		String query="select count(*) as utenti from User where username ='"+username+"' and password = '"+password+"'";
		int i=0;
		try{
			ResultSet rs=st.executeQuery(query);
			rs.next();
			i=rs.getInt(1);
		}catch(SQLException e) {
			return false;
		}
		if(i==1)
			return true;
		else
			return false;
	}
	
	public void closeConnection() throws SQLException {
		conn.close();
		st.close();
	}
}