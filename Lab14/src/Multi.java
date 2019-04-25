import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Multi {
	public int found = -1;
	public int tNum;
	public ArrayList<SubM> subs = new ArrayList<SubM>();
	
	public Multi(int tNum) {
		this.tNum = tNum;
	}
	
	public int search(int array[], int target) {
		long before = System.currentTimeMillis();
		ExecutorService executor = Executors.newFixedThreadPool(tNum);
		for(int i = 0; i < tNum; i ++) {
			int sublen = array.length / tNum;
			int offset = sublen * i;
			int subarray[] = new int [sublen];
			for(int j = 0; j < sublen; j ++) {
				subarray[j] = array[j+offset];
			}
			SubM s = new SubM(subarray, target, this, before);
			this.subs.add(s);
			executor.execute(s);
		}
		executor.shutdown();
		while(!executor.isTerminated()) {
			Thread.yield();
		}
		if(this.found == -1) {
			long after = System.currentTimeMillis();
			System.out.println("Time with multi-thread = " + (after - before) + " Index: " + this.found);
		}
		return this.found;
	}
	
	public void StopAll() {
		for(int i = 0; i < subs.size(); i ++)
			subs.get(i).Stop();
	}
}

class SubM extends Thread{
	private int [] array;
	private int target;
	private boolean stop;
	private long before;
	public Multi m;
	
	public SubM(int array[], int target, Multi m, long before) {
		this.array = array;
		this.target = target;
		this.stop = false;
		this.m = m;
		this.before = before;
	}
	
	public void Stop() {
		this.stop = true;
	}
	
	public void run() {
		for(int i = 0; i < this.array.length && !this.stop; i ++) {
			if(array[i] == this.target) {
				long after = System.currentTimeMillis();
				System.out.println("Time with Multi = " + (after - this.before) + " index: " + target);
				this.m.found = i;
				this.m.StopAll();
				break;
			}
		}
	}
}
