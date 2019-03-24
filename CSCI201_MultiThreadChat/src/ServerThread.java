import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread {
	
	private BufferedReader br;
	private PrintWriter pw;
	private ChatRoom cr;
	public ServerThread(Socket s, ChatRoom cr) {
		this.cr = cr;
		try {
			this.br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			this.pw = new PrintWriter(s.getOutputStream());
			this.start();
		} catch (IOException ioe) {
			System.out.println("ioe getting streams: " + ioe.getMessage());
		}
	}
	
	public void sendMessage(String message) {
		// br uses readline, so println is needed.
		pw.println(message);
		pw.flush();
	}
	
	public void run() {
		try {
			while(true) {
				String line = br.readLine();
				cr.broadcast(line, this);
			}
		} catch (IOException ioe) {
			System.out.println("ioe reading line: " + ioe.getMessage());
		}
	}
}
