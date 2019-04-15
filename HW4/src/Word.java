import java.util.ArrayList;

public class Word {
	String word;
	public int x = 0;
	public int y = 0;
	public int number;
	public String position;
	public boolean answered = true;
	
	public Word(String word, int number, String position) {
		this.word = word;
		this.number = number;
		this.position = position;
	}
	
	public int[] edge() {
		int output[] = {0, 0};
		if(position.equals("ACROSS")){
			output[0] = this.x + this.word.length() - 1;
			output[1] = this.y;
		}
		else {
			output[0] = this.x;
			output[1] = this.y + this.word.length() - 1;
		}
		return output;
	}
	
	public int cross(Word w) {
		for(int i = 0; i < this.word.length(); i ++)
			for(int j = 0; j < w.word.length(); j ++)
				if(this.word.charAt(i) == w.word.charAt(j)) 
					return i;
		return -1;
	}
	
	public int index(char c) {
		for(int i = 0; i < this.word.length(); i ++)
			if(this.word.charAt(i) == c)
				return i;
		return -1;
	}
	
	public char[][] draw(char[][] board) {
		if(position.equals("ACROSS")) {
			for(int x = this.x; x < this.x + this.word.length(); x ++)
				board[x][this.y]= (this.answered)? this.word.charAt(x - this.x): '_';  
		}
		else {
			for(int y = this.y; y < this.y + this.word.length(); y ++)
				board[this.x][y]= (this.answered)? this.word.charAt(y - this.y): '_';  
		}
//		if(this.x > 0)
//			board[this.x - 1][this.y] = (char) ('0' + this.number);
		return board;
	}
}
