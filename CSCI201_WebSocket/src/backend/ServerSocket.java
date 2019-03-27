package backend;

import java.io.IOException;
import java.util.Vector;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint (value="/chat")
public class ServerSocket {
	
	private static Vector<Session> sessionVector = new Vector<Session>();
	
	@OnOpen
	public void open(Session session) {
		System.out.println("Connection made!");
		sessionVector.add(session);
	}
	@OnMessage
	public void onMessage(String message, Session session) {
		System.out.println("Message received: " + message);
		for(Session s : sessionVector) {
			try {
				s.getBasicRemote().sendText(message);
			} catch (IOException ioe) {
				System.out.println("IOE: " + ioe.getMessage());
			}
		}
	}
	@OnClose
	public void close(Session session) {
		System.out.println("Disconnect!");
		sessionVector.remove(session);
	}
	@OnError
	public void error(Throwable error) {
		System.out.println("Error");
	}

}
