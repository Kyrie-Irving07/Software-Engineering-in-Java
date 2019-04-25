import java.util.concurrent.ThreadLocalRandom;

public class Helper {
	public static void main(String [] args) {
		int len = 1_000_000_00;
		int target = -1;//ThreadLocalRandom.current().nextInt(0, len);
		int []array = new int[len];
		
		System.out.println("Target: " + target);
		for(int i = 0; i < len; i ++)
			array[i] = i;
		
		Single.search(array, target);
		
		Multi m = new Multi(4);
		m.search(array, target);
		
		Parallel.search(array, target, 4);
	}
}
