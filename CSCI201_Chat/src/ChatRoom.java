import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

// this is the server
public class ChatRoom {
	
	public ChatRoom(int port) {
		try {
			System.out.println("Trying to bind to port: " + port);
			ServerSocket ss = new ServerSocket(port);
			System.out.println("Bound to port " + port);
			Socket s = ss.accept();
			System.out.println("Client connected: " + s.getInetAddress());
			InputStreamReader isr = new InputStreamReader(s.getInputStream());
			BufferedReader br = new BufferedReader(isr);
			PrintWriter pw = new PrintWriter(s.getOutputStream());
			
			String line = br.readLine(); // Blocking
			System.out.println("server received: " + line);
			sendMessage(pw, "Thanks for sending me a message!");
			br.close();
			pw.close();
			s.close();
			ss.close();
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
	}
	
	public void sendMessage(PrintWriter pw, String message) {
		pw.println(message);
		pw.flush();
	}
	
	public static void main(String[] args) {
		ChatRoom cr = new ChatRoom(6789);
	}
}
