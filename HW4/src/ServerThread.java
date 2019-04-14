import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class ServerThread extends Thread {
	
	private BufferedReader br;
	private PrintWriter pw;
	private ChatRoom cr;
	private Lock lock;
	private Condition condition;
	private boolean first;
	public ServerThread(Socket s, ChatRoom cr, Lock lock, Condition condition, boolean first) {
		this.cr = cr;
		this.lock = lock;
		this.condition = condition;
		this.first = first;
		try {
			this.br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			this.pw = new PrintWriter(s.getOutputStream());
			this.start();
		} catch (IOException ioe) {
			System.out.println("ioe getting streams: " + ioe.getMessage());
		}
	}
	
	public void sendMessage(String message) {
		pw.println(message);
		pw.flush();
	}
	
	public void run() {
		try {
			while(true) {
				this.lock.lock();
				if(!this.first)
					this.condition.await();
				else
					this.first = false;
				this.pw.println("This is your turn");
				this.pw.flush();
				String line = br.readLine();
				while(!line.equals("END_OF_MESSAGE")) {
					cr.broadcast(line, this);
					line = br.readLine();
				}
				this.lock.unlock();
				cr.signal_next();
				this.pw.println("It's not your turn");
				this.pw.flush();
			}
		} catch (IOException ioe) {
			System.out.println("ioe reading line: " + ioe.getMessage());
		} catch (InterruptedException ie) {
			System.out.println("ie: " + ie.getMessage());
		}
	}
}
