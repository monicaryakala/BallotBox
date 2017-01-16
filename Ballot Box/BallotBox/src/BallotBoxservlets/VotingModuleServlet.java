package BallotBoxservlets;


import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.List;
//import model.hashtag;

/**
 * Servlet implementation class VotingModuleServlet
 */
@WebServlet("/VotingModuleServlet")
public class VotingModuleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public VotingModuleServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Object value = request.getSession().getAttribute("username");
		System.out.printf("session details in another servlet:", value);
		RequestDispatcher view=request.getRequestDispatcher("VotingModule.jsp");
	    view.forward(request,response);
		// TODO Auto-generated method stub
			
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		/*
		PrintWriter out = response.getWriter();
		request.getRequestDispatcher("userlogin1.jsp").include(request, response);
		HttpSession session = request.getSession(false);
		if(session != null){
			String name = (String)session.getAttribute("username");
			out.print("Hello," + name + ". Ready to cast your vote");
			System.out.println("heloo" + name);
		}
		 */
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//get request parameter to look up if user already voted
			PrintWriter out = response.getWriter();
			Object value = request.getSession().getAttribute("username");
			
			String action = request.getParameter("action");
			System.out.println("action :" + action + ", value: " + value);
			if (value.equals(action)) {
				try{
					String[] temp = action.split("_");	
					String candTable = String.format("%s_Candidate", temp[0]);
					// Get total count of Candidates
					int count = Vote.candidateNumber(candTable);
					RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/VotingModule.jsp");
				//	RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/BeforeVote.jsp");
					request.setAttribute("result", count);
					System.out.println( count);
					rd.include((ServletRequest)request, (ServletResponse)response);
					
					// Get Blurb
					//InputStream blurb = Vote.candidateDetails(searchTable);
					//String blurb =
					//List<String> V = Vote.candidateDetails(searchTable);
					Map<String, List<String>> V = Vote.candidateDetails(candTable);
				//	List<BufferedImage>  V = Vote.candidateDetails(searchTable);
					RequestDispatcher rdImg = this.getServletContext().getRequestDispatcher("/VotingModule.jsp");
					//RequestDispatcher rdImg = this.getServletContext().getRequestDispatcher("/BeforeVote.jsp");
				//	request.setAttribute("blurb", V);
					HttpSession session = request.getSession();
					
					session.setAttribute("blurb", V);
					//rdImg.include((ServletRequest)request, (ServletResponse)response);
					rdImg.forward((ServletRequest)request, (ServletResponse)response);
							} 
				catch(Exception e){
					e.printStackTrace();
				}
			}
			else if ("Vote".equals(action)) {
					try{
						String user = (String) value;	// voter name
						String[] temp = user.split("_");	
						String votertable = String.format("%s_Voter", temp[0]);
						System.out.println("username-"+user);
						// send username to database for cross check if voter has already voted or not
						boolean vote_flag = Vote.main(user);
						if(!vote_flag){
								System.out.println("Vote is 0");					
								System.out.println("Radio: "+request.getParameter("radios")); 
								if(request.getParameter("radios") != null){
									System.out.println("Radio not  empty");
									Vote.flagUser(user,votertable);
									PrintWriter t = response.getWriter();
									//t.print("<script type=\"text/javascript\">var ok = confirm('Are you sure. If yes please press OK'); if(ok==true){ ok=window.location='userlogin3.jsp';} else {history.go(-1);}</script>");
									t.print("<script type=\"text/javascript\">var ok = confirm('Are you sure. If yes please press OK'); if(ok==true){ ok=window.location='FaceRecog.jsp';} else {history.go(-1);}</script>");
									//t.println("<script type=\"text/javascript\">" "var ok = confirm('deadbeef');");  
								//	t.println("</script>");
								//	t.println("<html><body onload=\"confirm('Are you sure?')\"></body></html>");
									
									//t.println("<html><body onload=\"alert('Your vote has been registered. Thanks')\"></body></html>");
									RequestDispatcher rmd = getServletContext().getRequestDispatcher("/home.jsp");
									rmd.include(request, response);			}
								else {
									System.out.println("Radio is empty");
									PrintWriter ot = response.getWriter();
									out.println("<font color=red> No Candidate selected !!");
								//	ot.println("<html><body onload=\"alert('No Candidate selected !!')\"></body></html>");
									RequestDispatcher reqdisp = getServletContext().getRequestDispatcher("/VotingModule.jsp");
									reqdisp.include(request, response);
								}
						}
						else{
							System.out.println("Vote is not 0");
							PrintWriter ut = response.getWriter();
							ut.println("<html><body onload=\"alert('Your vote exist')\"></body></html>");
							RequestDispatcher r = getServletContext().getRequestDispatcher("/home.jsp");
							r.include(request, response);			}
						}catch(Exception ex){
							ex.printStackTrace();
						}
			}
			}
}