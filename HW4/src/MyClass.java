import java.util.ArrayList;

public class MyClass {
	public ArrayList<Word> across;
	public ArrayList<Word> down;
	public ArrayList<Word> adone;
	public ArrayList<Word> ddone;
	boolean valid;
	public MyClass(ArrayList<Word> across, ArrayList<Word> down,
				   ArrayList<Word> adone,  ArrayList<Word> ddone) {
		this.across = cloneArray(across);;
		this.down = cloneArray(down);
		this.adone = cloneArray(adone);
		this.ddone = cloneArray(ddone);
		this.valid = true;
	}
	
	public MyClass(ArrayList<Word> across, ArrayList<Word> down) {
	this.across = cloneArray(across);;
	this.down = cloneArray(down);
	this.adone = new ArrayList<Word> ();
	this.ddone = new ArrayList<Word> ();
	this.adone.add(this.across.remove(0));
	this.valid = true;
}
	
	public MyClass(MyClass m) {
		this.across = cloneArray(m.across);
		this.down = cloneArray(m.down);
		this.adone = cloneArray(m.adone);
		this.ddone = cloneArray(m.ddone);
	}
	
	public ArrayList<Word> cloneArray(ArrayList<Word> l){
		ArrayList<Word> newl = new ArrayList<Word>();
		try {
			for(int i = 0; i < l.size(); i ++) {
					Word nw = (Word) l.get(i).clone();
					newl.add(nw);
			}
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newl;
	}
	
	public void show() {
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
		for(int i = 0; i <= xrange; i ++)
			for(int j = 0; j <= yrange; j ++)
				board[i][j] = ' ';
		for(int i = 0; i < adone.size(); i ++)
			board = adone.get(i).draw(board);
		for(int i = 0; i < ddone.size(); i ++)
			board = ddone.get(i).draw(board);
		
		String Board = "";
		for(int i = 0; i <= yrange; i ++) {
			for(int j = 0; j <= xrange; j ++){
				int n = is_begin(j, i);
				String left = (n < 0)? " " : "" + n;
				Board += left + board[j][i] + " ";
			}
			Board += "\n";
		}
		System.out.print(Board);
		System.out.print("ACROSS LEFT: ");
		for(int i = 0; i < this.across.size(); i ++)
			System.out.print(this.across.get(i).word + " | ");
		System.out.print("\nDOWN LEFT: ");
		for(int i = 0; i < this.down.size(); i ++)
			System.out.print(this.down.get(i).word + " | ");
		System.out.println("\n");
	}
	
	public String showString() {
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
		for(int i = 0; i <= xrange; i ++)
			for(int j = 0; j <= yrange; j ++)
				board[i][j] = ' ';
		for(int i = 0; i < adone.size(); i ++)
			board = adone.get(i).draw(board);
		for(int i = 0; i < ddone.size(); i ++)
			board = ddone.get(i).draw(board);
		
		String Board = "";
		for(int i = 0; i <= yrange; i ++) {
			for(int j = 0; j <= xrange; j ++) {
				int n = is_begin(j, i);
				String left = (n < 0)? " " : "" + n;
				Board += left + board[j][i] + " ";
			}
			Board += "\n";
		}
		return Board;
	}
	
	public int is_begin(int x, int y) {
		for(int i = 0; i < this.adone.size(); i ++)
			if(this.adone.get(i).x == x && this.adone.get(i).y == y)
				return adone.get(i).number;
		for(int i = 0; i < this.ddone.size(); i ++)
			if(this.ddone.get(i).x == x && this.ddone.get(i).y == y)
				return ddone.get(i).number;
		return -1;
	}
	
	public boolean crash(Word w, boolean is_across) {
		ArrayList<Word> list = (is_across)? this.ddone : this.adone;
		for(int i = 0; i < list.size(); i ++)
			if(list.get(i).crash(w))
				return true;
		list = (is_across)? this.adone : this.ddone;
		for(int i = 0; i < list.size(); i ++)
			if(list.get(i).same_posi_crash(w))
				return true;
		return false;
	}
	
	public MyClass iterate(int layer) {
		MyClass temp = new MyClass(this);
		if(this.across.isEmpty() && this.down.isEmpty()) {
			temp.valid = true;
			return temp;
		}
		
		for(int i = 0; i < temp.adone.size(); i ++)
			for(int j = 0; j < temp.down.size(); j ++) {
				Word a = temp.adone.get(i);
				Word d = temp.down.get(j);
				ArrayList<Pair> p = a.cross(d);
				for(int k = 0; k < p.size(); k ++) {
					temp = new MyClass(this);
					a = temp.adone.get(i);
					d = temp.down.get(j);
					Pair pair = p.get(k);
					d.x = a.x + pair.x;
					if(pair.y > a.y) {
						d.y = 0;
						int dis = pair.y - a.y;
						for(int ii = 0; ii < temp.adone.size(); ii ++) {
							Word aa = temp.adone.get(ii);
							aa.y += dis;
						}
						for(int ii = 0; ii < temp.ddone.size(); ii ++) {
							Word dd = temp.ddone.get(ii);
							dd.y += dis;
						}
					}
					else {
						d.y = a.y - pair.y;
					}
					if(temp.crash(d, false)) {
						continue;
					}
					temp.down.remove(j);
					temp.ddone.add(d);
//					System.out.println("Layer: " + layer);
//					temp.show();
					temp = temp.iterate(layer + 1);
					if(temp.valid)
						return temp;
				}
			}
		for(int i = 0; i < temp.ddone.size(); i ++)
			for(int j = 0; j < temp.across.size(); j ++) {
				Word a = temp.across.get(j);
				Word d = temp.ddone.get(i);
				ArrayList<Pair> p = a.cross(d);
				for(int k = 0; k < p.size(); k ++) {
					temp = new MyClass(this);
					a = temp.across.get(j);
					d = temp.ddone.get(i);
					Pair pair = p.get(k);
					a.y = d.y + pair.y;
					if(pair.x > d.x) {
						d.x = 0;
						int dis = pair.x - d.x;;
						for(int ii = 0; ii < temp.adone.size(); ii ++) {
							Word aa = temp.adone.get(ii);
							aa.x += dis;
						}
						for(int ii = 0; ii < temp.ddone.size(); ii ++) {
						 	Word dd = temp.ddone.get(ii);
							dd.x += dis;
						}
					}
					else {
						a.x = d.x - pair.x;
					}
					if(temp.crash(a, true)) {
						continue;
					}
					temp.across.remove(j);
					temp.adone.add(a);
//					System.out.println("Layer: " + layer);
//					temp.show();
					temp = temp.iterate(layer + 1);
					if(temp.valid)
						return temp;
				}
			}
		temp.valid = false;
		return temp;
	}
}
