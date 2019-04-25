import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ChatRoom {
	private Vector<ServerThread> serverThreads;
	private Vector<Lock> locks;
	private Vector<Condition> conditions;
	private int index = 0;
	private int playerNum = -1;
	public Data data = new Data();
	public boolean end = false;

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
				// Prepare data
				ArrayList<String> filesArray = new ArrayList<String>();
				File[] files = new File("./gamedata/").listFiles();
				for (File file : files) {
				    if (file.isFile() && !file.getName().equals(".DS_Store")) {
				    	filesArray.add(file.getName());
				    }
				}
				Random rand = new Random();
				int rand_index = rand.nextInt(filesArray.size());
				this.data = new Data();
				this.data.Parse("./gamedata/" + filesArray.get(rand_index));
				System.out.println("Randomly pick file: " + "./gamedata/" + filesArray.get(rand_index));
				
				// The first player.
				Socket s1 = ss.accept();
				System.out.println("The first user: Connection from " + s1.getInetAddress());
				Lock lock1 = new ReentrantLock();
				Condition condition1 = lock1.newCondition();
				this.locks.add(lock1);
				this.conditions.add(condition1);
				ServerThread st1 = new ServerThread(s1, this, lock1, condition1, "player 1", first);
				first = false;
				this.serverThreads.add(st1);
				while(this.playerNum == -1)
					Thread.yield();
				for(int i = 1; i < this.playerNum; i ++) {
					this.broadcast("Waiting for player " + (i + 1), null);
					Socket s = ss.accept();
					System.out.println("Connection from " + s.getInetAddress());
					Lock lock = new ReentrantLock();
					Condition condition = lock.newCondition();
					this.locks.add(lock);
					this.conditions.add(condition);
					ServerThread st = new ServerThread(s, this, lock, condition, "player " + (i + 1), first);
					first = false;
					this.serverThreads.add(st);
				}
				this.index = -1;
				this.signal_next();
				for(int i = 0; i < this.serverThreads.size(); i ++) {
					this.serverThreads.get(i).join();
					System.out.println(this.serverThreads.get(i).name + " has stopped");
				}
				System.out.println("Next Game");
				first = true;
				this.end = false;
				this.playerNum = -1;
				this.clear();
			}
		} catch (IOException ioe) {
			System.out.println("ieo: " + ioe.getMessage());
		} catch (InterruptedException e) {
			System.out.println("ie: " + e.getMessage());
		} finally {
			try {
				if(ss != null) ss.close();
			} catch(IOException ioee) {
				System.out.println("ioe closing ss: " + ioee.getMessage());
			}
		}
	}
	
	private void clear() {
		this.serverThreads.clear();
		this.locks.clear();
		this.conditions.clear();
	}
	
	public void signal_next() {
		if(this.index == this.locks.size() - 1) 
			this.index = 0;
		else
			this.index++;
		this.locks.get(index).lock();
		this.conditions.get(this.index).signal();
		this.locks.get(index).unlock();
	}
	
	public void set_playerNum(int num) {
		this.playerNum = num;
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
	
	public boolean Champion(int score) {
		for(int i = 0; i < this.serverThreads.size(); i ++)
			if(this.serverThreads.get(i).score > score)
				return false;
		return true;
	}
	
	public static void main(String[] args) {
		ChatRoom cr = new ChatRoom(3456);
	}

}
