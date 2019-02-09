<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="backend.string_process, backend.Weather, java.util.HashMap, backend.City"%>
<!DOCTYPE html>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

    <title>WeatherMeister</title>
	<meta charset="ISO-8859-1">
	<link rel="stylesheet" type="text/css" href="mystyle.css" media="all">
	<style>
	form {
	  position: absolute;
	  right: 0px;
	  top: 0px;
	}
	button {
    background-color: Transparent;
    background-repeat:no-repeat;
    border: none;
    cursor:pointer;
    overflow: hidden;
    outline:none;
}
	</style>
</head>
<body id="main" background="./Assignment2Images/background.jpg">

  <div id="Capital">WeatherMeister</div>
  
  <div id="form">
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

<div id="location">
	<button name="locationr" onclick="show_info('location')">
	 <img src="./Assignment2Images/location.png" width="100" height="100" alt="location"><br />
	 <font color=white style="font-size:large">Location</font>
	</button>
</div>

<div id="templow">
	<button name="templow" onclick="show_info('templow')">
	 <img src="./Assignment2Images/templow.png" width="100" height="100" alt="templow"><br />
	 <font color=white style="font-size:large">Templow</font>
	</button>
</div>

<div id="temphigh">
	<button name="temphigh" onclick="show_info('temphigh')">
	 <img src="./Assignment2Images/temphigh.png" width="100" height="100" alt="temphigh"><br />
	<font color=white style="font-size:large">TempHigh</font>
	</button>
</div>

<div id="wind">
	<button name="wind" onclick="show_info('wind')">
	 <img src="./Assignment2Images/wind.png" width="100" height="100" alt="wind"><br />
	<font color=white style="font-size:large">Wind</font>
	</button>
</div>

<div id="humidity">
	<button name="humidity" onclick="show_info('humidity')">
	 <img src="./Assignment2Images/humidity.png" width="100" height="100" alt="humidity"><br />
	 <font color=white style="font-size:large">Humidity</font>
	</button>
</div>

<div id="coordinates">
	<button name="coordinates" onclick="show_info('coordinates')">
	 <img src="./Assignment2Images/coordinates.png" width="100" height="100" alt="coordinates"><br />
	<font color=white style="font-size:large">Coordinates</font>
	</button>
</div>

<div id="currenttemp">
	<button name="currenttemp" onclick="show_info('currenttemp')">
	 <img src="./Assignment2Images/currenttemp.png" width="100" height="100" alt="currenttemp"><br />
	<font color=white style="font-size:large">CurrentTemp</font>
	</button>
</div>

<div id="sun">
	<button name="sun" onclick="show_info('sun')">
	 <img src="./Assignment2Images/sun.png" width="100" height="100" alt="sun"><br />
	<font color=white style="font-size:large">Sun</font>
	</button>
</div>

  
</body>

<script>
function show_image(weather) {
	console.log("Show image " + weather);
	var xhttp = new XMLHttpRequest();
	  xhttp.onreadystatechange = function() {
	    if (this.readyState == 4 && this.status == 200) {
	    	if(weather == "location") {
				document.getElementById("location").innerHTML =
				this.responseText;
	    	}
	    	else if(weather == "templow") {
	    		document.getElementById("templow").innerHTML =
        		this.responseText;
	    	}
	    	else {
    			document.getElementById(weather).innerHTML = 
       			this.responseText;
	    	}
	    }
	}
  xhttp.open("GET", "get_image?weather=" + weather, false);
  xhttp.send();
}

function show_info(weather) {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
    	if(weather == "location")
			document.getElementById("location").innerHTML =
			this.responseText;
    	else if(weather == "templow")
    		document.getElementById("templow").innerHTML =
    		this.responseText;
    	else {
    		document.getElementById(weather).innerHTML = 
   			this.responseText;
    	}
    }
  }
  xhttp.open("GET", "get_info?weather=" + weather, false);
  xhttp.send();
}

function switchform(option) {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
      document.getElementById("form").innerHTML =
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
