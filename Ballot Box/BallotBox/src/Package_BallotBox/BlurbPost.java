package Package_BallotBox;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.Connection;

/**
 * Servlet implementation class BlurbPost
 */
@WebServlet("/BlurbPost")
public class BlurbPost extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BlurbPost() {
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

		String title = (String) request.getParameter("title");
		String content = (String) request.getParameter("content");

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

			String sql = "CREATE TABLE IF NOT EXISTS " + table
					+ " (ID varchar(30) PRIMARY KEY, Username varchar(50), Title varchar(50), Text varchar(200), Date date)";

			Statement s = myConnection.createStatement();
			s.executeUpdate(sql);
			
			Date dt = new java.util.Date();
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			String id = ((sdf.format(dt).replace("-", "")).replace(":",
					"")).replace(" ", "");
			sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
			String datenow = sdf.format(dt);
			
			sql = "insert into "+table+" values('"+id+"','"+session.getAttribute("Candidate_username")+"','"+title+"','"+content+"','"+datenow+"');";
			
			s.executeUpdate(sql);
			
			response.sendRedirect("getBlurb");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
