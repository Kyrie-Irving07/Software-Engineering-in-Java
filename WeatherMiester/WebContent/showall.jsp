<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>WeatherMeister</title>	
<link rel="stylesheet" type="text/css" href="mystyle.css" media="all">
<style>
form {
	position: absolute;
	right: 0px;
	top: 0px;
}
tr { border: 2px solid white;}
button {
	background:Transparent;
	border: none;
}
button:active {
	background:gray;
	border: none
}
</style>
</head>
<body id="main" background="./Assignment2Images/background.jpg">

  <div id="Capital">WeatherMeister</div>
  
  <h1 style="color:white; top:20px; left:20px; position:absolute">All Cities</h1>
  
  <div id="barform">
	  <form name="myform" action="details.jsp" method="POST" onsubmit="return validate();">
			<input type="text" name="city" /><font color="red"></font>
			<button id="barsearchbutton" type="submit">
				<img src="./Assignment2Images/magnifying_glass.jpeg" width="13" height="13" alt="search"><br />
			</button>
		  	<input type="radio" name="option" value="city" checked="true" onclick="switchform('city')"><font color="white">City</font>
			<input type="radio" name="option" value="location" onclick="switchform('location')"><font color="white">Location (Lat/Long)</font><br />
	  </form>
  </div>
  <font color="red" style="position:absolute; right:0; top:20px"><div id="info"></div></font>

<%
	String city[] = (String [])session.getAttribute("name_array");
	String templow[] = (String [])session.getAttribute("templow");
	String temphigh[] = (String [])session.getAttribute("temphigh");
	int count = city.length;
	int i = 0;
%>

<table id="mytable">
<tr>
	<th><font color="white" size="30px"> City </font></th>
	<th><font color="white" size="30px"> Temp.Low </font></th>
	<th><font color="white" size="30px"> Temp.High </font></th>
</tr>
<% for(i = 0; i < count; i ++) { %>
	<tr>
		<td><button onclick="go_details('<%=city[i] %>')"><font color="white" size="20px"><%=city[i] %></font></button></td>
		<td><font color="white" size="20px"><%=templow[i] %></font></td>
		<td><font color="white" size="20px"><%=temphigh[i] %></font></td>
	</tr>
<% } %>
</table>

<div id="sort">
<font color="white" size=20px>Sorted By</font><br />
<select id="Sort" onchange="sort();">
	<option value="name">City Name A-Z</option>
	<option value="named">City Name Z-A</option>
	<option value="templow">Temp.Low ASC</option>
	<option value="templowd">Temp.Low DASC</option>
	<option value="temphigh">Temp.High ASC</option>
	<option value="temphighd">Temp.High DASC</option>
</select>
</div>

</body>

<script>
function go_details(city) {
	console.log("123");
	alart(city);
}

function sort() {
    var selectBox = document.getElementById("Sort");
    var sort = selectBox.options[selectBox.selectedIndex].value;
	var xhttp = new XMLHttpRequest(); 
	xhttp.open("GET",
			"show_all?sort=" + sort
			, false);
	xhttp.send();
	window.location.href = "showall.jsp";
}
   
function switchform(option) {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
      document.getElementById("barform").innerHTML =
      this.responseText;
    }
  };
  xhttp.open("GET", "./Barform/" + option + "form.html", true);
  xhttp.send();
}
</script>
</html>
