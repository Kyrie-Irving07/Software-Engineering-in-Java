package backend;

public class IntegerArray {
	public int array[];
	public int length;
	public IntegerArray() {
		this.array = null;
		this.length = 0;
	}
	public void add (int element) {
		int tmp[] = this.array;
		this.array = new int[this.length + 1];
		for(int i = 0; i < this.length; i ++) {
			this.array[i] = tmp[i];
		}
		this.array[this.length++] = element;
	}
}
