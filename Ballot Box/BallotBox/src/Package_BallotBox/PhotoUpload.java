package Package_BallotBox;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

/**
 * Servlet implementation class PhotoUpload
 */
@WebServlet("/PhotoUpload")
public class PhotoUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PhotoUpload() {
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

		HttpSession session = request.getSession();

		if (ServletFileUpload.isMultipartContent(request)) {
			try {
				List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
				for (FileItem item : multiparts) {
					if (!item.isFormField()) {
						String filename = new File(item.getName()).getName();
						String fileextension = (FilenameUtils.getExtension(filename)).toUpperCase();
						String name = session.getAttribute("Candidate_username")+"_"+ session.getAttribute("election_id") + "." + fileextension;
						String filepath = "C:\\" + File.separator + "Users\\" + File.separator + "pooja\\"
								+ File.separator + "Desktop\\" + File.separator + "sprint\\" + File.separator
								+ "BallotBox.zip\\" + File.separator + "BallotBox\\" + File.separator + "WebContent\\"
								+ File.separator + "img\\" + File.separator + name;
						item.write(new File(filepath));

						System.out.println(name);

						try {
							Connection conn;
							Class.forName("com.mysql.jdbc.Driver");
							conn = DriverManager.getConnection("jdbc:mysql://localhost/ballotboxdb", "root", "");
							Statement s = conn.createStatement();

							String sql = "UPDATE " + session.getAttribute("election_id") + " SET PhotoName = '" + name
									+ "' WHERE Candidate_username = '" + session.getAttribute("Candidate_username")
									+ "';";

							if (s.executeUpdate(sql) == 1) {

							}
						} catch (Exception e) {
							e.printStackTrace();
						}

						session.setAttribute("photo", "img/" + name);

						File fLocation = new File("img");
						fLocation.listFiles();

						response.sendRedirect("CandidateProfile");

					}
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}

		} else {
			System.out.println("File not Uploaded Successfully");
		}
		// request.getRequestDispatcher("/profile.jsp").forward(request,
		// response);
	}
}
