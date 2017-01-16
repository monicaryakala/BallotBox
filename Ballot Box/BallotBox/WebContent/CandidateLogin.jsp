<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>

</head>
<body>
<form action="LoginServlet" method="post" onsubmit="return validate()">
Candidate username: <input type="text" name="Candidate_username" id="Candidate_username" required></br>
<p style="color:red" id="msg_candidate_uname"></p>
Candidate password: <input type="password" name="Candidate_password" id="Candidate_password" required></br>
<p style="color:red" id="msg_candidate_pwd"></p>
election id: <input type="text" name="election_id" id="election_id" required></br>
<p style="color:red" id="msg_election_id"></p>
<input type="submit" value="Login" >
</form>
</body>
</html>