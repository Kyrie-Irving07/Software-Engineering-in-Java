import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class ChatClient extends Thread {

	private BufferedReader br;
	public ChatClient() {
		Socket s = null;
		try {
			System.out.println("Welcome to 201 Crossword.");
			Scanner scan = new Scanner(System.in);
			System.out.print("Enter the server hostname: ");
			String hostname = scan.nextLine();
			System.out.print("Enter the server port: ");
			String portS = scan.nextLine();
			int port = Integer.parseInt(portS);
			
			System.out.println("Connecting to " + hostname + ":" + port);
			s = new Socket(hostname, port);
			System.out.println("Connected to " + hostname + ":" + port);
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			this.start();
			PrintWriter pw = new PrintWriter(s.getOutputStream());
			while(true) {
				String line = scan.nextLine();
				pw.println(line);
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
				if(line != null)
					System.out.println(line);
			}
		} catch (IOException ioe) {
			System.out.println("ioe reading lines: " + ioe.getMessage());
		}
	}
	
	public static void main(String[] args) {
		ChatClient cc = new ChatClient();
		
	}
}
