import java.lang.Exception;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


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
//		String []s = {"test ", "sucess !"};
//		String []ss = new String[2];
//		ss =s;
//		System.out.println(ss[0] + ss[1]);
		
		Scanner scan = new Scanner(System.in);
		String command = scan.nextLine();
		System.out.println(command.length());
		if((int)command.charAt(0) > (int)'5')
		{
			System.out.println("Larger than 5.");
		}
		System.out.println(command);
		// Print the number of char matches.
		System.out.print(command.contentEquals("the"));
		// Print a boolean number.
		System.out.print(command.equals("the"));
		scan.close();
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("ABC", "abc");
		String a = map.get("abc");
		System.out.println(a);
	}
}
