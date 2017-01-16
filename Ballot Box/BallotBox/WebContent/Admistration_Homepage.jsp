<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>BallotBox</title>
</head>
<body>

<form action="UpdateEmail" method="post">  
<input type="submit" value="Update Email"/>
</form><br>
<form action="NotifyUsers" method="post">  
<input type="submit" value="Send Email"/>
</form><br>
<form action="CalculateResult" method="post">  
<input type="submit" value="Calculate Result"/>
</form>
<br><br><br>
<a href="statistics.jsp">View Voter Survey Results</a>
<% 
 //check if session variable exists, if yes then show error msg
 //session.removeAttribute("time_left");
 if(null != session.getAttribute("time_left")) 
 {
Object hashtag_not_exists = session.getAttribute("time_left"); 
if(hashtag_not_exists.equals("1"))
{
%>
<p style="color:red" >Election is still in Voting stage.</p>
<% 
}
session.removeAttribute("time_left");
}
%>
</body>
</html>