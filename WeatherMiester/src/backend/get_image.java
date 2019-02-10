package backend;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/get_image")
public class get_image extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public get_image() {
        super();
    }
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		PrintWriter out = response.getWriter();
		String weather = request.getParameter("weather");
		out.print("	<button name=\"currenttemp\" onclick=\"show_info('" + weather + "')\">\n" + 
				"	 <img src=\"./Assignment2Images/" + weather + ".png\" width=\"100\" height=\"100\" alt=\"" + weather + "\"><br />\n" + 
				"	<font color=white style=\"font-size:large\">" + weather + "</font>\n" + 
				"	</button>");
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
