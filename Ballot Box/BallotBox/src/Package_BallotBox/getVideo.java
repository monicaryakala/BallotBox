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

/**
 * Servlet implementation class getVideo
 */
@WebServlet("/getVideo")
public class getVideo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public getVideo() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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

				String table = session.getAttribute("election_id") + "_video";

				String sql1 = "CREATE TABLE IF NOT EXISTS " + table
						+ " (url varchar(50) PRIMARY KEY, Username varchar(50))";

				Statement s1 = myConnection.createStatement();
				s1.executeUpdate(sql1);
				String sql = "select * from " + table + " where Username='" + session.getAttribute("Candidate_username")
						+ "'";
				

				Statement s = myConnection.createStatement();

				ResultSet rs = s.executeQuery(sql);
				ArrayList<String> video = new ArrayList<String>();

				while (rs.next()) {

					video.add(rs.getString("url"));
				}

				session.setAttribute("videoitem", video);

				response.sendRedirect("Video.jsp");

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}