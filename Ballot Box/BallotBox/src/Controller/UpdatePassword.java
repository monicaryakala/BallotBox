package Controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Model.connect;

/**
 * Servlet implementation class OrganiserSer
 */
@WebServlet("/UpdatePassword")
public class UpdatePassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdatePassword() {
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
		

		String email = request.getParameter("name");
		String pass = request.getParameter("pass");
		connect abc= new connect();
		System.out.println(email);
		System.out.println(pass);
		String valid = abc.checkRecord(email);
		if(valid!=null)
		{
			String res = abc.getpass(email);
			System.out.println(res);
			abc.updateRecord(email, pass);	
			
			response.sendRedirect("PasswordChanged.jsp");
		}		
		else
			
			
		{
			request.setAttribute("error","Invalid Credentials.");
			getServletContext().getRequestDispatcher("/userlogin1.jsp").forward(request, response);
			
		System.out.println("Invalid Credentials");
			
		}
		
	
		
		
		
	}
	
	

}
