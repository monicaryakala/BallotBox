package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Model.connect;

/**
 * Servlet implementation class OrganiserServlet
 */
@WebServlet("/OrganiserServlet")
public class OrganiserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrganiserServlet() {
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
		
		HttpSession session = request.getSession(true);
		String name=request.getParameter("username");
		String password = request.getParameter("password");

		connect abc = new connect();
		String validation = abc.checkForRecord(name,password);
		if (validation!=null)
		{
			session.setAttribute("election_id", name);
			response.sendRedirect("Administration_Homepage.jsp");
			//getServletContext().getRequestDispatcher("/Administration_Homepage.jsp").forward(request, response);
		}
		
		else
			
			
		{
			request.setAttribute("error","Invalid Credentials.");
			getServletContext().getRequestDispatcher("/AdminLogin.jsp").forward(request, response);
			System.out.println("Invalid Credentials");
			
		}
		
		
		
		// TODO Auto-generated method stub
	}

}
