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
	public int score;
	public String name;
	public ServerThread(Socket s, ChatRoom cr, Lock lock, Condition condition, String name, boolean first) {
		this.cr = cr;
		this.lock = lock;
		this.condition = condition;
		this.first = first;
		this.name = name;
		this.score = 0;
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
				// Take the lock
				this.lock.lock();
				
				// The first user need to specify the number of users.
				if(this.first) {
					int num = 0;
					boolean errorNum = false;
					while(num <= 0 || errorNum) {
						this.println("How many players will there be? ");
						this.pw.flush();
						String ans = br.readLine();
						errorNum = false;
						for(int i = 0; i < ans.length(); i ++)
							if(ans.charAt(i) > '9' || ans.charAt(i) < '0') {
								errorNum = true;
								break;
							}
						if(errorNum) {
							this.println("Invalid Number");
							continue;
						}
						num = Integer.parseInt(ans);
					}
					cr.set_playerNum(num);
					this.first = false;
				}
				this.condition.await();
				
				// If the game is over, jump out the dead loop.
				if(this.cr.end)
					break;
				
				// Play the game. Answer a question.
				int right = 1;
				while(right == 1 && !this.cr.end) {
					right = this.cr.data.play(this);
					if(right == 1)
						this.score += 1;
				}
				
				// There is no more questions, end the game.
				if(right == -1) {
					this.println("Found out no questions.");
					this.cr.end = true;
					break;
				}
				
				// Signal the next user to answer questions.
				this.lock.unlock();
				cr.signal_next();
				this.println("It's not your turn");
			}
			if(this.cr.Champion(this.score)) {
				this.println("You are the victor!!!");
				this.broadcast(this.name + " is the victor!!!");
			}
			this.println("Game is over. The thread ends.");
			this.lock.unlock();
			cr.signal_next();
			return;
		} catch (IOException ioe) {
			System.out.println("ioe reading line: " + ioe.getMessage());
		} catch (InterruptedException ie) {
			System.out.println("ie: " + ie.getMessage());
		}
	}
	
	public void println(String s) {
		this.pw.println(s);
		this.pw.flush();
	}
	public void print(String s) {
		this.pw.print(s);
		this.pw.flush();
	}
	public String readLine() {
		String Line = "Uninitialized";
		try {
			Line = this.br.readLine();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return Line;
	}
	public void broadcast(String s) {
		cr.broadcast(s, this);
	}
}
