import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Data {
	static public ArrayList<Question> Parse(String filename) {
		ArrayList<Question> qList = new ArrayList<Question>();
		String position = null;
		try {
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			
			String line = br.readLine();
			while(line != null) {
				line = br.readLine();
				if(line.contentEquals("ACROSS") || line.contentEquals("DOWN"))
					position = line;
				else {
					String temp[] = line.split("\\|");
					if(temp.length != 3)
						return null;
					if(temp[1].contains(" "))
						return null;
					int number = parseInt(temp[0]);
					Question tempQ = new Question(number, temp[1], temp[2]);
					
				}
			}
			
			fr.close();
			br.close();
		} catch(FileNotFoundException fnfe) {
			System.out.println("fnfe : " + fnfe.getMessage());
			return null;
		} catch(IOException ioe) {
			System.out.println("ioe : " + ioe.getMessage());
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return qList;
	}
	
	public static int parseInt(String input) throws Exception
	{
		int valuei = 0;
		try
		{
			valuei = Integer.parseInt(input);
		} catch(NumberFormatException e) {
			throw new Exception("parsing exception : \" + e.getMessage()");
		}
		return valuei;
	}
}

class Word {
	public int x;
	public int y;
	public int height;
	public String position;
	public Word(int x, int y, String position) {
		this.x = x;
		this.y = y;
		this.position = position;
	}
}
