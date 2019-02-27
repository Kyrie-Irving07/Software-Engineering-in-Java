<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="backend.Weather, java.util.HashMap, backend.City"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

    <title>WeatherMeister</title>
	<link rel="stylesheet" type="text/css" href="./mystyle.css">
</head>
<body background="./Assignment2Images/background.jpg" onload="load_data()">

<h1>
  <font id="info" color="white">
  	Loading Data ......
  </font>
</h1>
  
</body>

<script>
function load_data() {
	var xhttp = new XMLHttpRequest(); 
	xhttp.open("GET",
			"Weather"
			, false);
	xhttp.send();
	if (xhttp.responseText.trim().length > 0) {
		document.getElementById("info").innerHTML = xhttp.responseText;
	}	
	else {
		window.location.href = "Homepage.jsp";
	}
}
</script>

</html>