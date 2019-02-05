package abc;


import java.io.IOException;
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
    public servlet1() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fname=request.getParameter("fname");
		String lname=request.getParameter("lname");
		String email=request.getParameter("email");
		String month=request.getParameter("month");
		String payment=request.getParameter("payment");
		String items=request.getParameter("items");
		
		System.out.println("items: " + items);
		
		String nextPage = "/success.jsp";
		if(fname == null || fname.trim().length() == 0) {
			request.setAttribute("fnameError", "First name needs a validation string");
			nextPage = "/form.jsp";
		}
		if(lname == null || lname.trim().length() == 0) {
			request.setAttribute("lnameError", "Last name needs a validation string");
			nextPage = "/form.jsp";
		}
		if(email == null || email.trim().length() == 0) {
			request.setAttribute("emailError", "Invalid email");
			nextPage = "/form.jsp";
		}
		if(payment == null || payment.trim().length() == 0) {
			request.setAttribute("paymentError", "Choose a way to pay");
			nextPage = "/form.jsp";
		}
		if(month == null || month.trim().length() == 0) {
			request.setAttribute("monthError", "Choose a Month");
			nextPage = "/form.jsp";
		}
		if(items == null || items.trim().length() == 0) {
			request.setAttribute("itemsError", "Choose at least an item");
			nextPage = "/form.jsp";
		}
		
		RequestDispatcher dispatch = getServletContext().getRequestDispatcher(nextPage);
		dispatch.forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	public static String getTitle() {
		return "Form Validation";
	}

}
