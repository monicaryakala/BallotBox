package Mailer;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class CalculateResult
 */
@WebServlet("/CalculateResult")
public class CalculateResult extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CalculateResult() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        HttpSession session = request.getSession(true);
		
		String election_id = (String) session.getAttribute("election_id");
		//String election_id = "suny";
		
		//java.util.Date date = new java.util.Date();
		//java.sql.Date sqlDate = new java.sql.Date(date.getTime()); 
		//System.out.println(sqlDate);
		Date date= new Date();
        //getTime() returns current time in milliseconds
	 long time = date.getTime();
        //Passed the milliseconds to constructor of Timestamp class 
	 Timestamp ts = new Timestamp(time);
	 Timestamp end_date=null;
		
		String[] candidate_list;
		int[] no_of_votes;
		String[] candidate_username_list;
		int candidateCount=0;
		
		String candidate_table;
		String voter_table;
		
	    candidate_table = election_id;
		candidate_table = candidate_table.concat("_Candidate");
		voter_table = election_id;
		voter_table = voter_table.concat("_Voter");
    	
		try {
			Class.forName("com.mysql.jdbc.Driver");

		} catch (ClassNotFoundException ex) {
			System.out.println(ex);
		}
		
		java.sql.Connection c;
		c = null;
		ResultSet result = null;
		try {
			c = DriverManager.getConnection("jdbc:mysql://localhost/ballotboxdb?autoReconnect=true&useSSL=false", "root", "");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String sql = "SELECT * FROM  masterdb WHERE election_id= ?";
		java.sql.PreparedStatement ps = null;
		try {
			ps = c.prepareStatement(sql);
			ps.setString(1, election_id);
			result = ps.executeQuery();
										
				while (result.next()) {
					end_date= result.getTimestamp("end_time");
					}
		//System.out.println(end_date);
		if(end_date.getTime() - ts.getTime()>0)
		{
			session.setAttribute("time_left", "1");
			
			RequestDispatcher RequetsDispatcherObj = request.getRequestDispatcher("/Admistration_Homepage.jsp");
			RequetsDispatcherObj.forward(request, response);
			
		}
		sql = "SELECT * FROM " + candidate_table;
		ps = null;
		
			ps = c.prepareStatement(sql);
			result = ps.executeQuery();
										
				while (result.next()) {
					candidateCount++;
					}
				candidate_list = new String[candidateCount];
				no_of_votes = new int[candidateCount];
				candidate_username_list= new String[candidateCount];
				
				sql = "SELECT * FROM " + candidate_table;
				ps = null;				
					ps = c.prepareStatement(sql);
					result = ps.executeQuery();
					int i=0;				
						while (result.next()) {
							candidate_list[i] = result.getString("Candidate_name");
							no_of_votes[i] = result.getInt("Total_votes");
							candidate_username_list[i] = result.getString("Candidate_username");
							  i++;
							}
						
						int max = 0;
						for(int j=0; j<candidateCount; j++)
						{
							if (no_of_votes[j] > no_of_votes[max]) {
							      max = j;
							    }
						}
						
						request.setAttribute("candidate_username", candidate_username_list[max]);
						request.setAttribute("candidate_name", candidate_list[max]);
				        RequestDispatcher RequetsDispatcherObj =request.getRequestDispatcher("/ResultPage.jsp");
					    RequetsDispatcherObj.forward(request, response);
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

}
