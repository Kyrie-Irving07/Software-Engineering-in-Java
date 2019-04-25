import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Parallel {

		public static void search(int [] array, int target, int tNum) {
			int minNumber = 0;
			int maxNumber = array.length;
			long before = System.currentTimeMillis();
			int numThreads = tNum;
			ForkJoinPool pool = new ForkJoinPool(); // equivalent ExcecutorServlet
			SubSearch s[] = new SubSearch[numThreads];
			int start = minNumber;
			int end = maxNumber / numThreads - 1;
			for(int i =0; i < numThreads; i ++) {
				s[i] = new SubSearch(start, end, array, target, before);
				start = end + 1;
				end += (maxNumber / numThreads);
				pool.execute(s[i]);
			}
			int num = -1;
			for(int i = 0; i < numThreads; i ++) {
				num = s[i].join();
				if(num != -1)
					break;
			}
			if(num == -1) {
				long after = System.currentTimeMillis();
				System.out.println("Time with parallelism = " + (after - before) + " index: " + num);
			}

		}
	
}

