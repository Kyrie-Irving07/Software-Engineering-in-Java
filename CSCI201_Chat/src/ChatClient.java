import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {

	public ChatClient(String hostname, int port) {
		try {
			System.out.println("Trying to connect to " + hostname + ":" + port);
			Socket s = new Socket(hostname, port);
			System.out.println("Connected to " + hostname + ":" + port);
			InputStreamReader isr = new InputStreamReader(s.getInputStream());
			BufferedReader br = new BufferedReader(isr);
			PrintWriter pw = new PrintWriter(s.getOutputStream());
			sendMessage(pw, "Hello from the Client");
			String line = br.readLine();
			System.out.println("received from server: " + line);
			pw.close();
			br.close();
			s.close();
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
	}
	
	public void sendMessage(PrintWriter pw, String message) {
		pw.println(message);
		pw.flush();
	}
	
	public static void main(String[] args) {
		ChatClient c = new ChatClient("localhost", 6789);
	}
}
