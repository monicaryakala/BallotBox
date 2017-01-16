package Model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class connect {
	static PreparedStatement statement = null;
	private static String foundType;
	private static String result=null;
	
	public int insert(String name) throws IOException

	{
	
		Connection conn;
		Statement stmt;

		String username = "root";
		String password = "";

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();

			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ballotboxdb", username, password);

			stmt = conn.createStatement();

			// String q = "insert into hashtag(name_of_hashtag,
			// date_of_creation) values('fhgvgfc','2212-04-08)";

			String q = "insert into hashtag(name_of_hashtag) values('" + name + "')";
			int r = stmt.executeUpdate(q);
			System.out.println(r);
			return 1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}

	}

	public String checkForRecord(String name, String pass) {
		// System.out.println("inside funtion for checking record");
		// System.out.println(queryName);
		// TODO Auto-generated method stub
		Connection conn;
		Statement stmt;
	//	System.out.println(name+" "+pass);
		String username = "root";
		String password = "";

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
	//		System.out.println("here");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ballotboxdb",
					username, password);
	//		System.out.println("here1");
			stmt = conn.createStatement();

			String q = "SELECT id from masterdb WHERE election_id='" + name + "' AND Password = '" + pass + "'";
			ResultSet rs = stmt.executeQuery(q);
				if(rs.next()){
					foundType = rs.getString(1);
				System.out.println(foundType);
				result=foundType;
		}
				else{
					result = null;
				System.out.println("Didnt Get");
				}
				return result;
			
			// result=foundType;

			// System.out.println(result);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}
	
	public String checkRecord(String name) {
		// System.out.println("inside funtion for checking record");
		// System.out.println(queryName);
		// TODO Auto-generated method stub
		Connection conn;
		Statement stmt;
	//	System.out.println(name+" "+pass);
		String username = "root";
		String password = "";

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
	//		System.out.println("here");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ballotboxdb",
					username, password);
	//		System.out.println("here1");
			stmt = conn.createStatement();

			String q = "SELECT id from masterdb WHERE election_id='" + name + "'";
			ResultSet rs = stmt.executeQuery(q);
				if(rs.next()){
					foundType = rs.getString(1);
				System.out.println(foundType);
				result=foundType;
		}
				else{
					result = null;
				System.out.println("Didnt Get");
				}
				return result;
			
			// result=foundType;

			// System.out.println(result);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public List<String> getContentFromHashTagFeed(String checkName) {
		// TODO Auto-generated method stub
		Connection conn;
		Statement stmt;
			List<String> temp= new ArrayList<String>();
		String username = "root";
		String password = "";
			
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();

			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab01?autoReconnect=true&useSSL=false",
					username, password);

			stmt = conn.createStatement();

			String q = "SELECT * from hashtagfeed WHERE hashtag='" + checkName + "' ";
			ResultSet rs = stmt.executeQuery(q);
				while(rs.next()) {
					temp.add(rs.getString("content"));
				}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return temp;
		}
	
	public String getpass(String email) 
	{
		
		Connection conn;
		Statement stmt;
	//	System.out.println(name+" "+pass);
		String username = "root";
		String password = "";

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
	//		System.out.println("here");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ballotboxdb",
					username, password);
	//		System.out.println("here1");
			stmt = conn.createStatement();

			String q = "SELECT Password from masterdb WHERE election_id='" + email + "'";
			ResultSet rs = stmt.executeQuery(q);
			rs.next();
			String res = rs.getString(1);
			
			return res;
			
	}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
}
	public void updateRecord(String email, String pass) 
	{
		
		Connection conn;
		Statement stmt;
	//	System.out.println(name+" "+pass);
		String username = "root";
		String password = "";

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
	//		System.out.println("here");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ballotboxdb",
					username, password);
	//		System.out.println("here1");
			stmt = conn.createStatement();

			String q = "SELECT Password from masterdb WHERE election_id='" + email + "'";
			ResultSet rs = stmt.executeQuery(q);
			rs.next();
			String res = rs.getString(1);
			String p = "UPDATE masterdb SET password = '"+ pass +"' WHERE election_id='" + email + "' ";
			stmt.executeUpdate(p);
			//return res;
			
	}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//return null;
		}
		
		
}
	
}
