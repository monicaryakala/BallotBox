package Package_BallotBox;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class UpdateProfile
 */
@WebServlet("/UpdateProfile")
public class UpdateProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateProfile() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();

		String Candidate_username = request.getParameter("Candidate_username");
		String Candidate_password = request.getParameter("Candidate_password");
		String Candidate_name = request.getParameter("Candidate_name");
		String Candidate_email = request.getParameter("Candidate_email");
		String URL  = request.getParameter("WebPage");
		String election_id = (String) session.getAttribute("election_id");
		System.out.println(URL);
		
		int Candidate_id = (int) session.getAttribute("Candidate_id");
		String candidate_table;

		candidate_table = election_id;
		//candidate_table = "N_Candidate";
		try {
			Class.forName("com.mysql.jdbc.Driver");

		} catch (ClassNotFoundException ex) {
			System.out.println(ex);
		}
		// Check if HashTag Exists
		try {
			java.sql.Connection c;
			c = null;
			
			c = DriverManager.getConnection("jdbc:mysql://localhost/ballotboxdb", "root", "");
		//	c = DriverManager.getConnection("jdbc:mysql://ballotbox.c8lctglgve3o.us-west-2.rds.amazonaws.com:3316/BallotBox","team5","BallotBox");
			
			String sql1 = "select * from "+election_id+" where Candidate_username='"+Candidate_username+"';";
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(sql1);
			String pic = "defaultpic.jpg";
			while(rs.next()) {
				//pic = rs.getString("image");
				System.out.println(pic);
			}
			if(!pic.equals("defaultpic.jpg")) {
				pic = Candidate_username+"_"+election_id+".JPG";
			}
			
			/*String sql = "UPDATE " + candidate_table + " SET Candidate_username = '" + Candidate_username
					+ "',  Candidate_password = '" + Candidate_password + "', Candidate_name = '" + Candidate_name
					+ "', Candidate_email = '" + Candidate_email
					+ "', logintype = '1', PhotoName='"+pic+"' WHERE Candidate_id = " + Candidate_id + ";"; */
			String sql = "UPDATE " + candidate_table + " SET Candidate_username = '" + Candidate_username
					+ "',  Candidate_password = '" + Candidate_password + "', Candidate_name = '" + Candidate_name
					+ "', Candidate_email = '" + Candidate_email
					+ "',  blurb='"+URL+"' WHERE Candidate_id = " + Candidate_id + ";";
					
			java.sql.PreparedStatement ps = c.prepareStatement(sql);
			ps.executeUpdate();
			
			

			session.setAttribute("Candidate_username", Candidate_username);
			session.setAttribute("login_type", 1);
			session.setAttribute("Candidate_id", Candidate_id);
			session.setAttribute("election_id", election_id);

			response.sendRedirect("ShowProfile.jsp");

		} catch (SQLException ex)

		{
			System.out.println(ex);
		}

	}
}
