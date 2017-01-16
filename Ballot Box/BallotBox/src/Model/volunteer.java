package Model;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DAO.DBconnect;

public class volunteer {
	private String name;
	private String email;
	private String phonenumber;
	private String comments;
	private String ElectionID;
	
	
	
	public String getElectionID() {
		return ElectionID;
	}
	public void setElectionID(String electionID) {
		ElectionID = electionID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public  void volunter(){
		Connection c;
		DBconnect db=new DBconnect();
		c=db.getConnection();
		//System.out.println("hey");
		try{
			PreparedStatement p=c.prepareStatement("insert into volunteertable values(?,?,?,?,?)");
			p.setString(1, name);
			p.setString(2, email);
			p.setString(3, phonenumber);
			p.setString(4, comments);
			p.setString(5, ElectionID);
		    p.executeUpdate();
		    //System.out.println(p);
		    //System.out.println(p);
		    }
		catch(Exception e){
			e.printStackTrace();
        }finally{
		                   try {
							c.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	
	}
	}
	public ArrayList<String[]> viewvolunteerlist(){
		Connection c;
		DBconnect db=new DBconnect();
		c=db.getConnection();
		ArrayList<String[]> a=new ArrayList<String[]>();
		try{
			Statement s=c.createStatement();
			ResultSet r=s.executeQuery("select Email,Phonenumber,ElectionID,name from volunteertable");
			while(r.next())
			{
				String[] s1=new String[4];
				s1[0]=r.getString("name");
				s1[1]=r.getString("ElectionID");
				s1[2]=r.getString("Phonenumber");
				s1[3]=r.getString("Email");
				a.add(s1);
				
			}
		}
	catch(Exception e){
		e.printStackTrace();
		
	}finally{
		try{
			c.close();
		}catch (SQLException e){
			e.printStackTrace();
		}
	}
	
return a;
}
}
