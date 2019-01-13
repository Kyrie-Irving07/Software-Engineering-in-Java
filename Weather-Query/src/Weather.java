import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Weather {

	public static void main(String []args) throws Exception {
		System.out.print("What is the name of the weather file? ");
		Scanner scan = new Scanner(System.in);
		String filename = scan.nextLine();
		System.out.println("File name = " + filename);
		
		// Load data from file and convert it to a map.
		HashMap<String, City> map = new HashMap<String, City>();
		try {
			FileReader fr = new FileReader(filename);
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
			return;
		} catch(IOException ioe) {
			System.out.println("ioe : " + ioe.getMessage());
			scan.close();
			return;
		}

		String option = "";
		while(true)
		{
			// Read the command and parse it.
			System.out.print("For what city would you like weather information? (Press 'l' to see the city list) ");
			while(!scan.hasNextLine());
			option = scan.nextLine();
			option = option.toLowerCase();
			
			// Exit
			if(option.equals("exit"))
				break;
			
			// List of City
			else if(option.equals("l"))
			{
				for(String key : map.keySet())
					System.out.println(map.get(key).name);
			}
				
			// Show all cities information.
			else if(option.equals("all"))
			{
				for(String key : map.keySet()) 
				{
					map.get(key).show_all();
					System.out.println();
				}
			}
			
			// Specific City
			else
			{
				City city = map.get(option);
				if(city == null)
				{
					System.out.println("No such city: " + option + " in the database.");
					continue;
				}
				city.query();

			}
		}
		System.out.println("Thank you for using my weather program.");
		scan.close();
	}	
}


