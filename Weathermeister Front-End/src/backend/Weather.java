package backend;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet("/Weather")
public class Weather extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public Weather() {
		super();
	}

	public static HashMap<String, City> Load_data() throws Exception {
		// TODO Auto-generated method stub
				Scanner scan = new Scanner(System.in);
				// Load data from file and convert it to a map.
				System.out.println(System.getProperty("user.dir"));
				HashMap<String, City> map = new HashMap<String, City>();
				try {
					FileReader fr = new FileReader("goodweather.txt");
					BufferedReader br = new BufferedReader(fr);
					
					String line = br.readLine();
					while(line != null) {
						City city = City.parse(line);
						map.put(city.name.toLowerCase(), city);
						line = br.readLine();
					}
					fr.close();
					br.close();
				} catch(FileNotFoundException fnfe) {
					System.out.println("fnfe : " + fnfe.getMessage());
					scan.close();
					return null;
				} catch(IOException ioe) {
					System.out.println("ioe : " + ioe.getMessage());
					scan.close();
					return null;
				} catch (Exception e) {
					e.printStackTrace();
					scan.close();
					return null;
				}
				scan.close();
				return map;
	}	
}


