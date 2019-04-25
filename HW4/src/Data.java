import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Data {
	public ArrayList<Question> ql;
	public ArrayList<Word> across = new ArrayList<Word>();
	public ArrayList<Word> down = new ArrayList<Word>();
	public void Parse(String filename) {
		ArrayList<Question> qList = new ArrayList<Question>();
		String position = null;
		try {
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			
			String line = br.readLine();
			int Qindex = 0;
			while(line != null) {
				if(line.equals("ACROSS") || line.equals("DOWN"))
					position = line;
				else {
					String temp[] = line.split("\\|");
					if(temp.length != 3 || temp[1].contains(" "))
						return;
					int number = parseInt(temp[0]);
					Word word = new Word(temp[1], number, position, Qindex);
					Question tempQ = new Question(number, temp[1], temp[2], position, word);
					if(position.equals("ACROSS"))
						this.across.add(word);
					else
						this.down.add(word);
					qList.add(tempQ);
					Qindex ++;
				}
				line = br.readLine();
			}
			
			fr.close();
			br.close();
		} catch(FileNotFoundException fnfe) {
			System.out.println("fnfe : " + fnfe.getMessage());
		} catch(IOException ioe) {
			System.out.println("ioe : " + ioe.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.ql = qList;
	}
	
	public int play(ServerThread st) {
		// Print Board on all player's screen
		String Board = this.rendering();
		st.broadcast(Board);
		st.println(Board);
		st.broadcast("It's " + st.name + "'s turn.");
		st.println("It's your turn now");
		String position = "";
		boolean across_remain = false;
		boolean down_remain = false;
		
		// Print all questions left on the screen
		for(int i = 0; i < ql.size(); i ++) {
			if(!position.equals(ql.get(i).position)) {
				position = ql.get(i).position;
				st.println(position);
				st.broadcast(position);
			}
			if(!ql.get(i).answered) {
				if(position.equals("ACROSS"))
					across_remain = true;
				else
					down_remain = true;
				st.println(ql.get(i).num + ". " + ql.get(i).question);
				st.broadcast(ql.get(i).num + ". " + ql.get(i).question);
			}
		}
		if(!across_remain && !down_remain) {
			st.println("Game Over");
			return -1;
		}
		
		// Choose a question to answer.
		position = "";
		Question q = new Question();
		boolean remain = false;
		int num = 0;
		boolean invalid = false;
		while((!position.equals("a") && !position.equals("d")) || !remain) {
			if(invalid)
				st.println("Invalid Input");
			st.println("Would you like to answer a question across (a) or down (d)?");
			position = st.readLine();
			if((position.equals("a") && across_remain) || (position.contentEquals("d") && down_remain))
				remain = true;
			invalid = true;
		}
		position = (position.equals("a"))? "ACROSS" : "DOWN";
		boolean validNum = false;
		invalid = false;
		while(!validNum) {
			if(invalid)
				st.println("Invalid Input");
			st.println("Which number? ");
			String line = st.readLine();
			boolean errorNum = false;
			for(int i = 0; i < line.length(); i ++)
				if(line.charAt(i) > '9' || line.charAt(i) < '0') {
					errorNum = true;
					break;
				}
			if(errorNum)
				continue;
			num = Integer.parseInt(line);
			for(int i = 0; i < ql.size(); i ++) {
				q = ql.get(i);
				if(q.position.equals(position) && q.num == num && !q.answered) {
					validNum = true;
					break;
				}
			}
			invalid = true;
		}
		
		// Answer the Question
		st.println("What is your guess for " + num + " " + position + "? ");
		String answer = st.readLine();
		boolean right = q.validate(answer);
		st.broadcast(st.name + " guessed '" + answer + "' for " + num + " " + position);
		String result = (right)? "correct" : "wrong";
		st.broadcast("That is " + result);
		st.println("That is " + result);
		int output = (right)? 1 : 0;
		return output;
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
		System.out.println("ACROSS size: " + this.across.size());
		System.out.println("DOWN size: " + this.down.size());
		ArrayList<Word> across = this.across;
		ArrayList<Word> down = this.down;
		MyClass m = new MyClass(across, down);
		m = m.iterate(0);
		String Board = m.showString();
		return Board;
	}
	
	public static void main(String arg[]) {
		Data data = new Data();
		String filename = "./gamedata/Q1";
		System.out.println("Parsing");
		data.Parse(filename);
		System.out.println("Rendering");
	}
}

