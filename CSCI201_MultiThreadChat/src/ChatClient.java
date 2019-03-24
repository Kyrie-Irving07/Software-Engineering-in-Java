import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient extends Thread {

	private BufferedReader br;
	public ChatClient(String hostname, int port) {
		Socket s = null;
		try {
			System.out.println("Connecting to " + hostname + ":" + port);
			s = new Socket(hostname, port);
			System.out.println("Connected to " + hostname + ":" + port);
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			this.start();
			PrintWriter pw = new PrintWriter(s.getOutputStream());
			Scanner scan = new Scanner(System.in);
			while(true) {
				String line = scan.nextLine();
				pw.println("IOException: " + line);
				pw.flush();
			}
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		} finally {
			try {
				if (s != null) s.close();
			} catch (IOException ioe) {
				System.out.println("ioe closing socket: " + ioe.getMessage());
			}
		}
	}
	
	public void run() {
		try {
			while(true) {
				String line = br.readLine();
				System.out.println(line);
			}
		} catch (IOException ioe) {
			System.out.println("ioe reading lines: " + ioe.getMessage());
		}
	}
	
	public static void main(String[] args) {
		ChatClient cc = new ChatClient("192.168.1.148", 6789);
		
	}
}
