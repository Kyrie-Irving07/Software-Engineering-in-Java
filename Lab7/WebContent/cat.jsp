<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="mypack.Jdbc"%>
<!DOCTYPE html>
<%
int user = (int)session.getAttribute("result");
Jdbc.increment("3", String.valueOf(user));
%>
<html>
<head>
<meta charset="UTF-8">
<title>Cat</title>
</head>
<body background="./image/Cat.jpg">

<a href="cat.jsp" style="color:blue;">Cat</a><br />
<a href="dog.jsp" style="color:blue;">Dog</a><br />
<a href="fish.jsp" style="color:blue;">Fish</a><br />

</body>
</html>