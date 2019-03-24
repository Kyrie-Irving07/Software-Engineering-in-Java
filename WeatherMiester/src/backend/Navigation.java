package backend;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/Navigation")
public class Navigation extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Navigation() {
        super();
    }
    
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    	HttpSession session = request.getSession();
		
		String option = request.getParameter("option");
		PrintWriter out = response.getWriter();
		City city = null;
		StringArray result = new StringArray();
		IntegerArray id = new IntegerArray();
		
		if(option.equals("city")) {
			String city_name = request.getParameter("city").toLowerCase();
			record(request, response, city_name);
			id = getIDfromName(city_name, result);
			if(id.length == 0) {
				out.print("No such city: " + city_name);
				return;
			}
			else if(id.length == 1)
			{
				JSONObject file = getjson(id.array[0]);
				try {
					city = City.parse(file);
				} catch (Exception e) {
					e.printStackTrace();
				}
				session.setAttribute("city_name", city.name);
				session.setAttribute("city", city);
				session.setAttribute("number", "1");
				System.out.println(session.getAttribute("number"));
				out.print("1");
			}
			else {
				session.setAttribute("number", "More");
				System.out.println(session.getAttribute("number"));
				out.print("More");
			}
		}
		else if(option.equals("id")) {
			int idd = Integer.parseInt(request.getParameter("id"));
			id.add(idd);
			JSONObject file = getjson(idd);
			try {
				city = City.parse(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
			session.setAttribute("city_name", city.name);
			session.setAttribute("city", city);
			session.setAttribute("number", "1");
			System.out.println(session.getAttribute("number"));
			out.print("1");
		}
		else {
			String lat = request.getParameter("lat");
			String lon = request.getParameter("lon");
			if(lat == null || lon == null) {
				out.print("Please Enter Both Latitude and Longitude<br /s>");
				return;
			}
			
			record(request, response, lat + ", " + lon);
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
			
			id = getIDfromLoc(longitude, latitude, result);
			if(id.length == 0) {
				out.print("No cities in such location: Lat " + lat + " Lon " + lon + "<br />");
				return;
			}
			else if(id.length == 1) {
				JSONObject file = getjson(id.array[0]);
				try {
					city = City.parse(file);
				} catch (Exception e) {
					e.printStackTrace();
				}
				session.setAttribute("city_name", city.name);
				session.setAttribute("city", city);
				out.print("1");
			}
			else {
				out.print("More");
			}
		}
		session.setAttribute("id", id);
		session.setAttribute("result", result);
    }

	public static JSONObject getjson(int id) {
		JSONObject myresponse = null;
    	try {
    	  String appid = "&appid=b710617ec75d20e7e78bc8644671453c";
    	  String url = "http://api.openweathermap.org/data/2.5/weather?id=";
    	  URL obj = new URL(url+id+appid);
    	  HttpURLConnection con = (HttpURLConnection) obj.openConnection();
    	  int responseCode = con.getResponseCode();
    	  System.out.println("\nSending 'GET' request to URL : " + obj);
    	  System.out.println("Response Code : " + responseCode);
    	  BufferedReader in =new BufferedReader(
    	  new InputStreamReader(con.getInputStream()));
    	  String inputLine;
    	  StringBuffer response = new StringBuffer();
    	   while ((inputLine = in.readLine()) != null) {
    	     response.append(inputLine);
    	   } in .close();
    	   myresponse = new JSONObject(response.toString());
    	   System.out.println("City: " + myresponse.getString("name"));
    	   
    	  } catch(Exception e) {
    	    System.out.println(e);
    	  }
    	return myresponse;
	}
	
	public static IntegerArray getIDfromName(String cityname, StringArray result) {
		IntegerArray id = new IntegerArray();
		cityname = cityname.toLowerCase();
		try {
			FileReader fr = new FileReader("./city.list.json");
			BufferedReader br = new BufferedReader(fr);
			String inputLine;
			StringBuffer list = new StringBuffer();
			while ((inputLine = br.readLine()) != null) {
				list.append(inputLine);
			} br.close();
	    	JSONArray citylist = new JSONArray(list.toString());
			fr.close();
			br.close();
			
			for (int i = 0; i < citylist.length(); i ++)
			{
				JSONObject temp = citylist.getJSONObject(i);
				if(temp.getString("name").toLowerCase().startsWith(cityname)) {
					id.add(temp.getInt("id"));
					result.add(temp.getString("name"));
				}
			}
		} catch(FileNotFoundException fnfe) {
			System.out.println("fnfe : " + fnfe.getMessage());
		} catch(IOException ioe) {
			System.out.println("ioe : " + ioe.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}
	
	public static IntegerArray getIDfromLoc(float lon, float lat, StringArray result) {
		IntegerArray id = new IntegerArray();
		try {
			FileReader fr = new FileReader("./city.list.json");
			BufferedReader br = new BufferedReader(fr);
			String inputLine;
			StringBuffer list = new StringBuffer();
			while ((inputLine = br.readLine()) != null) {
				list.append(inputLine);
			} br.close();
	    	JSONArray citylist = new JSONArray(list.toString());
			fr.close();
			br.close();
			double x;
			double y;
			for (int i = 0; i < citylist.length(); i ++)
			{
				JSONObject temp = citylist.getJSONObject(i);
				JSONObject coord = new JSONObject(temp.getJSONObject("coord").toString());
				x = coord.getDouble("lon");
				y = coord.getDouble("lat");
				if(lon < x + 2 && lon > x - 2 && lat < y + 2 && lat > y - 2) {
					id.add(temp.getInt("id"));
					result.add(citylist.getJSONObject(i).getString("name"));
				}
			}
		} catch(FileNotFoundException fnfe) {
			System.out.println("fnfe : " + fnfe.getMessage());
		} catch(IOException ioe) {
			System.out.println("ioe : " + ioe.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}
	
	public static void record(HttpServletRequest request, HttpServletResponse response, String history) throws IOException {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		
		StringArray total_history = (StringArray) session.getAttribute("history");
		if(total_history == null)
			total_history = new StringArray();
		total_history.add(history);
		session.setAttribute("history", total_history);
		
		Connection conn = null;
		PreparedStatement pt = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Assignment3?user=root&password=4f6e618ec3964b0052831a5c3a40d26d");
			pt = conn.prepareStatement("INSERT INTO History (history, username) VALUE (?, ?)");
			pt.setString(1, history);
			pt.setString(2, username);
			System.out.println(pt.toString());
			pt.executeUpdate();
		} catch (SQLException sqle) {
			System.out.println("sqle: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		}
		finally {
			try {
				if(pt != null) {pt.close();}
				if(conn != null) {conn.close();}
			} catch (SQLException sqle) {
				System.out.println("Record sqle closing stuff: " + sqle.getMessage());
			}
		}
	}
	
}
