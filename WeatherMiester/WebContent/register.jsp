<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
	String username = (String) session.getAttribute("username");
%>

<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="./mystyle.css" media="all">
<title>Register Page</title>
</head>
<body id="main" background="./Assignment2Images/background.jpg" background-color="rgba(0,0,0,0.5)">

<div id="Capital"><a href="Homepage.jsp" style="color:white; text-decoration: none;"><h1>WeatherMeister</h1></a></div>

<div id="userbutton" style="position:absolute; right:10px; top:10px;">
	<%if(username.equals("anonymous")) {%>
		<a href="login.jsp" style="color:white; text-decoration: none; white-space: nowrap;">Login </a>
		<a href="register.jsp" style="color:white; text-decoration: none; white-space: nowrap">Register</a>
	<%} else {%>
		<a href="profile.jsp" style="color:white; text-decoration: none; white-space: nowrap">Profile</a>
		<a href="signout.jsp" style="color:white; text-decoration: none; white-space: nowrap" onclick="return signout()">Sign Out</a>
	<%} %>
</div>

<div id="logo">
  <img src="./Assignment3Images/new-account-icon-256x256.png" width="200" height="200"><br />
</div>

<div id="logform">
	<form style="background-image:url('Assignment3Images/Black Vignette.png');" name="register_form" action="Homepage.jsp" method="POST" onsubmit="return register()">
		<font color="white">
		
		Username<br />
		<input type="text" name="username"><br />
		Password<br />
		<input type="password" name="password"><br />
		Confirm Password<br />
		<input type="password" name="confirm_password"><br />
		<button style="background-color:orange" type="submit" value="Register">Register</button>
		
		</font>
	</form>
</div>
<font color="red"><div id="info"></div></font>
  

</body>

<script>

function register() {
	 var xhttp = new XMLHttpRequest();
	
	 xhttp.open("GET",
			 "Register?username=" + document.register_form.username.value +
			 "&password=" + document.register_form.password.value +
			 "&confirm_password=" + document.register_form.confirm_password.value
			 , false);
	 
	 xhttp.send();
	 if (xhttp.responseText.trim().length > 0) {
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


</html>

