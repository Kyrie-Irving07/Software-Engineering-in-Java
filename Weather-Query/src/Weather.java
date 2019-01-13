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
		scan.close();
		
		// Load data from file and convert it to a map.
		HashMap<String, information> map = new HashMap<String, information>();
		try {
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			
			String line = br.readLine();
			while(line != null) {
				information info = information.parse(line);
				map.put(info.city.toLowerCase(), info);
				line = br.readLine();
			}
			
			fr.close();
			br.close();
		} catch(FileNotFoundException fnfe) {
			System.out.println("fnfe : " + fnfe.getMessage());
		} catch(IOException ioe) {
			System.out.println("ioe : " + ioe.getMessage());
		}
		
		
		
	}

	
}


