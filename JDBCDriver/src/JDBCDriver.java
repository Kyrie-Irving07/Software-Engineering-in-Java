import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class JDBCDriver {
	final static public String url = "jdbc:mysql://localhost:3306/CSCI201?user=root&password=4f6e618ec3964b0052831a5c3a40d26d&serverTimezone=UTC";
	
	static public String login(String username, String password) {
		// Output error message if error happens, otherwise null pointer.
		boolean result = false;
		boolean found = false;
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String output = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conn = DriverManager.getConnection(url);
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM Account WHERE username='" + username + "'");
			while(rs.next()) {
				found = true;
				String real_password = rs.getString("password");
				if(real_password!=null && real_password.equals(password))
					result = true;
			}
			if(!found) {
				output = "This user does not exist.";
			}
			else if(!result) {
				output = "Incorrect password.";
			}
			else
				output = null;
			
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
		return output;
	}
	
	
	static public String register(String username, String password, String confirm_password, boolean is_employer) {
		// Output error message if error happens, otherwise (Success) null pointer.
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		boolean result = true;
		String output = null;
		String status = (is_employer)? "employer":"employee";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conn = DriverManager.getConnection(url);
			st = conn.createStatement();
			rs = st.executeQuery("SELECT u.userID FROM Account u WHERE username='" + username + "'");
			while(rs.next()) {
				result = false;
			}
			
			if(!result) {
				output = "Username already taken!";
			}
			else if(!password.equals(confirm_password)) {
				output = "The passwords do not match!";
			}
			else if(password.length() < 6) {
				output = "Password length not less than 6!";
			}
			else {
				st.execute("INSERT INTO Account (username, password, status) VALUE ('" + username + "', '" + password + "', '" + status + "');");
				output = null;
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
		return output;
	}
	
	static public String updateAccount(int userID, String username, String password, String confirm_password) {
		// Input userID, new user name and new password, return error message if error happens.
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		boolean result = true;
		String output = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conn = DriverManager.getConnection(url);
			st = conn.createStatement();
			rs = st.executeQuery("SELECT u.userID FROM Account u WHERE username='" + username + "'");
			while(rs.next()) {
				if(rs.getInt("userID") != userID)
					result = false;
			}
			
			if(!result) {
				output = "Username already taken!";
			}
			else if(!password.equals(confirm_password)) {
				output = "The passwords do not match!";
			}
			else if(password.length() < 6) {
				output = "Password length not less than 6!";
			}
			else {
				st.execute("Update Account \n"
						+ "SET username = '" + username + "', password = '" + password + "'\n"
						+ "WHERE userID = '" + userID + "'");
				output = null;
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
		return output;
	}
	
	
	static public ArrayList<String> checkTime(String day, int clock) {
		// Input day and clock, then return the name of people already taken the time slot.
		ArrayList<String> people = new ArrayList<String>();
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conn = DriverManager.getConnection(url);
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM Timeslot WHERE day='" + day + "' AND clock = '" + clock + "'");
			
			while(rs.next()) {
				String status = rs.getString("status");
				if(status!=null && status.equals("taken")) {
					UserInfo info = GetUserInfo(rs.getInt("employeeID"));
					people.add(info.username);
				}
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
		return people;
	}
	
	static public UserInfo GetUserInfo(int userID) {
		// Input userID and return user's information.
		UserInfo info = new UserInfo();
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conn = DriverManager.getConnection(url);
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM Account WHERE userID='" + userID + "'");
			rs.next();
			info.password = rs.getString("password");
			info.username = rs.getString("username");
			info.status = rs.getString("status");
			info.hour_worked = rs.getInt("hour_worked");
			info.hourly_rate_of_pay = rs.getInt("hourly_rate_of_pay");
			
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
		return info;
	}


}
