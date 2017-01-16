package Mailer;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

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
@WebServlet("/NotifyUsers")
public class NotifyUsers extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public NotifyUsers() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
    	
    	
       HttpSession session = request.getSession(true);
		

        String election_id = (String) session.getAttribute("election_id");
				
    	
    	//String election_id = request.getParameter("election_id");
	    String to="";  
	    String subject= "";  
	    String content="";  
	    String candidate_table;
		String voter_table;
		
	    candidate_table = election_id;
		candidate_table = candidate_table.concat("_candidate");
		voter_table = election_id;
		voter_table = voter_table.concat("_voter");
    	
		try {
			Class.forName("com.mysql.jdbc.Driver");

		} catch (ClassNotFoundException ex) {
			System.out.println(ex);
		}
		
		java.sql.Connection c;
		c = null;
		ResultSet result = null;
		try {
			c = DriverManager.getConnection("jdbc:mysql://localhost/ballotboxdb", "root", "");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String sql = "SELECT * FROM " + candidate_table ;
		java.sql.PreparedStatement ps = null;
		try {
			ps = c.prepareStatement(sql);
			result = ps.executeQuery();
		
		if (result.next()) 
		{
			String username= result.getString("Candidate_username");
			String password= result.getString("Candidate_password");
			
			subject= "BallotBox Voting";  
		    content="Welcome to BallotBox. Kindly Login with ur credentials. As follows:"
		    		+ "UserId-" +username+
		    		 "Password-" +password+"/n"
		    		 		+ "Follow the link: http://www.ballotbox.com?param1="+election_id;
			String get_Candidate_mailID = result.getString("Candidate_email");
			SendEmail.sendEmail("smtp.gmail.com", "587", "ballotboxSEteam5@gmail.com", "ballotbox@team5", get_Candidate_mailID, subject, content);
			
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		sql = "SELECT * FROM " + voter_table;
		ps = null;
		try {
			System.out.println(sql);
			ps = c.prepareStatement(sql);
			result = ps.executeQuery();
		
		if (result.next()) 
		{
			String username= result.getString("Voter_username");
			String password= result.getString("Voter_password");
			
			subject= "BallotBox Voting";  
		    content="Welcome to BallotBox. Kindly Login with ur credentials. As follows:"
		    		+ "UserId-" +username+
		    		 "Password-" +password+"/n"
			    		 		+ "Follow the link: http://www.ballotbox.com?param1="+election_id;
			String get_Voter_mailID = result.getString("Voter_email");
			SendEmail.sendEmail("smtp.gmail.com", "587", "ballotboxSEteam5@gmail.com", "ballotbox@team5", get_Voter_mailID, subject, content);
			
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		RequestDispatcher RequetsDispatcherObj =request.getRequestDispatcher("/Administration_Homepage.jsp");
	    RequetsDispatcherObj.forward(request, response);
		
    }
}
