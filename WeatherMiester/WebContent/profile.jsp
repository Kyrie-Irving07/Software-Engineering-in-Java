<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="backend.StringArray"%>
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

  <div id="Capital" style="font-family:myFont"><a href="Homepage.jsp" style="color:white; text-decoration: none;"><h1>WeatherMeister</h1></a></div>
  
  <h1 style="color:white; top:90px; left:20px; position:absolute; font-size:50px;"><%=(String) session.getAttribute("username") %></h1>
  
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
	StringArray history = (StringArray) session.getAttribute("history");
	int count = 0;	
	if(history.array != null)
		count = history.array.length;
	int i = 0;
%>

<table id="mytable" style="display:block; height:400px; overflow-y:scroll; position:absolute; top:240px; left:40%">
<tr>
	<th><font color="white" size="30px"> History </font></th>
</tr>
<% for(i = 0; i < count; i ++) { %>
	<tr>
		<td><font color="white" size="20px"><%=history.array[i] %></font></td>
	</tr>
<% } %>
</table>

</body>

<script>

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

function validate() {
	 var option;
	 var radios = document.getElementsByName("option");
	 for (var i = 0, length = radios.length; i < length; i ++)
	 {
	 	if(radios[i].checked)
		{
			option = radios[i].value;
			break;
		}
	 }
	 var xhttp = new XMLHttpRequest();
	 if(option == "city")
	 {
		 xhttp.open("GET",
				 "Navigation?city=" + document.myform.city.value +
				 "&option=" + option
				 , false);
	 }
	 else {
		 xhttp.open("GET",
				 "Navigation?option=" + option +
				 "&lat=" + 	document.myform.lat.value +
				 "&lon=" + 	document.myform.lon.value
				 , false);
	 }
	 
	 xhttp.send();
	 if (xhttp.responseText.trim().length > 0) {
			 document.getElementById("info").innerHTML = xhttp.responseText;
			 return false; 
	}
	 return true;

}
</script>
</html>
