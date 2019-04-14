import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ChatRoom {
	private Vector<ServerThread> serverThreads;
	private Vector<Lock> locks;
	private Vector<Condition> conditions;
	private int index = 0;

	public ChatRoom(int port) {
		ServerSocket ss = null;
		try {
			System.out.println("Trying to bind to port " + port);
			ss = new ServerSocket(port);
			System.out.println("Bound to port " + port);
			serverThreads = new Vector<ServerThread>();
			locks = new Vector<Lock>();
			conditions = new Vector<Condition>();
			boolean first = true;
			while(true) {
				Socket s = ss.accept();
				System.out.println("Connection from " + s.getInetAddress());
				Lock lock = new ReentrantLock();
				Condition condition = lock.newCondition();
				this.locks.add(lock);
				this.conditions.add(condition);
				ServerThread st = new ServerThread(s, this, lock, condition, first);
				first = false;
				this.serverThreads.add(st);
			}
		} catch (IOException ioe) {
			System.out.println("ieo: " + ioe.getMessage());
		} finally {
			try {
				if(ss != null) ss.close();
			} catch(IOException ioee) {
				System.out.println("ioe closing ss: " + ioee.getMessage());
			}
		}
	}
	
	public void signal_next() {
		if(this.index == this.locks.size() - 1) 
			this.index = 0;
		else
			this.index++;
		System.out.println("change to " + this.index);
		this.locks.get(index).lock();
		this.conditions.get(this.index).signal();
		this.locks.get(index).unlock();
	}
	
	public void broadcast(String message, ServerThread currentST) {
		if(message != null) {
			System.out.println(message);
			for(ServerThread st : serverThreads) {
				if(st != currentST) {
					st.sendMessage(message);
				}
			}
		}
	}
	
	public static void main(String[] args) {
		ChatRoom cr = new ChatRoom(6789);
	}
}
