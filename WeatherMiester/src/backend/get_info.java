package backend;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/get_info")
public class get_info extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		System.out.println("Searching");
		String weather = request.getParameter("weather");
    	HttpSession session = request.getSession();
		City city = (City) session.getAttribute("city");
		String info = city.search(weather);
		PrintWriter out = response.getWriter();
		out.print("<button name=\""+weather+"\" onclick=\"show_image('"+weather+"')\">");
		out.print("<font color=white style=\"font-size:large\">");
		out.print(info);
		out.print("</font>");
		out.print("</buttom>");
		System.out.println(info);
	}
    public get_info() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
