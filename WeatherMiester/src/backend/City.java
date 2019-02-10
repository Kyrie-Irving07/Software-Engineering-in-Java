package backend;

import java.io.PrintWriter;
import java.util.Scanner;

public class City {
	String name;
	double latitude;
	double longitude;
	String dayLow;
	String dayHigh;
	Attribute [] info_attr = new Attribute[11];
	String []conditionDescription;
	boolean valid;
	
	public String search(String attr) {
		String answer = attr + " Not Found";
		for(int i = 0;i < this.info_attr.length;i ++) {
			if(attr.equals(this.info_attr[i].name)) {
				answer = this.info_attr[i].value;
				break;
			}
		}
		return answer;
	}
	
	public static City parse(String str, PrintWriter out) throws Exception
	{
		String []attr = str.split("\\|");

		// The first element of String array is name of city.
		City info = new City();
		info.valid = true;
		
		// If the length of array is no larger than the number of attributes.
		// Exception raised.
		if(attr.length < 16)
		{
			info.valid = false;
			out.println("There are no enough parameters on line : " + str + "<br />");
			return info;
		}
		
		info.name = attr[0];
		info.info_attr[0] = Attribute.init("name", attr[0]);
		
		// The next two elements are state and country of city
		info.info_attr[1] = Attribute.init("location", attr[1] + " " + attr[2]);
		
		// Used to save value from parsing.
		float valuef = (float) 0.0;
		int valuei = 0;
		
		// latitude and longitude of city
		valuef = info.parseFloat(attr[3], "latitude", out);
		info.latitude = valuef;
		valuef = info.parseFloat(attr[4], "longitude", out);
		info.longitude = valuef;
		info.info_attr[2] = Attribute.init("coordinates", attr[3] + ", " +  attr[4]);
		
		// sunrise and sunset time
		info.parseTime(attr[5], "sunrise", out);
		info.parseTime(attr[6], "sunset", out);
		info.info_attr[3] = Attribute.init("sun", attr[5] + ", " + attr[6]);
		
		// Parse the string of each attribute.
		info.parseInt(attr[7], "Current Temperature", out);
		info.info_attr[4] = Attribute.init("currenttemp", attr[7] + " degrees Fahrenheit.");
		
		valuei = info.parseInt(attr[8], "dayLow", out);
		info.dayLow = attr[8];
		valuei = info.parseInt(attr[9], "dayHigh", out);
		info.dayHigh = attr[9];
		info.info_attr[5] = Attribute.init("templow", attr[8] + " degrees Fahrenheit.");
		info.info_attr[6] = Attribute.init("temphigh", attr[9] + " degrees Fahrenheit.");
		
		info.parseInt(attr[10], "Humidity", out);
		info.info_attr[7] = Attribute.init("humidity", attr[10] + "%.");
		
		info.parseFloat(attr[11], "Pressure", out);
		info.info_attr[8] = Attribute.init("pressure", attr[11] + " Inch Hg.");
		
		info.parseFloat(attr[12], "Visibility", out);
		info.info_attr[9] = Attribute.init("visibility", attr[12] + " miles.");
		
		info.parseFloat(attr[13], "Wind Speed", out);
		info.parseInt(attr[14], "Wind Direction", out);
		info.info_attr[10] = Attribute.init("wind", attr[13] + " miles/hour <br //> " + attr[14] + " degrees.");
		
		info.conditionDescription = new String[attr.length - 15];
		for(int i = 15;i < attr.length;i ++)
		{
			info.conditionDescription[i-15] = attr[i];
		}
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
