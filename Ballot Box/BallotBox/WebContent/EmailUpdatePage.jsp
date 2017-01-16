<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h3>Update Candidate Email</h3>
<table>
<tbody>
<c:forEach items="${requestScope.candidateMailList}" var="reply">
<tr><td style="width:50px"></td><td>
<c:out value="${reply.candidate_username}"></c:out></td>
<td style="width:50px"></td><td>   
<form action="UpdateEmail" method="get">
	<input type="text" id="candidate_updated_email" name="candidate_updated_email" value="<c:out value="${reply.candidate_email}"/>" required />
	<input type="hidden" id="usertype" name="usertype" value="candidate" />
	<input type="hidden" id="candidate_uname" name="candidate_uname" value="<c:out value="${reply.candidate_username}"/>" />
	<input type="submit" value="Update">
	</form>
</td></tr>
</c:forEach>
</tbody>
</table>

</br>
<h3>Update Voter Email</h3>
<table>
<tbody>
<c:forEach items="${requestScope.voterMailList}" var="reply">
<tr><td style="width:50px"></td><td>
<c:out value="${reply.voter_username}"></c:out></td>
<td style="width:50px"></td><td>   
<form action="UpdateEmail" method="get">
	<input type="text" id="voter_updated_email" name="voter_updated_email" value="<c:out value="${reply.voter_email}"/>" required />
	<input type="hidden" id="usertype" name="usertype" value="voter" />
	<input type="hidden" id="voter_uname" name="voter_uname" value="<c:out value="${reply.voter_username}"/>" />
	<input type="submit" value="Update">
	</form>
</td></tr>
</c:forEach>
</tbody>
</table>

</body>
</html>