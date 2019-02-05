<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="backend.Weather, java.util.HashMap, backend.City"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="refresh" content="2;Homepage.jsp">
<%
	String root = request.getContextPath();
	HashMap<String, City> map = Weather.Load_data();
	request.setAttribute("map", map);
//	response.sendRedirect("Homepage.jsp");
%>
    <title>WeatherMeister</title>
	<link rel="stylesheet" type="text/css" href="mystyle.css">
</head>
<body background="./Assignment2Images/background.jpg">

  <font color="white">
	  This is Index.jsp <br />
	  Loading data ...... <br />
	  It would stop for 2 seconds. <br />
	  No need to load data anymore in subsequent pages.
  </font>
  
</body>

</html>