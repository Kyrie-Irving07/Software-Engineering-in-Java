import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessageTest {
	public static void main(String []args) {
		for(int i = 0; i < 10; i ++) {
			System.out.println("#######################");
			System.out.println("Iteration " + i);
			System.out.println("#######################");
			MessageQueue que = new MessageQueue();
			Messenger msg = new Messenger(que);
			Subscriber sub = new Subscriber(que);
			ExecutorService executor = Executors.newFixedThreadPool(2);
			executor.execute(msg);
			executor.execute(sub);
			executor.shutdown();
			while(!executor.isTerminated()) {
				Thread.yield();
			}
		}
	}
}
