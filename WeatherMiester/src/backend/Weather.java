package backend;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Weather")
public class Weather extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public Weather() {
		super();
	}
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		boolean success = true;
		PrintWriter out = response.getWriter();
		Scanner scan = new Scanner(System.in);
		// Load data from file and convert it to a map.
		HashMap<String, City> map = new HashMap<String, City>();
		try {
			FileReader fr = new FileReader("goodweather.txt");
			BufferedReader br = new BufferedReader(fr);
			
			String line = br.readLine();
			while(line != null) {
				City city = City.parse(line, out);
				System.out.println(city.name + " data read :" + String.valueOf(city.valid));
				success &= city.valid;
				map.put(city.name.toLowerCase(), city);
				line = br.readLine();
			}
			fr.close();
			br.close();
		} catch(FileNotFoundException fnfe) {
			System.out.println("fnfe : " + fnfe.getMessage());
			out.println("fnfe : " + fnfe.getMessage());
			scan.close();
		} catch(IOException ioe) {
			System.out.println("ioe : " + ioe.getMessage());
			out.println("ioe : " + ioe.getMessage());
			scan.close();
		} catch (Exception e) {
			e.printStackTrace();
			out.println(e.toString());
			scan.close();
		}
		scan.close();
		
		if(success) {
			HttpSession session = request.getSession();
			session.setAttribute("map", map);
			System.out.println("Load successfully!");
		}
	}
	
}


