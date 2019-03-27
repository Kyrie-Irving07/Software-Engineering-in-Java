import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;

public class ServerThread extends Thread {
	private BufferedReader br;
	private PrintWriter pw;
	private Server cr;
	public ServerThread(Socket s, Server cr) {
		this.cr = cr;
		try {
			this.br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			this.pw = new PrintWriter(s.getOutputStream());
			this.start();
		} catch (IOException ioe) {
			System.out.println("ioe getting streams: " + ioe.getMessage());
		}
	}
	
	public void run() {
		try {
			while(true) {
				String line = br.readLine();
				if(line != null) {
					if(line.startsWith("GET")) {
						System.out.println("Enter Parse");
						// Parse
						String [] array = line.split("/");
						String [] sec = array[1].split(" ");
						String dest = sec[0];
						System.out.println("Request: " + dest);

						File empty = new File("");
						String path = empty.getAbsolutePath();
						File f = new File(path + "/WebContent/" + dest);
						System.out.println("Corss");
						if(f.exists() && !f.isDirectory()) { 
							System.out.println("Existing");
							pw.println("HTTP/1.1 200 OK ");
							if(dest.endsWith("html")) {
								System.out.println("File is html");
								pw.println("Content-Type: text/html \r\n\r\n");
							}
							else {
								System.out.println("File is something else.");
								pw.println("Content-Type: image \r\n\r\n");
							}
				            byte[] fileContent = Files.readAllBytes(f.toPath());
				            for(byte b: fileContent)
				            	this.pw.write((int) b);
						}
						else {
							System.out.println("Nofile");
							pw.println("HTTP/1.1 404 Not Found");
							pw.println("Connection: Closed");
							pw.println("Content-Type: text/html;  charset=iso-8859-1 \r\n\r\n");
							break;
						}
						pw.flush();
					}
				}
			}
		} catch (IOException ioe) {
			System.out.println("ioe reading line: " + ioe.getMessage());
		}
		System.out.println("Thread exit");
		pw.close();
	}
}
