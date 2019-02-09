package backend;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Navigation")
public class Navigation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Navigation() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		System.out.println("Enter Navigation");

    	HttpSession session = request.getSession();
		HashMap<String, City> map = (HashMap<String, City>) session.getAttribute("map");
		
		String option = request.getParameter("option");
		PrintWriter out = response.getWriter();
		
		if(option.equals("city")) {
			String city_name = request.getParameter("city").toLowerCase();
			City city = map.get(city_name);
			if(city == null) {
				out.print("No such city: " + city_name);
				return;
			}
			System.out.println(city.name);
			System.out.println(city.info_attr[1].value + " " + city.info_attr[2].value);
			session.setAttribute("city_name", city.name);
			session.setAttribute("city", city);
		}
		else {
			City city = null;
			City temp = null;
			String lat = request.getParameter("lat");
			String lon = request.getParameter("lon");
			if(lat == null || lon == null) {
				out.print("Please Enter Both Latitude and Longitude<br /s>");
				return;
			}
			
			float latitude;
			float longitude;
			float valuef = (float) 0.0;
			try
			{
				valuef = Float.parseFloat(lat);
			} catch(NumberFormatException e) {
				out.println("Latitude parsing exception : " + e.getMessage());
				return;
			}
			latitude = valuef;
			try
			{
				valuef = Float.parseFloat(lon);
			} catch(NumberFormatException e) {
				out.println("Longitude parsing exception : " + e.getMessage());
				return;
			}
			longitude = valuef;
			
			for(String key : map.keySet()) {
				temp = map.get(key);
				if(temp.longitude == longitude && temp.latitude == latitude) {
					city = temp;
					break;	
				}
			}
			if(city == null) {
				out.print("No cities in such location: Lat " + lat + " Lon " + lon + "<br />");
				return;
			}
			System.out.println(city.name);
			System.out.println(city.info_attr[1].value + " " + city.info_attr[2].value);
			session.setAttribute("city_name", city.name);
			session.setAttribute("city", city);
		}
    }

}
