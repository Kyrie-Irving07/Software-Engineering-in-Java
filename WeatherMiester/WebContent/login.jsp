<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="./mystyle.css" media="all">
<title>Login Page</title>
</head>
<body id="main" background="./Assignment2Images/background.jpg" background-color="rgba(0,0,0,0.5)">

<div id="Capital"><a href="Homepage.jsp" style="color:white; text-decoration: none;"><h1>WeatherMeister</h1></a></div>

<div id="userbutton" tyle="position:absolute; right:10px; top:10px;">
		<a href="login.jsp" style="color:white; text-decoration: none; white-space: nowrap;">Login </a>
		<a href="register.jsp" style="color:white; text-decoration: none; white-space: nowrap">Register</a>
</div>

<div id="logo">
  <img src="./Assignment3Images/Keychain_Locked@2x.png" width="200" height="200"><br />
</div>

<div id="logform">
	<form style="background-image:url('Assignment3Images/Black Vignette.png');" name="login_form" action="Homepage.jsp" method="POST" onsubmit="return login()">
		<font color="white">
		
		Username<br />
		<input type="text" name="username"><br />
		Password<br />
		<input type="password" name="password"><br />
		<button style="background-color:orange" type="submit" value="Login">Login</button>
		
		</font>
	</form>
</div>
<font color="red"><div id="info"></div></font>
  

</body>

<script>

function login() {
	 var xhttp = new XMLHttpRequest();
	
	 xhttp.open("GET",
			 "Login?username=" + document.login_form.username.value +
			 "&password=" + document.login_form.password.value
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

