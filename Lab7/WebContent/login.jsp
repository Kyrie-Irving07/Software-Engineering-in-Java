<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="mypack.Jdbc"%>
<!DOCTYPE html>

<%
	String msg = (String) session.getAttribute("msg");
	if(msg == null || msg.trim().length() == 0)
		msg = "";
	Jdbc.increment("1", "4");
%>

<html>
<head>
<meta charset="UTF-8">
<title>Log In</title>
</head>
<body>
	<form name="userform" action="authenticate.jsp">
		UserName <input type="text" name="username" /><br />
		Password <input type="text" name="password" /><br />
		<input type="submit" name="submit" value="Log In" />
	</form>
	<font color="red"><%=msg %></font>
</body>
</html>