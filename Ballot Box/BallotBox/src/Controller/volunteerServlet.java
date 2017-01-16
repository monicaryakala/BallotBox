package Controller;

import java.io.IOException;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Model.volunteer;

/**
 * Servlet implementation class volunteerServlet
 */
@WebServlet("/volunteerServlet")
public class volunteerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public volunteerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action=request.getParameter("action");
		System.out.println(action);
	       if(action.equals("volunter"))   {
	       String s1=request.getParameter("name");
	       String s2=request.getParameter("email");
	       String s3=request.getParameter("phonenumber");
	       String s4=request.getParameter("comments");
	       String s5=request.getParameter("ElectionID");
	       volunteer v= new volunteer();
	    	HttpSession session = request.getSession();
		    String ElectionID=(String)session.getAttribute("ElectionID");
	       v.setName(s1);
	       v.setEmail(s2);
	       v.setPhonenumber(s3);
	       v.setComments(s4);
	       v.setElectionID(s5);
	       v.volunter();
	     System.out.println(s1);
	     System.out.println(s2);
	     System.out.println(s3);
	     System.out.println(s4);
	     System.out.println(s5);
	       }
	       if(action.equals("viewvolunteerlist")){
	    	   volunteer v=new volunteer();
	    	   ArrayList<String[]> a=v.viewvolunteerlist();
	    	   //System.out.println(a);
	    	   request.setAttribute("volunteerlist", a);
	    	   System.out.println(a);
	    	   RequestDispatcher rd=request.getRequestDispatcher("viewvolunteerlist.jsp");
	       rd.forward(request,response);
	       }
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
