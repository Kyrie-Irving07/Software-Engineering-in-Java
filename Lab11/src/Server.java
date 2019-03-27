import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server {
	private Vector<ServerThread> serverThreads;
	
	public Server(int port) {
		ServerSocket ss = null;
		System.out.println("Binding the port");
		try {
			ss = new ServerSocket(port);
			System.out.println("Bound to port: " + port);
			serverThreads = new Vector<ServerThread>();
			
			while(true) {
				Socket s = ss.accept();
				System.out.println("Connection from " + s.getInetAddress());
				ServerThread st = new ServerThread(s, this);
				this.serverThreads.add(st);
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if(ss != null) ss.close();
			} catch(IOException ioee) {
				System.out.println("IOEE closing: " + ioee.getMessage());
			}
		}
	}
	
	public static void main(String[] args) {
		Server s = new Server(6789);
	}
	
}
