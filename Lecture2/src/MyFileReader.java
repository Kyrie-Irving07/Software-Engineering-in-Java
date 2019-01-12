import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class MyFileReader {
	
	public static void main(String [] args) {
		System.out.print("Enter the name of the file to read: ");
		Scanner scan = new Scanner(System.in);
		String filename = scan.nextLine();
		
		System.out.print("filename = " + filename);
		scan.close();
		
		try {
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			while(line != null) {
				System.out.println(line);
				line = br.readLine();
			}
			br.close();
			fr.close();
		} catch (FileNotFoundException fnfe) {
			System.out.println("fnfe: " + fnfe.getMessage());
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
		
	}
}
