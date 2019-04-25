<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="backend.string_process" %>
<!DOCTYPE html>
<%
	String fnameError = (String) request.getAttribute("fnameError");
	String lnameError = (String) request.getAttribute("lnameError");
	String monthError = (String) request.getAttribute("monthError");
	String emailError = (String) request.getAttribute("emailError");
	String paymentError = (String) request.getAttribute("paymentError");
	String itemsError = (String) request.getAttribute("itemsError");
	
	String fname = request.getParameter("fname");
	String lname = request.getParameter("lname");
	String month = request.getParameter("month");
	String email = request.getParameter("email");
	String payment = request.getParameter("payment");
	
	fnameError = string_process.process(fnameError);
	lnameError = string_process.process(lnameError);
	monthError = string_process.process(monthError);
	emailError = string_process.process(emailError);
	paymentError = string_process.process(paymentError);
	itemsError = string_process.process(itemsError);
	
	fname = string_process.process(fname);
	lname = string_process.process(lname);
	month = string_process.process(month);
	email = string_process.process(email);
	payment = string_process.process(payment);
%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>My form validation</title>
	</head>
	<body>
		<h2>Form Validation Example</h2>
		<strong></strong><font color="red"><div id="info"></div></font>
		<form name="pigform" method="POST" action="success.jsp" onsubmit="return validate();">
			First Name <input type="text" name="fname" value="<%=fname %>" />
			<font color="red"><div id="fnameinfo"></div><%= fnameError %></font><br />
			Last Name <input type="text" name="lname" value="<%=lname %>" />
			<font color="red"><div id="lnameinfo"></div><%= lnameError %></font><br />
			Email <input type="text" name="email" value="<%=email %>" />
			<font color="red"><div id="nameinfo"></div><%= emailError %></font><br />
			Month <input type="month" name="month">
			<font color="red"><div id="nameinfo"></div><%= monthError %></font><br />
			Items 
				<input type="text" name="items">
				<font color="red"><div id="nameinfo"></div><%= itemsError %></font>
				<br />
			Payment
				<input type="radio" name="payment" value="crash">Crash
				<input type="radio" name="payment" value="credit"> Credit Card
				<font color="red"><div id="nameinfo"></div></font>
				<br />
			<input type="submit" name="submit" value="Validate Me"" />
			
		</form>
	</body>

<script>
 function validate() {
	 var xhttp = new XMLHttpRequest();
	 
	 xhttp.open("GET",
			 "servlet1?fname=" + document.pigform.fname.value +
			 "&lname=" + 	document.pigform.lname.value +
			 "&month=" + 	document.pigform.month.value +
			 "&email=" + 	document.pigform.email.value +
			 "&payment=" + 	document.pigform.payment.value +
			 "&items=" + 	document.pigform.items.value
			 , false);

	 xhttp.send();
	 if (xhttp.responseText.trim().length > 0) {
			 document.getElementById("info").innerHTML = xhttp.responseText;
			 return false;
	}
	 return true;
 }
</script>

</html>



