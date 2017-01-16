package BallotBoxservlets;

import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

public class Vote {

	public static boolean main(String user) {
		Connection con = null;
		ResultSet rs = null;
		Statement stmt = null;
		boolean vote_flag = false;
		String uname = user;
		/***** COMMENT THIS LINES OF CODES WHEN CONNECTING TO OTHER DB  *****/
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		//	con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ballotboxdb?user=root&password=root");
			con = DriverManager.getConnection("jdbc:mysql://ballotbox.c8lctglgve3o.us-west-2.rds.amazonaws.com:3316/BallotBox?user=team5&password=BallotBox");
			stmt = con.createStatement();
			
			String query = String.format("SELECT Voter_username,voted_flag FROM new_Voter WHERE Voter_username = \"%s\" ;",uname);
			//String query = String.format("SELECT Voter_username,voted_flag FROM Ualbany_Voter WHERE Voter_username = \"%s\" ;",uname);
			rs = stmt.executeQuery(query);
			while(rs.next()){
				if(uname.equalsIgnoreCase(rs.getString("Voter_username"))){
		    		vote_flag = rs.getBoolean("voted_flag");
		    		
		    	}
			}
			
						//vote_flag = rs.getString("voted_flag");
		//	System.out.println(rs.getString("Voter_id"));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		 finally {
				try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
			//	try { if (con != null) con.close(); } catch (SQLException e) { e.printStackTrace(); }
			}
		return vote_flag;
	}
	
		public static Connection connectToDB(){
			Connection conn = null;
			try{
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				
				String connectionUrl = "jdbc:mysql://localhost:3306/ballotboxdb";
				String connectionUser = "root";
				String connectionPassword = "";
				
			/*	String connectionUrl = "jdbc:mysql://ballotbox.c8lctglgve3o.us-west-2.rds.amazonaws.com:3316/BallotBox";
				String connectionUser = "team5";
				String connectionPassword = "BallotBox";*/
				
				conn = DriverManager.getConnection(connectionUrl, connectionUser, connectionPassword);
			}
			 catch (Exception e) {
					e.printStackTrace();
				} 
			return conn;
		}
		
		public static void flagUser(String uname, String vtable){
			try {
				Statement st = null;
				Connection conn = connectToDB();
				st = conn.createStatement();
				String V_table = vtable;
				System.out.println("Flagging user in table: " + uname);
				String sql = String.format("UPDATE %s set voted_flag = 1 where Voter_username = \"%s\";",V_table,uname );
				System.out.println(sql);
				//String sql = String.format("UPDATE Ualbany_Voter set voted_flag = 1 where Voter_username = \"%s\";",uname );
				st.executeUpdate(sql);
				
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		
		
		public static int candidateNumber(String searchTable){
			int count = 0;
			try {
				Statement st = null;
				Connection conn = connectToDB();
				st = conn.createStatement();
				PreparedStatement myStatement = null;
				String table = searchTable;
				String sql = String.format("SELECT count(*) from %s;" ,table );
				ResultSet rs = st.executeQuery(sql);
			/*
				String detailsTable = String.format("SELECT image from %s;" ,table );
				ResultSet rset = st.executeQuery(detailsTable);
				*/
				while(rs.next()){
					count = Integer.parseInt(rs.getString(1));
				}
			/*
				int i =0;
				while(rset.next()){
					InputStream in = rset.getBinaryStream(1);
					OutputStream f = new FileOutputStream(new File("test"+i+".jpg"));
					i++;
					int c = 0;
					while ((c = in.read()) > -1) {
						f.write(c);
					}
					f.close();
					in.close();
				}
				*/
			} catch (Exception e) {
				e.printStackTrace();
			} 
			return count;
		}
		
		//public static InputStream candidateDetails(String searchTable) throws 
		//public static List<String> candidateDetails(String searchTable) throws 
		public static Map<String, List<String>> candidateDetails(String searchTable) throws
		//public static List<BufferedImage> candidateDetails(String searchTable) throws 
		IllegalArgumentException, SQLException, ClassNotFoundException {
			//InputStream blurbBin = null;
			String cn = null;
			// create map to store
	        Map<String, List<String>> map = new HashMap<String, List<String>>();
	     	        
			//List<BufferedImage> images = new ArrayList<BufferedImage>();
			try {
				Statement st = null;
				Connection conn = connectToDB();
				st = conn.createStatement();
				PreparedStatement myStatement = null;
				String table = searchTable;
				//String dtsql = String.format("SELECT Candidate_username,image,blurb from %s;" ,table );
				String dtsql = String.format("SELECT Candidate_username,Candidate_name,blurb from %s;" ,table );
				ResultSet rset = st.executeQuery(dtsql);
				int i =0;
				
		        while(rset.next()){
		        	List<String> cname = new ArrayList<String>();
		        //	valSetOne.add(rset.getString(1));
		        //	valSetTwo.add(rset.getString(2));
		        	cname.add(rset.getString(1));
		        	cname.add(rset.getString(2));
		        	cname.add(rset.getString(3));
		        	System.out.println(rset.getString(3));
		        	map.put(String.valueOf(i), cname);
		        	i++;
				//	images.add(ImageIO.read(new File(rset.getString(1))));
					
					//blurbBin = rset.getBinaryStream(1);
				/*	OutputStream f = new FileOutputStream(new File("test"+i+".jpg"));
					i++;
					int c = 0;
					while ((c = in.read()) > -1) {
						f.write(c);
					}
					f.close();
					in.close();
					*/
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
			for (Map.Entry<String, List<String>> entry : map.entrySet()) {
	            String key = entry.getKey();
	            List<String> values = entry.getValue();
	           // System.out.println("Key = " + key);
	           // System.out.println("Values = " + values + "n");
	        }
			// put values into map
			//map.put("CandidateName", valSetOne);
		//	map.put("imageURL", valSetTwo);
			//return blurbBin;
			return map;
		//	return images;
		}

		//public static String getTimeFromMaster(String tableName){
	/*	public static String getTimeFromMaster(String tableName){
			try {
				Statement st = null;
				Connection conn = connectToDB();
				st = conn.createStatement();
				PreparedStatement myStatement = null;
				String table = tableName;
				String query = String.format("select * from masterdb where election_id =\"%s\";", table);
				System.out.println(query);
				ResultSet rs = st.executeQuery(query);
				
			//	DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
			//	Date dateobj = new Date();
		//		System.out.println(df.format(dateobj));
				while(rs.next()){
					System.out.println(rs.getString(4));
				}
				System.out.println("result set:"+rs);
		}catch (Exception e) {
			e.printStackTrace();
		} 
			return "none";
	}*/
		/**************END OF LINEs OF CODE*************/
		

		/*******************BEGIN DB Connection MANAGEMENT*********************/
	 /*   try {
	    	Class.forName("com.mysql.jdbc.Driver");
	    	System.out.println("Driverlocated");
	        String url="jdbc:mysql://localhost:3306/ballotboxdb";
	    	String user="root";
	    	String password="jense1ts";
	    	Connection myConnection=null;
	    	
	    try {
	    	myConnection=(Connection)DriverManager.getConnection(url,user,password);
	    	System.out.println("Successful Connection made");
	    	stmt = con.createStatement();
			rs = stmt.executeQuery(String.format("SELECT voted_flag FROM ualbany_voter WHERE username = \"%s\" );",username));
			c = rs.getString("voted_flag");
			System.out.println(c);
	    } catch(SQLException e){
	    	System.out.println("Connection did not work");
	    } */
	/*******************END DB Connection MANAGEMENT **************************/

}