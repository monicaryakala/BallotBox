package Mailer;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class UpdateEmail
 */
@WebServlet("/UpdateEmail")
public class UpdateEmail extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public UpdateEmail() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//String election_id = request.getParameter("election_id");
		HttpSession session = request.getSession(true);
		

		String election_id = (String) session.getAttribute("election_id");
		
		
		//String election_id = request.getParameter("election_id");
	    String to="";  
	    String subject= "";  
	    String content="";  
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
		
		List<CandidateEmail> candidateList = new ArrayList<CandidateEmail>();
		List<VoterEmail> voterList = new ArrayList<VoterEmail>();
		
		
		String sql = "SELECT * FROM " + candidate_table;
		java.sql.PreparedStatement ps = null;
		try {
			ps = c.prepareStatement(sql);
			result = ps.executeQuery();
										
				while (result.next()) {
					CandidateEmail candidateListPopulate = new CandidateEmail();
					candidateListPopulate.setCandidate_username(result.getString("Candidate_username"));
					candidateListPopulate.setCandidate_email(result.getString("Candidate_email"));
					candidateList.add(candidateListPopulate);
					}
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		
		sql = "SELECT * FROM " + voter_table;
		ps = null;
		try {
			ps = c.prepareStatement(sql);
			result = ps.executeQuery();
										
				while (result.next()) {
					VoterEmail voterListPopulate = new VoterEmail();
					voterListPopulate.setVoter_username(result.getString("Voter_username"));
					voterListPopulate.setVoter_email(result.getString("Voter_email"));
					voterList.add(voterListPopulate);
					}
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		request.setAttribute("candidateMailList", candidateList);
        request.setAttribute("voterMailList", voterList);
        session.setAttribute("candidateMailList", candidateList);
        session.setAttribute("voterMailList", voterList);
        RequestDispatcher RequetsDispatcherObj =request.getRequestDispatcher("/EmailUpdatePage.jsp");
	    RequetsDispatcherObj.forward(request, response);
		
    }

    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession(true);
		String election_id = (String) session.getAttribute("election_id");
		String usertype = request.getParameter("usertype");
		
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
		
		if(usertype.equalsIgnoreCase("candidate"))
		{
			String candidate_email = request.getParameter("candidate_updated_email");
			String candidate_username = request.getParameter("candidate_uname");
			String sql = "UPDATE " + candidate_table + " SET Candidate_email=? WHERE Candidate_username = ?;";
			java.sql.PreparedStatement ps = null;
			try {
				ps = c.prepareStatement(sql);
				ps.setString(1, candidate_email);
				ps.setString(2, candidate_username);
				ps.execute();
											
				
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		if(usertype.equalsIgnoreCase("voter"))
		{
			String voter_email = request.getParameter("voter_updated_email");
			String voter_username = request.getParameter("voter_uname");
			String sql = "UPDATE " + voter_table + " SET Voter_email=? WHERE Voter_username = ?;";
			java.sql.PreparedStatement ps = null;
			try {
				ps = c.prepareStatement(sql);
				ps.setString(1, voter_email);
				ps.setString(2, voter_username);
				ps.execute();
											
				
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		request.setAttribute("election_id", election_id);
		doPost(request, response);
	}

    
}
