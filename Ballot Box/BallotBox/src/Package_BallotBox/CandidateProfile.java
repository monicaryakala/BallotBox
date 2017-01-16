package Package_BallotBox;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class CandidateProfile
 */
@WebServlet("/CandidateProfile")
public class CandidateProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CandidateProfile() {
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
		HttpSession session = request.getSession();
		String Candidate_username = (String) session.getAttribute("Candidate_username");
		String election_id = (String) session.getAttribute("election_id");
		int Candidate_id = (int) session.getAttribute("Candidate_id");

		try {
			Class.forName("com.mysql.jdbc.Driver");
			java.sql.Connection c;
			c = null;
			ResultSet result = null;
			c = DriverManager.getConnection("jdbc:mysql://localhost/ballotboxdb", "root", "");
			String sql = "select * from " + election_id + " where Candidate_id=" + Candidate_id;

			java.sql.PreparedStatement ps = c.prepareStatement(sql);
			result = ps.executeQuery();

			String Candidate_name = "null";
			String Candidate_email = "null";

			while (result.next()) {
				// Candidate_password = result.getString("Candidate_password");
				Candidate_email = result.getString("Candidate_email");
				Candidate_name = result.getString("Candidate_name");
			}

			session.setAttribute("Candidate_name", Candidate_name);
			session.setAttribute("Candidate_email", Candidate_email);

			String picname = Candidate_username + "_" + election_id + ".JPG";
			session.setAttribute("Candidate_photo", picname);

			response.sendRedirect("ShowProfile.jsp");

		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);

	}

}
