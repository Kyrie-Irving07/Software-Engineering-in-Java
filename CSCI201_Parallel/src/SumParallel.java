import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class SumParallel {

		public static void main(String [] args) {
			long minNumber = 0;
			long maxNumber = 1_000_000_000;
			long before = System.currentTimeMillis();
			int numThreads = Runtime.getRuntime().availableProcessors();
			ForkJoinPool pool = new ForkJoinPool(); // equivalent ExcecutorServlet
			SumP sum[] = new SumP[numThreads];
			long start = minNumber;
			long end = maxNumber / numThreads;
			for(int i =0; i < numThreads; i ++) {
				sum[i] = new SumP(start, end);
				start = end + 1;
				end += (maxNumber / numThreads);
				pool.execute(sum[i]);
			}
			long num = 0;
			for(int i = 0; i < numThreads; i ++) {
				num += sum[i].join();
			}

			long after = System.currentTimeMillis();
			System.out.println("time with parallelism = " + (after - before));
			System.out.println("SUM(" + minNumber + ".." + maxNumber + ") = " + num);
		}
	
}

class SumP extends RecursiveTask<Long>{
	private long minNum, maxNum;
	public SumP(long minNum, long maxNum) {
		this.minNum = minNum;
		this.maxNum = maxNum;
	}
	public Long compute() {
		long sum = 0;
		for(long i=minNum; i <= maxNum; i ++) {
			sum += i;
		}
		return sum;
	}
	
}