import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Subscriber extends Thread {
	MessageQueue queue;
	public Subscriber(MessageQueue queue) {
		this.queue = queue;
	}
	
	public void run() {
		for(int i = 0; i < 20; i ++) {
			String s = this.queue.getMessage();
			if(s == null)
				System.out.println(getTime() + " Subscriber - Tried to read but no message...");
			while(s == null) {
				Thread.yield();
				s = this.queue.getMessage();
			}
			System.out.println(getTime() + " Subscriber - read \"" + s + "\"");  
		
			sleep(1000);
		}
	}
	
	private String getTime() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();  
		return dtf.format(now);  
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
