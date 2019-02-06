<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="backend.servlet1" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Form Success</title>
	</head>
	<body>
		<h2><%=servlet1.getTitle() %></h2>
<%
		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		String email = request.getParameter("email");
		String items = request.getParameter("items");
		String month = request.getParameter("month");
		String payment = request.getParameter("payment");
%>
		First Name = <%= fname %> <br />
		Last Name = <%= lname %> <br />
		Email: <%= email %> <br />
		Month: <%=month %> <br />
		Items: <%= items %> <br />
		Payment: <%= payment %> <br />
	</body>
</html>