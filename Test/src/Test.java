import java.lang.Exception;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Test {
	public static void main(String []args) throws Exception 
	{
//		int a = 0;
//		add_one(a);
//		System.out.println(a);
//		throw new Exception("I don't know what it is.\n");
		
		// Initialize String array by this way.
		// You can assign a String array to another String array
//		String []s = {"test ", "sucess !"};
//		String []ss = new String[2];
//		ss =s;
//		System.out.println(ss[0] + ss[1]);
//		
//		Scanner scan = new Scanner(System.in);
//		String command = scan.nextLine();
//		System.out.println(command.length());
//		if((int)command.charAt(0) > (int)'5')
//		{
//			System.out.println("Larger than 5.");
//		}
//		System.out.println(command);
//		// Print the number of char matches.
//		System.out.print(command.contentEquals("the"));
//		// Print a boolean number.
//		System.out.print(command.equals("the"));
//		scan.close();
//		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("ABC", "abc");
		String a = map.get("ab1c");
		System.out.println(a);
	}
	public static void add_one(int input)
	{
		input = input + 1;
	}
}
