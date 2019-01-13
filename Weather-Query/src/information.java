
public class information {
	String city;
	int currentTemperature;
	int dayLow;
	int dayHigh;
	int humidity;
	float pressure;
	float visibility;
	float windspeed;
	int windDirection;
	String []conditionDescription;
	
	public static information parse(String str) throws Exception
	{
		String []attr = str.split("\\|");
		
		// If the length of array is no larger than the number of attributes.
		// Exception raised.
		if(attr.length < 10)
		{
			throw new Exception("There are no enough parameters on line : " + str);
		}
		
		// The first element of String array is name of city.
		information info = new information();
		info.city = attr[0];
		
		// Used to save value from parsing.
		float valuef = (float) 0.0;
		int valuei = 0;
		
		// Parse the string of each attribute.
		valuei = parseInt(attr[1], "Current Temperature");
		info.currentTemperature = valuei;
		valuei = parseInt(attr[2], "dayLow");
		info.dayLow = valuei;
		valuei = parseInt(attr[3], "dayHigh");
		info.dayHigh = valuei;
		valuei = parseInt(attr[4], "Humidity");
		info.humidity = valuei;
		valuef = parseFloat(attr[5], "Pressure");
		info.pressure = valuef;
		valuef = parseFloat(attr[6], "Visuability");
		info.visibility = valuef;
		valuef = parseFloat(attr[7], "Wind Speed");
		info.windspeed = valuef;
		valuei = parseInt(attr[8], "Wind Direction");
		info.windDirection = valuei;
		info.conditionDescription = new String[attr.length - 8];
		for(int i = 9;i < attr.length;i ++)
		{
			info.conditionDescription[i-9] = attr[i];
		}
		return info;
	}
	
	public static int parseInt(String input, String name)
	{
		int valuei = 0;
		try
		{
			valuei = Integer.parseInt(input);
		} catch(NumberFormatException e) {
			System.out.println(name + " parsing exception : " + e.getMessage());
		}
		return valuei;
	}
	
	public static float parseFloat(String input, String name)
	{
		float valuef = (float) 0.0;
		try
		{
			valuef = Float.parseFloat(input);
		} catch(NumberFormatException e) {
			System.out.println(name + " parsing exception : " + e.getMessage());
		}
		return valuef;
	}
	
}
