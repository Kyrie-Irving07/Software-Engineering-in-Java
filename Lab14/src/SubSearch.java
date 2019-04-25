import java.util.concurrent.RecursiveTask;

class SubSearch extends RecursiveTask<Integer>{
	private int minNum, maxNum;
	private int target;
	private int [] array;
	private long start;
	public SubSearch(int minNum, int maxNum, int []array, int target, long start) {
		this.minNum = minNum;
		this.maxNum = maxNum;
		this.array = array;
		this.target = target;
		this.start = start;
	}
	public Integer compute() {
		int index = -1;
		for(int i=minNum; i <= maxNum; i ++) {
			if(this.target == this.array[i]) {
				index = i;
				break;
			}
		}
		if(index != -1) {
			
			
			long after = System.currentTimeMillis();
			System.out.println(start);
			System.out.println(after);
			System.out.println("Time with parallelism = " + (after - start) + " index: " + index);
		}
		return index;
	}
	
}