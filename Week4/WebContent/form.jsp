<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="abc.string_process" %>
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
		<form action="servlet1" method="POST">
			First Name <input type="text" name="fname" value="<%=fname %>" /><font color="red"><%= fnameError %></font><br />
			Last Name <input type="text" name="lname" value="<%=lname %>" /><font color="red"><%= lnameError %></font><br />
			Email <input type="text" name="email" value="<%=email %>" /><font color="red"><%= emailError %></font><br />
			Month <input type="month" name="month"><font color="red"><%= monthError %></font><br />
			Items 
				<input type="checkbox" name="items" value="Apple">Apple
				<input type="checkbox" name="items" value="Banana"> Banana
				<input type="checkbox" name="items" value="Egg"> Egg
				<font color="red"><%= itemsError %></font>
				<br />
			Payment
				<input type="radio" name="payment" value="crash" checked="true">Crash
				<input type="radio" name="payment" value="credit"> Credit Card<%= paymentError %><br />
			<input type="submit" name="submit" value="Validate Me" />
			
		</form>
	</body>
</html>