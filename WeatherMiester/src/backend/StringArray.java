package backend;

public class StringArray {
	public String array[];
	public int length;
	
	public StringArray() {
		this.array = null;
		this.length = 0;
	}
	
	public void add (String element) {
		String temp[] = this.array;
		if(temp != null)
			this.array = new String[temp.length + 1];
		else
			this.array = new String[1];
		int i;
		for(i = 0; temp!=null && i < temp.length; i ++) {
			this.array[i] = temp[i];
		}
		this.array[i] = element;
		this.length = this.array.length;
	}
}
