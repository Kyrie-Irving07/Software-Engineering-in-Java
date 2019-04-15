import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Data {
	public ArrayList<Question> ql;
	public ArrayList<Word> across = new ArrayList<Word>();
	public ArrayList<Word> down = new ArrayList<Word>();
	public ArrayList<Question> Parse(String filename) {
		ArrayList<Question> qList = new ArrayList<Question>();
		String position = null;
		try {
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			
			String line = br.readLine();
			while(line != null) {
				if(line.equals("ACROSS") || line.equals("DOWN"))
					position = line;
				else {
					String temp[] = line.split("\\|");
					if(temp.length != 3)
						return null;
					if(temp[1].contains(" "))
						return null;
					int number = parseInt(temp[0]);
					Question tempQ = new Question(number, temp[1], temp[2]);
					if(position.equals("ACROSS"))
						this.across.add(new Word(temp[1], number, position));
					else
						this.down.add(new Word(temp[1], number, position));
					qList.add(tempQ);
				}
				line = br.readLine();
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
		this.ql = qList;
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
	
	public String rendering() {
		ArrayList<Word> across = this.across;
		ArrayList<Word> down = this.down;
		ArrayList<Word> adone = new ArrayList<Word> ();
		ArrayList<Word> ddone = new ArrayList<Word> ();
		adone.add(across.remove(0));
		
		while(across.size()!=0 || down.size()!= 0) {
			for(int i = 0; i < adone.size(); i ++)
				for(int j = 0; j < down.size(); j ++) {
					Word a = adone.get(i);
					Word d = down.get(j);
					if(a.cross(d) > -1) {
						char c = a.word.charAt(a.cross(d));
						d.x = a.x + a.cross(d);
						if(d.index(c) > a.y) {
							d.y = 0;
							int dis = d.index(c) - a.y;
							for(int ii = 0; ii < adone.size(); ii ++) {
								Word aa = adone.get(ii);
								aa.y += dis;
							}
							for(int ii = 0; ii < ddone.size(); ii ++) {
								Word dd = ddone.get(ii);
								dd.y += dis;
							}
						}
						else {
							d.y = a.y - d.index(c);
						}
						down.remove(j);
						ddone.add(d);
					}
				}
			for(int i = 0; i < ddone.size(); i ++)
				for(int j = 0; j < across.size(); j ++) {
					Word a = across.get(j);
					Word d = ddone.get(i);
					if(d.cross(a) > -1) {
						char c = d.word.charAt(d.cross(a));
						a.y = d.y + d.cross(a);
						if(a.index(c) > d.x) {
							d.x = 0;
							int dis = a.index(c) - d.x;;
							for(int ii = 0; ii < adone.size(); ii ++) {
								Word aa = adone.get(ii);
								aa.x += dis;
							}
							for(int ii = 0; ii < ddone.size(); ii ++) {
							 	Word dd = ddone.get(ii);
								dd.x += dis;
							}
						}
						else {
							a.x = d.x - a.index(c);
						}
						across.remove(j);
						adone.add(a);
					}
				}
		}
		
		int xrange = 0;
		int yrange = 0;
		for(int i = 0; i < adone.size(); i ++) {
			int edge[] = adone.get(i).edge();
			if(xrange < edge[0])
				xrange = edge[0];
			if(yrange < edge[1])
				yrange = edge[1];
		}
		for(int i = 0; i < ddone.size(); i ++) {
			int edge[] = ddone.get(i).edge();
			if(xrange < edge[0])
				xrange = edge[0];
			if(yrange < edge[1])
				yrange = edge[1];
		}
		char board[][] = new char[xrange+1][yrange+1];
		for(int i = 0; i < xrange; i ++)
			for(int j = 0; j < yrange; j ++)
				board[i][j] = ' ';
		for(int i = 0; i < adone.size(); i ++)
			board = adone.get(i).draw(board);
		for(int i = 0; i < ddone.size(); i ++)
			board = ddone.get(i).draw(board);
		
		String Board = "";
		for(int i = 0; i < yrange; i ++) {
			for(int j = 0; j < xrange; j ++)
				Board += " " + board[j][i] + " ";
			Board += "\n";
		}
		return Board;
		
	}
	
	public static void main(String arg[]) {
		Data data = new Data();
		String filename = "./gamedata/Q1";
		System.out.println("Parsing");
		data.Parse(filename);
		System.out.println("Rendering");
		String board = data.rendering();
		System.out.println(board);
	}
}

