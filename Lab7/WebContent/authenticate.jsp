<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="mypack.Jdbc" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Authenticate</title>
</head>

<%
String username = (String) request.getParameter("username");
String password = (String) request.getParameter("password");

int result = Jdbc.validate(username, password);

String destination = null;
if(result != 0) {
	Jdbc.increment("2", String.valueOf(result));
	session.setAttribute("user", result);
	response.sendRedirect("cat.jsp");
}
else {
	Jdbc.increment("2", "4");
	session.setAttribute("msg", "Invalid username and password");
	response.sendRedirect("login.jsp");
}
%>

<body onload="login.jsp">
</body>

</html>