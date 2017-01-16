package Package_BallotBox;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.Connection;

import Model.modelBlurb;

/**
 * Servlet implementation class getBlurb
 */
@WebServlet("/getBlurb")
public class getBlurb extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public getBlurb() {
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

		HttpSession session = request.getSession();
		if (session.isNew())
			response.sendRedirect("CandidateLogin.jsp");

		else {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				System.out.println("Driverlocated");
			} catch (ClassNotFoundException e) {
				System.out.println("Driver could NOT be located:" + e);

			}
			String url = "jdbc:mysql://localhost:3306/ballotboxdb";
			String user = "root";
			String password = "";

			Connection myConnection = null;
			try {
				myConnection = (Connection) DriverManager.getConnection(url, user, password);
				System.out.println("Successful Connection made");

				String table = session.getAttribute("election_id") + "_blurb";
				
				String sql1 = "CREATE TABLE IF NOT EXISTS " + table
						+ " (ID varchar(50) PRIMARY KEY, Username varchar(50), Title varchar(100), Text varchar(100), Date varchar(50))";

				
				String sql = "select * from " + table+ " where Username='" + session.getAttribute("Candidate_username")
				+ "'";

				Statement s = myConnection.createStatement();
				s.executeUpdate(sql1);
				ResultSet rs = s.executeQuery(sql);
				ArrayList<modelBlurb> blurb = new ArrayList<modelBlurb>();

				while (rs.next()) {

					modelBlurb mb = new modelBlurb(rs.getString("ID"), rs.getString("Username"), rs.getString("Title"),
							rs.getString("Text"), rs.getString("Date"));

					blurb.add(mb);
				}

				session.setAttribute("blurbitem", blurb);
				response.sendRedirect("Blurb.jsp");

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
}
