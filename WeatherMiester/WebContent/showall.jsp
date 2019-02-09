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
button {
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


</body>

<script>
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
