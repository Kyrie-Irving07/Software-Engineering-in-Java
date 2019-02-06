package backend;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/servlet1")
public class servlet1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void  service(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	String fname=request.getParameter("fname");
		String lname=request.getParameter("lname");
		String email=request.getParameter("email");
		String month=request.getParameter("month");
		String payment=request.getParameter("payment");
		String items=request.getParameter("items");
		
		PrintWriter out = response.getWriter();
		out.flush();
		
		System.out.println(payment);
		
		if(fname == null || fname.trim().length() == 0) {
			request.setAttribute("fnameError", "First name needs a validation string");
			out.println("First name needs a validation string<br />");
		}
		if(lname == null || lname.trim().length() == 0) {
			request.setAttribute("lnameError", "Last name needs a validation string");
			out.println("Last name needs a validation string<br />");
		}
		if(email == null || email.trim().length() == 0) {
			request.setAttribute("emailError", "Invalid email");
			out.println("Invalid email<br />");
		}
		if(payment == null || payment.trim().length() == 0) {
			request.setAttribute("paymentError", "Choose a way to pay");
			out.println("Choose a way to pay<br />");
		}
		if(month == null || month.trim().length() == 0) {
			request.setAttribute("monthError", "Choose a Month");
			out.println("Choose a Month<br />");
		}
		if(items == null || items.trim().length() == 0) {
			request.setAttribute("itemsError", "Choose at least an item");
			out.println("Choose at least an item<br />");
		}
    }

	
	public static String getTitle() {
		return "Form Validation";
	}

}
