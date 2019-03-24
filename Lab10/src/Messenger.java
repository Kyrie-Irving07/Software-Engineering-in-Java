import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Messenger extends Thread {
	MessageQueue queue;
	public Messenger(MessageQueue queue) {
		this.queue = queue;
	}
	
	public void run() {
		for(int i = 0; i < 20; i ++) {
			this.queue.addMessage("message #" + i);
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
			LocalDateTime now = LocalDateTime.now();  
			System.out.println(dtf.format(now) + " Messenger - insert \"" + "message #" + i + "\"");  
			
			sleep(1000);
		}
	}
	
	private void sleep(int range) {
		// Sleep for 0 ~ 1 second
		Random rand = new Random(); 
		int millis = rand.nextInt(range); 
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
