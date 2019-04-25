
public class Single {
	public static int search(int array[], int target) {
		long before = System.currentTimeMillis();
		int found = -1;
		for(int i = 0; i < array.length; i ++) {
			if(array[i] == target) {
				found = i;
				break;
			}
		}
		long after = System.currentTimeMillis();
		System.out.println("Time with single thread = " + (after - before) + " Index: " + found);
		return found;
	}
}
