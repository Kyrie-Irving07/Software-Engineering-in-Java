package backend;

import java.io.PrintWriter;
import java.util.Scanner;

import org.json.JSONObject;

public class City {
	String name;
	Attribute [] info_attr = new Attribute[11];
	boolean valid;
	
	public String search(String attr) {
		String answer = attr + " Not Found";
		for(int i = 0;i < this.info_attr.length;i ++) {
			if(this.info_attr[i]!=null && attr.equals(this.info_attr[i].name)) {
				answer = this.info_attr[i].value;
				break;
			}
		}
		return answer;
	}
	
	public static City parse(JSONObject file) throws Exception
	{
		// The first element of String array is name of city.
		City info = new City();
		info.valid = true;

		info.name = file.getString("name");
		
		JSONObject sys = new JSONObject(file.getJSONObject("sys").toString());
		info.info_attr[1] = Attribute.init("location", info.name + "<br />" + sys.getString("country"));
		
		// Used to save value from parsing.
		float valuef = (float) 0.0;
		int valuei = 0;
		
		// latitude and longitude of city
		JSONObject coord = new JSONObject(file.getJSONObject("coord").toString());
		double lon = coord.getDouble("lon");
		double lat = coord.getDouble("lat");
		info.info_attr[2] = Attribute.init("coordinates", lon + ", " + lat);
		
		// sunrise and sunset time
		int sunrise = sys.getInt("sunrise");
		int sunset = sys.getInt("sunset");
		info.info_attr[3] = Attribute.init("sun", sunrise + ",<br /> " + sunset);
		
		// Parse the string of each attribute.
		JSONObject main = new JSONObject(file.getJSONObject("main").toString());
		double temp = main.getDouble("temp");
		info.info_attr[4] = Attribute.init("currenttemp", temp + " degrees Fahrenheit.");
		
		double templow = main.getDouble("temp_min");
		double temphigh = main.getDouble("temp_max");
		info.info_attr[5] = Attribute.init("templow", templow + " degrees Fahrenheit.");
		info.info_attr[6] = Attribute.init("temphigh", temphigh + " degrees Fahrenheit.");
		
		int humidity = main.getInt("humidity");
		info.info_attr[7] = Attribute.init("humidity", humidity + "%.");
		
		int pressure = main.getInt("pressure");
		info.info_attr[8] = Attribute.init("pressure", pressure + " Inch Hg.");
		
		int visibility = 1000; //file.getInt("visibility");
		info.info_attr[9] = Attribute.init("visibility", visibility + " miles.");
		
		JSONObject wind = new JSONObject(file.getJSONObject("wind").toString());
		double speed = wind.getDouble("speed");
		double deg = wind.getDouble("deg");
		info.info_attr[10] = Attribute.init("wind", speed + " miles/hour <br //> " + deg + " degrees.");
		return info;
	}
	
	public int parseInt(String input, String name, PrintWriter out) throws Exception
	{
		int valuei = 0;
		try
		{
			valuei = Integer.parseInt(input);
		} catch(NumberFormatException e) {
			this.valid = false;
			out.println(name + " parsing exception : " + e.getMessage() + "<br />");
		}
		return valuei;
	}
	
	public float parseFloat(String input, String name, PrintWriter out) throws Exception
	{
		float valuef = (float) 0.0;
		try
		{
			valuef = Float.parseFloat(input);
		} catch(NumberFormatException e) {
			this.valid = false;
			out.println(name + " parsing exception : " + e.getMessage() + "<br />");
		}
		return valuef;
	}

	public boolean parseTime(String input, String name, PrintWriter out) throws Exception
	{
		boolean result = true;
		if(!(input.endsWith(" am")||input.endsWith(" pm")))
			result = false;
		String [] time = input.split("\\s+");
		if(!(time.length==2))
			result = false;
		time = time[0].split(":");
		if(!(time.length==2))
			result = false;
		parseInt(time[0], "Time of sunrise and sunset", out);
		parseInt(time[1], "Time of sunrise and senset", out);
		if(!result)
		{
			this.valid = false;
			out.println(name + " parsing exception" + "<br />");
		}
		return result;
	}
}
