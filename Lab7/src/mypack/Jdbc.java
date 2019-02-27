package mypack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpSession;

public class Jdbc {
	@SuppressWarnings("null")
	public static int validate(String username, String password) {
		int result = 0;
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		int i = 0;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Lab7?user=root&password=4f6e618ec3964b0052831a5c3a40d26d");
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM User WHERE username='" + username + "'");
			while(rs.next()) {
				i ++;
				String real_password = rs.getString("password");
				if(real_password!=null && real_password.equals(password))
					result = 1;
			}
		} catch (SQLException sqle) {
			System.out.println("sqle: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		}
		finally {
			try {
				if(rs != null) {rs.close();}
				if(st != null) {st.close();}
				if(conn != null) {conn.close();}
			} catch (SQLException sqle) {
				System.out.println("sqle closing stuff: " + sqle.getMessage());
			}
		}
		System.out.println("Log in status " +result);
		return result * i;
	}
	
	public static void increment(String pageid_s, String userid_s) {
		Connection conn0 = null;
		Connection conn1 = null;
		Connection conn2 = null;
		Statement st0 = null;
		Statement st1 = null;
		Statement st2 = null;
		ResultSet pageidset = null;
		ResultSet useridset = null;
//		System.out.println(pagename);
//		System.out.println(username);
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
//			conn0 = DriverManager.getConnection("jdbc:mysql://localhost:3306/Lab7?user=root&password=4f6e618ec3964b0052831a5c3a40d26d");
//			st0 = conn0.createStatement();
//			pageidset = st0.executeQuery("SELECT * FROM Page WHERE name='" + pagename + "'");
//			pageidset.beforeFirst();
//			int pageid = pageidset.getInt("pageID");
//			
//			conn1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/Lab7?user=root&password=4f6e618ec3964b0052831a5c3a40d26d");
//			st1 = conn1.createStatement();
//			useridset = st1.executeQuery("SELECT * FROM User WHERE username='" + username + "'");
//			useridset.beforeFirst();
//			int userid = useridset.getInt("UserID");
//			
//			System.out.println(pageid + ' ' + userid);
//			
//			String pageid_s = String.valueOf(pageid);
//			String userid_s = String.valueOf(userid);
//			
//			System.out.println(pageid_s + ' ' + userid_s);
			
			conn2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/Lab7?user=root&password=4f6e618ec3964b0052831a5c3a40d26d");
			st2 = conn2.createStatement();
			st2.executeUpdate("INSERT INTO PageVisited (UserID, pageID) VALUES ('" + pageid_s + "', '" + userid_s + "')");
//			st2.getresult();
//			st2.execute("create if not exist table abc ");
		} catch (SQLException sqle) {
			System.out.println("sqle: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		}
		finally {
		}
	}
}
