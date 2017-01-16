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
 * Servlet implementation class VideoPost
 */
@WebServlet("/VideoPost")
public class VideoPost extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VideoPost() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();

		String url = (String) request.getParameter("url");

		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driverlocated");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver could NOT be located:" + e);

		}
		String url1 = "jdbc:mysql://localhost:3306/ballotboxdb";
		String user = "root";
		String password = "";

		Connection myConnection = null;
		try {
			myConnection = (Connection) DriverManager.getConnection(url1, user, password);
			System.out.println("Successful Connection made");

			String table = session.getAttribute("election_id") + "_video";

			String sql = "CREATE TABLE IF NOT EXISTS " + table
					+ " (url varchar(50) PRIMARY KEY, Username varchar(50))";

			Statement s = myConnection.createStatement();
			s.executeUpdate(sql);
			
			sql = "insert into "+table+" values('"+url+"','"+session.getAttribute("Candidate_username")+"');";
			
			s.executeUpdate(sql);
			
			response.sendRedirect("getVideo");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
