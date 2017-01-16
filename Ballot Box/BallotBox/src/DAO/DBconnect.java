package DAO;
import java.sql.Connection;
import java.sql.DriverManager;
public class DBconnect {
	
		int getConnection;
		Connection con;
		public Connection getConnection()
		{
		String s="root";
		String pwd="";
		String url="jdbc:mysql://localhost:3306/ballotboxdb";
		String driver="com.mysql.jdbc.Driver";
		try {
			Class.forName(driver);
		
	    con=DriverManager.getConnection(url,s,pwd);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
		}

	}

	 

