package Package_BallotBox;

import java.io.IOException;
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
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
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
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String Candidate_username = request.getParameter("Candidate_username");
		String Candidate_password = request.getParameter("Candidate_password");
		String election_id = request.getParameter("election_id");
		election_id = election_id.concat("_Candidate");

		try {
			Class.forName("com.mysql.jdbc.Driver");

		} catch (ClassNotFoundException ex) {
			System.out.println(ex);
		}
		// Check if HashTag Exists
		try {
			java.sql.Connection c;
			c = null;
			ResultSet result = null;
			c = DriverManager.getConnection("jdbc:mysql://localhost/ballotboxdb", "root", "");
			String sql = "SELECT * FROM " + election_id + " WHERE Candidate_username = ?";
			java.sql.PreparedStatement ps;
			ps = c.prepareStatement(sql);
			ps.setString(1, Candidate_username);
			result = ps.executeQuery();
			if (result.next()) // HashTag Exists
			{
				String get_Candidate_username = result.getString("Candidate_username");
				String get_Candidate_password = result.getString("Candidate_password");
				if (Candidate_password.equals(get_Candidate_password)
						&& Candidate_username.equals(get_Candidate_username)) {
					System.out.println("Inside candi");
					if (result.getInt("logintype") == 0) {
						HttpSession session = request.getSession();
						session.setAttribute("Candidate_username", Candidate_username);
						session.setAttribute("login_type", 0);
						session.setAttribute("Candidate_id", result.getInt("Candidate_id"));
						session.setAttribute("election_id", election_id);
						response.sendRedirect("FirstLogin.jsp");
					} else {

						HttpSession session = request.getSession();
						session.setAttribute("login_type", 1);
						session.setAttribute("election_id", election_id);
						session.setAttribute("Candidate_username", Candidate_username);
						session.setAttribute("Candidate_id", result.getInt("Candidate_id"));
						response.sendRedirect("CandidateIndex.jsp");
					}
				} else {
					HttpSession session = request.getSession();
					session.setAttribute("login details not matched", "1");

					RequestDispatcher RequetsDispatcherObj = request.getRequestDispatcher("/Login.jsp");
					RequetsDispatcherObj.forward(request, response);
				}

			} else // HashTag Doesn't Exist
			{

				HttpSession session = request.getSession();
				session.setAttribute("login details not matched", "1");

				RequestDispatcher RequetsDispatcherObj = request.getRequestDispatcher("/Login.jsp");
				RequetsDispatcherObj.forward(request, response);

			}

			c.close(); // Closing db connection

		} catch (

		SQLException ex)

		{
			System.out.println(ex);
		}

	}

}
