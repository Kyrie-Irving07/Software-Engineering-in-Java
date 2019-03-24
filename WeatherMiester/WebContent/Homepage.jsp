<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="backend.string_process, backend.Weather, java.util.HashMap, backend.City"%>
<!DOCTYPE html>

<%
	String username = (String) session.getAttribute("username");
%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

    <title>WeatherMeister</title>
	<meta charset="ISO-8859-1">
	<link rel="stylesheet" type="text/css" href="./mystyle.css" media="all">
	<style>
		h1 {
			font-family: myFont;
		}
		html, body {
        height: 100%;
        margin: 0;
        padding: 0;
      	}
	</style>
</head>
<body id="main" background="./Assignment2Images/background.jpg" background-color="rgba(0,0,0,0.5)">

 <div id="map" style="display:none; z-index :999;" >
 </div>
  
  <h1 id="Capital">WeatherMeister</h1>
  
  <div id="userbutton">
  	<%if(username.equals("anonymous")) {%>
  		<a href="login.jsp" style="color:white; text-decoration: none; white-space: nowrap;">Login </a>
  		<a href="register.jsp" style="color:white; text-decoration: none; white-space: nowrap">Register</a>
  	<%} else {%>
  		<a href="profile.jsp" style="color:white; text-decoration: none; white-space: nowrap">Profile</a>
  		<a href="Homepage.jsp" style="color:white; text-decoration: none; white-space: nowrap" onclick="return signout()">Sign Out</a>
  	<%} %>
  </div>

  <div id="logo">
    <img src="./Assignment2Images/logo.png" width="200" height="200"><br />
  </div>
  
  <div id="form">
	  <form name="myform" action="List.jsp" method="POST" onsubmit="return validate()">
			<input type="text" name="city" value="Los Angeles"
			 style="background:rgba(0, 0, 0, 0.5); color:gray"/><font color="red"></font>
			<button type="submit" value="submit">
				<img src="./Assignment2Images/magnifying_glass.jpeg" width="13" height="13" alt="search"><br />
			</button>
			<br />
		  	<input type="radio" name="option" value="city" checked="true" onclick="switchform('city')"><font color="white">City</font>
			<input type="radio" name="option" value="location" onclick="switchform('location')"><font color="white">Location (Lat/Long)</font><br />
	  </form>
  </div>
  <font color="red"><div id="info"></div></font>
  
  <br />
      
</body>

<script>

function disappear() {
	document.getElementById("map").style.display = "none";
}

var map;

function placeMarker(position, map) {
    var marker = new google.maps.Marker({
        position: position,
        map: map
    });
    map.panTo(position);
}

function show_map() {
	document.getElementById("map").style.display = "";
	console.log(123);
	var mapprop= {
	  center:new google.maps.LatLng(39.9042, 
			  						116.4074),
	  zoom:8,
	};
    map = new google.maps.Map(document.getElementById('map'), mapprop);
    map.addListener('click', function(e) {
        placeMarker(e.latLng, map);
        disappear();
    });
 }

function show_all() {
	var xhttp = new XMLHttpRequest(); 
	xhttp.open("GET",
			"show_all?sort=name"
			, false);
	xhttp.send();
	window.location.href = "showall.jsp";
}

function switchform(option) {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
      document.getElementById("form").innerHTML =
      this.responseText;
    }
  };
  xhttp.open("GET", "./Bigform/" + option + "form.html", true);
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
	 var out = xhttp.responseText;
	 if (out.trim().length > 0 && out != "1" && out != "More") {
		 document.getElementById("info").innerHTML = xhttp.responseText;
		 return false; 
	 }
	 return true;

}

function signout() {
	var xhttp = new XMLHttpRequest();
	
	 xhttp.open("GET",
			 	"Signout",
			 	false);
	 
	 xhttp.send();
	return true;
}

</script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBqgvqErWSw39pLylts8dTlwJpphi17pA0&callback=initMap"
    async defer></script>
