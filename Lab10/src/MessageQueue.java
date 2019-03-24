import java.util.LinkedList;
import java.util.Queue;

public class MessageQueue {
	private Queue<String> queue;
	
	public MessageQueue() {
		this.queue = new LinkedList<String>();
	}
	
	public void addMessage(String s) {
		this.queue.add(s);
	}
	
	public String getMessage() {
		if(this.queue.size() == 0)
			return null;
		return queue.remove();
	}
}
