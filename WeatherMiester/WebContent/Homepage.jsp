<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="backend.string_process, backend.Weather, java.util.HashMap, backend.City"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%
	String option = request.getParameter("option");
	if(option==null) option="city";
	
	String city = request.getParameter("city");
	if(city==null) city = "Los Angeles";
	
	String cityError = request.getParameter("cityError");
	cityError = string_process.process(cityError);
%>
    <title>WeatherMeister</title>
	<meta charset="ISO-8859-1">
	<link rel="stylesheet" type="text/css" href="mystyle.css" media="all">
</head>
<body id="main" background="./Assignment2Images/background.jpg">

  <h1 id="Capital">WeatherMeister</h1>

  <div id="logo">
    <img src="./Assignment2Images/logo.png" width="200" height="200">
  </div>
  
  <div id="form">
	  <form action="Navigation" method="POST" >
			<input type="text" name="city" value="<%=city %>" /><font color="red"><%=cityError %></font>
			<button type="submit">
			<img src="/Assignment2Images/magnifying_glass.jpeg" alt="Search"/>
			</button>
			<br />
		  	<input type="radio" name="option" value="city" checked="true" onclick="Cityform()"><font color="white">City</font>
			<input type="radio" name="option" value="location" onclick="Locationform()"><font color="white">Location (Lat/Long)</font><br />
	  </form>
  </div>
 
	<script>
	function jumptest() {
		windows.location.href = "index.jsp";
	}
	
	function Cityform() {
	  var xhttp = new XMLHttpRequest();
	  xhttp.onreadystatechange = function() {
	    if (this.readyState == 4 && this.status == 200) {
	      document.getElementById("form").innerHTML =
	      this.responseText;
	    }
	  };
	  xhttp.open("GET", "cityform.txt", true);
	  xhttp.send();
	}
	
	function Locationform() {
	  var xhttp = new XMLHttpRequest();
	  xhttp.onreadystatechange = function() {
	    if (this.readyState == 4 && this.status == 200) {
	      document.getElementById("form").innerHTML =
	      this.responseText;
	    }
	  };
	  xhttp.open("GET", "locationform.txt", true);
	  xhttp.send();
	}
	</script>

</body>
