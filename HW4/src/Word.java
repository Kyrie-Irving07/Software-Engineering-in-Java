import java.util.ArrayList;

public class Word implements Cloneable  {
	String word;
	public int x = 0;
	public int y = 0;
	public int number;
	public String position;
	public boolean answered = false;
	public int Qindex = 0;
	
	public Word(String word, int number, String position, int Qindex) {
		this.word = word;
		this.number = number;
		this.position = position;
		this.Qindex = Qindex;
	}
	
	public Object clone() throws CloneNotSupportedException 
	{ 
		return super.clone(); 
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
	
	public ArrayList<Pair> cross(Word w) {
		ArrayList<Pair> out = new ArrayList<Pair>();
		for(int i = 0; i < this.word.length(); i ++)
			for(int j = 0; j < w.word.length(); j ++)
				if(this.word.charAt(i) == w.word.charAt(j)) {
					Pair p = new Pair(i, j);
					out.add(p);
				}	
		return out;
	}
	
	public Pair p_cross(Word w) {
		Pair p = new Pair(-1, -1);
		Word aw = (w.position.equals("ACROSS"))? w : this;
		Word dw = (w.position.equals("DOWN"))? w : this;
		if(aw.x <= dw.x && dw.x <= aw.x + aw.word.length() - 1 &&
		   dw.y <= aw.y && dw.y + dw.word.length() - 1 > aw.y) {
			p = new Pair(dw.x - aw.x, aw.y - dw.y);
		}
		if(aw == w) {
			int temp = p.x;
			p.x = p.y;
			p.y = temp;
		}
		return p;
	}
	
	public boolean same_posi_crash(Word w) {
		if(w.position.equals("ACROSS")) {
			if(w.y != this.y)
				return false;
			else {
				int begin = (this.x > w.x)? this.x : w.x;
				int end = (this.x + this.word.length() - 1 < w.x + w.word.length() - 1)?
						this.x + this.word.length() - 1 : w.x + w.word.length() - 1;

				for(int i = begin; i <= end; i ++) {
					if(this.word.charAt(i - this.x) != w.word.charAt(i - w.x))
						return true;
				}
			}
		}
		else {
			if(w.x != this.x)
				return false;
			else {
				int begin = (this.y > w.y)? this.y : w.y;
				int end = (this.y + this.word.length() - 1 < w.y + w.word.length() - 1)?
						this.y + this.word.length() - 1 : w.y + w.word.length() - 1;

				for(int i = begin; i <= end; i ++) {
					if(this.word.charAt(i - this.y) != w.word.charAt(i - w.y))
						return true;
				}
			}
		}
		
		return false;
	}
	
	public boolean crash(Word w) {
		Pair p = this.p_cross(w);
		if(p.No() || (this.word.charAt(p.x) == w.word.charAt(p.y)))
			return false;
		return true;
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
				if(board[x][this.y] == ' ' || this.is_num(board[x][this.y]) || board[this.x][y] == '_')
					board[x][this.y]= (this.answered)? this.word.charAt(x - this.x): '_';  
		}
		else {
			for(int y = this.y; y < this.y + this.word.length(); y ++)
				if(board[this.x][y] == ' ' || this.is_num(board[this.x][y]) || board[this.x][y] == '_')
					board[this.x][y]= (this.answered)? this.word.charAt(y - this.y): '_';  
		}
//		if(this.x > 0 && board[this.x - 1][this.y] == ' ')
//			board[this.x - 1][this.y] = (char) ('0' + this.number);
//		else if(this.y > 0 && board[this.x][this.y - 1] == ' ')
//			board[this.x][this.y - 1] = (char) ('0' + this.number);
		return board;
	}
	
	private boolean is_num(char c) {
		return (c >= '0' && c <= '9');
	}
}
