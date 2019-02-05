package backend;
import java.util.Scanner;

public class City {
	String name;
	String state;
	String country;
	double latitude;
	double longitude;
	String sunrise_time;
	String sunset_time;
	int currentTemperature;
	int dayLow;
	int dayHigh;
	int humidity;
	float pressure;
	float visibility;
	float windspeed;
	int windDirection;
	Attribute [] info_attr = new Attribute[13];
	String []conditionDescription;
	
	public void show_all()
	{
		// Print 6 Attributes.
		for(int i = 0;i < 6; i ++)
			System.out.println("The " + this.info_attr[i].name + " in " + this.name  + " is " + this.info_attr[i].value);
		
		// Print the description of the city.
		System.out.print(this.name + " weather can be discribed as ");
		for(int i = 0;i < this.conditionDescription.length;i ++)
		{
			if(i == this.conditionDescription.length - 1)
				System.out.print(" and ");
			else if(i != 0)
				System.out.print(", ");
			System.out.print(this.conditionDescription[i]);
		}
		System.out.println(".");
	}
	
	public void query() throws Exception
	{
		System.out.println
		("I do have information about the weather in Los Angeles.\r\n" + 
				"1) Temperature\r\n" + 
				"2) High and low temperature today\r\n" + 
				"3) Humidity\r\n" + 
				"4) Pressure\r\n" + 
				"5) Visibility\r\n" + 
				"6) Wind speed and direction\r\n" + 
				"7) Descriptions of weather conditions\r\n" + 
				"8) Everything\r\n" + 
				"9) Enter a different city");
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		String option;
		while(true)
		{
			System.out.print("What information of " + this.name + " do you like to know? ");
			option = scan.nextLine();
			
			// Invalid Option
			if(option.length() != 1 || (int)option.charAt(0) > '9' || (int)option.charAt(0) < '0')
			{
				System.out.println("Invalid option: " + option + ". Only 1~9 are valid option.");
				continue;
			}
			int opt = Integer.parseInt(option);
			
			// To Another City
			if(opt == 9)
				break;
			
			// All Information of The City
			else if(opt == 8)
				this.show_all();
			
			// The Description of The City
			else if(opt == 7)
			{
				System.out.print(this.name + " weather can be discribed as");
				for(int i = 0;i < this.conditionDescription.length;i ++)
				{
					if(i == this.conditionDescription.length - 2)
						System.out.print(" and ");
					else if(i != 0)
						System.out.print(", ");
					System.out.print(this.conditionDescription[i]);
				}
				System.out.println(".");
			}
			
			// Some Specific Attribute
			else
				System.out.println("The " + this.info_attr[opt-1].name + " in " + this.name + " is " + this.info_attr[opt-1].value);
		}
	}
	
	public static City parse(String str) throws Exception
	{
		String []attr = str.split("\\|");
		
		// If the length of array is no larger than the number of attributes.
		// Exception raised.
		if(attr.length < 10)
		{
			throw new Exception("There are no enough parameters on line : " + str);
		}
		
		// The first element of String array is name of city.
		City info = new City();
		info.name = attr[0];
		info.info_attr[0] = Attribute.init(attr[0], attr[0]);
		
		// The next two elements are state and country of city
		info.state = attr[1];
		info.country = attr[2];
		info.info_attr[1] = Attribute.init(attr[1], attr[1]);
		info.info_attr[2] = Attribute.init(attr[2], attr[2]);
		
		// Used to save value from parsing.
		float valuef = (float) 0.0;
		int valuei = 0;
		
		// latitude and longitude of city
		valuef = parseFloat(attr[3], "latitude");
		info.latitude = valuef;
		info.info_attr[3] = Attribute.init("latitude", attr[3]);
		valuef = parseFloat(attr[4], "longitude");
		info.longitude = valuef;
		info.info_attr[4] = Attribute.init("longitude", attr[4]);
		
		// sunrise and sunset time
		parseTime(attr[5], "sunrise");
		parseTime(attr[6], "sunset");
		info.info_attr[5] = Attribute.init("sunrise_time", attr[6]);
		info.info_attr[6] = Attribute.init("sunset_time", attr[7]);
		
		// Parse the string of each attribute.
		valuei = parseInt(attr[7], "Current Temperature");
		info.currentTemperature = valuei;
		info.info_attr[7] = Attribute.init("current temperature", attr[7] + " degrees Fahrenheit.");
		
		valuei = parseInt(attr[8], "dayLow");
		info.dayLow = valuei;
		valuei = parseInt(attr[9], "dayHigh");
		info.dayHigh = valuei;
		info.info_attr[8] = Attribute.init("low and high temperature", attr[8] + " and " + attr[9] + " degrees Fahrenheit.");
		
		valuei = parseInt(attr[10], "Humidity");
		info.humidity = valuei;
		info.info_attr[9] = Attribute.init("humidity", attr[10] + "%.");
		
		valuef = parseFloat(attr[11], "Pressure");
		info.pressure = valuef;
		info.info_attr[10] = Attribute.init("pressure", attr[11] + " Inch Hg.");
		
		valuef = parseFloat(attr[12], "Visibility");
		info.visibility = valuef;
		info.info_attr[11] = Attribute.init("visibility", attr[12] + " miles.");
		
		valuef = parseFloat(attr[13], "Wind Speed");
		info.windspeed = valuef;
		valuei = parseInt(attr[14], "Wind Direction");
		info.windDirection = valuei;
		info.info_attr[12] = Attribute.init("wind speed and direction", attr[13] + " miles/hour and " + attr[14] + " degrees.");
		
		info.conditionDescription = new String[attr.length - 15];
		for(int i = 15;i < attr.length;i ++)
		{
			info.conditionDescription[i-15] = attr[i];
		}
		return info;
	}
	
	public static int parseInt(String input, String name) throws Exception
	{
		int valuei = 0;
		try
		{
			valuei = Integer.parseInt(input);
		} catch(NumberFormatException e) {
			throw new Exception("name + \" parsing exception : \" + e.getMessage()");
		}
		return valuei;
	}
	
	public static float parseFloat(String input, String name) throws Exception
	{
		float valuef = (float) 0.0;
		try
		{
			valuef = Float.parseFloat(input);
		} catch(NumberFormatException e) {
			throw new Exception(name + " parsing exception : " + e.getMessage());
		}
		return valuef;
	}

	public static boolean parseTime(String input, String name) throws Exception
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
		parseInt(time[0], "Time of sunrise and sunset");
		parseInt(time[1], "Time of sunrise and senset");
		if(!result)
			throw new Exception(name + " parsing exception");
		return result;
	}
}
