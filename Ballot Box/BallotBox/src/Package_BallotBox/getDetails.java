package Package_BallotBox;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;

public class getDetails {

	Connection myConnection = null;

	public getDetails() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driverlocated");

			String url = "jdbc:mysql://localhost:3306/ballotboxdb";
			String user = "root";
			String password = "";

			myConnection = (Connection) DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			System.out.println("Driver could NOT be located:" + e);

		}
	}

	public ArrayList<String> getVideo(String table, String candidateuser) {

		try {

			String sql1 = "CREATE TABLE IF NOT EXISTS " + table
					+ " (url varchar(50) PRIMARY KEY, Username varchar(50))";

			Statement s1 = myConnection.createStatement();
			s1.executeUpdate(sql1);
			String sql = "select * from " + table + " where Username='" + candidateuser + "'";

			System.out.println(sql);

			Statement s = myConnection.createStatement();

			ResultSet rs = s.executeQuery(sql);
			ArrayList<String> video = new ArrayList<String>();

			while (rs.next()) {
				video.add(rs.getString("url"));
			}

			return video;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	public ArrayList<String> getBlurb(String table, String candidateuser) {

		try {

			String sql1 = "CREATE TABLE IF NOT EXISTS " + table
					+ " (url varchar(50) PRIMARY KEY, Username varchar(50))";

			Statement s1 = myConnection.createStatement();
			s1.executeUpdate(sql1);
			String sql = "select * from " + table + " where Username='" + candidateuser + "'";

			System.out.println(sql);

			Statement s = myConnection.createStatement();

			ResultSet rs = s.executeQuery(sql);
			ArrayList<String> video = new ArrayList<String>();

			while (rs.next()) {
				video.add(rs.getString("Title") + ":::::" + rs.getString("Text"));
			}

			return video;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	public ArrayList<String> getElection() {

		try {

			String sql = "SELECT * FROM `masterdb`";

			Statement s = myConnection.createStatement();

			ResultSet rs = s.executeQuery(sql);
			ArrayList<String> election = new ArrayList<String>();

			while (rs.next()) {
				election.add(rs.getString("election_id"));
			}

			return election;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	public ArrayList<String> getCandidate(String electionid) {

		try {

			String sql = "SELECT * FROM " + electionid + " ";

			Statement s = myConnection.createStatement();

			ResultSet rs = s.executeQuery(sql);
			ArrayList<String> election = new ArrayList<String>();

			while (rs.next()) {
				election.add(rs.getString("Candidate_username"));
			}

			return election;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

}
