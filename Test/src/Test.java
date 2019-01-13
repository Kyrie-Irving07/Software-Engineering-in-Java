import java.lang.Exception;


public class Test {
	public static void main(String []args) throws Exception 
	{
		String str = "Los Angeles|68|54|72|35|29.80|88.3|8.4|270|sunny|breezy|clear";
		String []attr = str.split("\\|");  // Split by | + * \ needs prefix \\
		System.out.print("Length of Attributes : ");
		System.out.println(attr.length);
		for(int i = 0;i < attr.length;i ++) 
		{
			System.out.print(attr[i] + " . ");
		}
		System.out.print("\n");
//		throw new Exception("I don't know what it is.\n");
		
		// Initialize String array by this way.
		// You can assign a String array to another String array
		String []s = {"test ", "sucess !"};
		String []ss = new String[2];
		ss =s;
		System.out.println(ss[0] + ss[1]);
		
		float t = (float) 0.0;
		try
		{
			t = Float.parseFloat("29.80");
		} catch(NumberFormatException e) {
			System.out.println("Parsing exception : " + e.getMessage());
		}
		System.out.println(t);
	}
}
