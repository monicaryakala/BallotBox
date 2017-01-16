package Mailer;

import java.io.IOException;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String election_id = request.getParameter("election_id");
		String election_pwd = request.getParameter("election_pwd");
		String candidate_no_str = request.getParameter("candidate_no");
		String voter_no_str = request.getParameter("voter_no");
		
		
		
		
		
		
		
		String start_month = request.getParameter("start_month");
		String start_day = request.getParameter("start_day");
		String start_year = request.getParameter("start_year");
		String start_hours = request.getParameter("start_hours");
		String start_minutes = request.getParameter("start_minutes");
		String start_period = request.getParameter("start_period");
		
		
		String end_month = request.getParameter("end_month");
		String end_day = request.getParameter("end_day");
		String end_year = request.getParameter("end_year");
		String end_hours = request.getParameter("end_hours");
		String end_minutes = request.getParameter("end_minutes");
		String end_period = request.getParameter("end_period");
		
		
		
		
		if(start_hours.length()<2)
		{
			start_hours= "0" + start_hours;
		}
		
		if(start_minutes.length()<2)
		{
			start_minutes= "0" + start_minutes;
		}
		
		if(end_hours.length()<2)
		{
			end_hours= "0" + end_hours;
		}
		
		if(end_minutes.length()<2)
		{
			end_minutes= "0" + end_minutes;
		}
		
		
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");
		java.sql.Timestamp TS_startdate = null;
		java.sql.Timestamp TS_enddate= null;
		
		String final_start_date= start_day + "/" + start_month + "/" + start_year+ " " + start_hours + ":" 
				+ start_minutes + ":" + "00" + " " + start_period;
		
		java.sql.Date sqlStartDate;
		java.util.Date utilStartDate;
		//java.sql.Date sqlStartDate
		try {
			//java.sql.Date sDate = new java.sql.Date(date.getTime());
			utilStartDate = (java.util.Date) formatter.parse(final_start_date);	
            //java.sql.Date sqlStartDate = new java.sql.Date(date.getTime());
			//java.util.Date utilStartDate = jDateChooserStart.getDate();
			sqlStartDate = new java.sql.Date(utilStartDate.getTime());
	        TS_startdate = new java.sql.Timestamp(sqlStartDate.getTime());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String final_end_date= end_day + "/" + end_month + "/" + end_year+ " " + end_hours + ":" 
				+ end_minutes + ":" + "00" + " " + end_period;

		java.sql.Date sqlEndDate;
		java.util.Date utilEndDate;
		try {
			utilEndDate = (java.util.Date) formatter.parse(final_end_date);
			sqlEndDate = new java.sql.Date(utilEndDate.getTime());
			TS_enddate = new java.sql.Timestamp(sqlEndDate.getTime());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//System.out.println(final_start_date);
		//System.out.println(final_end_date);
		
		int candidate_no = Integer.parseInt(candidate_no_str);
		int voter_no = Integer.parseInt(voter_no_str);
		
		ResultSet result = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");

		} catch (ClassNotFoundException ex) {
			System.out.println(ex);
		}
		// Check if HashTag Exists
		try {
			
			/*
			java.sql.Connection c;
			c = null;
			ResultSet result = null;
			c = DriverManager.getConnection("jdbc:mysql://localhost:3306/ballotboxdb?useSSL=false", "root", "komal123");
			*/
			
			
			
			
			java.sql.Connection c;
			c = null;			
			try {
				c = DriverManager.getConnection("jdbc:mysql://localhost/ballotboxdb?autoReconnect=true&useSSL=false", "root", "");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
			
			
			String sql = "SELECT * FROM masterdb WHERE UPPER(election_id) = UPPER(?)";
			java.sql.PreparedStatement ps;
			ps = c.prepareStatement(sql);
			ps.setString(1, election_id);
			result = ps.executeQuery();
			if (result.next()) // HashTag Exists
			{
				HttpSession session = request.getSession();
				session.setAttribute("election_id_exists", "1");

				RequestDispatcher RequetsDispatcherObj = request.getRequestDispatcher("/RegisterElection.jsp");
				RequetsDispatcherObj.forward(request, response);
			} else // HashTag Doesn't Exist
			{
				String candidate_table;
				String voter_table;

				candidate_table = election_id;
				candidate_table = candidate_table.concat("_Candidate");
				voter_table = election_id;
				voter_table = voter_table.concat("_Voter");

				sql = "insert into masterdb(election_id, password, start_time, end_time) values(?, ?, ?, ?);";
				ps = c.prepareStatement(sql);
				ps.setString(1, election_id);
				ps.setString(2, election_pwd);
				ps.setTimestamp(3, TS_startdate);
				ps.setTimestamp(4, TS_enddate);
				ps.execute();

				sql = "create table " + candidate_table
						+ " (Candidate_id int AUTO_INCREMENT Primary key, Candidate_username varchar(20) NOT NULL, Candidate_password varchar(20), Candidate_name varchar(100), Candidate_email varchar(40), Total_votes int, logintype int not null default 0, blurb varchar(40));";ps = c.prepareStatement(sql);
				ps.execute();
				
				sql = "create table " + voter_table
						+ " (Voter_id int AUTO_INCREMENT Primary key, Voter_username varchar(20) NOT NULL, Voter_password varchar(20), Voter_email varchar(40), Voter_image varchar(100), voted_flag boolean not null default 0);";
				ps = c.prepareStatement(sql);
				ps.execute();

				for (int i = 1; i <= voter_no; i++) {
					String temp_voter_username = election_id;
					String temp_voter_pwd;

					// generating temporary pwd
					char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
					StringBuilder sb = new StringBuilder();
					Random random = new Random();
					for (int j = 0; j < 7; j++) {
						char random_char = chars[random.nextInt(chars.length)];
						sb.append(random_char);
					}
					temp_voter_pwd = sb.toString();

					// generating temporary voter username
					temp_voter_username = temp_voter_username + "_voter" + i;

					sql = "insert into " + voter_table + "(Voter_username, Voter_password) values(?,?);";
					ps = c.prepareStatement(sql);
					ps.setString(1, temp_voter_username);
					ps.setString(2, temp_voter_pwd);
					ps.execute();
				}
				for (int i = 1; i <= candidate_no; i++) {
					String temp_candidate_username = election_id;
					String temp_candidate_pwd;

					// generating temporary pwd
					char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
					StringBuilder sb = new StringBuilder();
					Random random = new Random();
					for (int j = 0; j < 7; j++) {
						char random_char = chars[random.nextInt(chars.length)];
						sb.append(random_char);
					}
					temp_candidate_pwd = sb.toString();

					// generating temporary candidate username
					temp_candidate_username = temp_candidate_username + "_candidate" + i;

					sql = "insert into " + candidate_table + "(Candidate_username, Candidate_password) values(?,?);";
					ps = c.prepareStatement(sql);
					ps.setString(1, temp_candidate_username);
					ps.setString(2, temp_candidate_pwd);
					ps.execute();
				}

				RequestDispatcher RequetsDispatcherObj = request.getRequestDispatcher("/Reg_Homepage.jsp");
				RequetsDispatcherObj.forward(request, response);

			}

			c.close(); // Closing db connection

		} catch (SQLException ex) {
			System.out.println(ex);
		}

	}

}
