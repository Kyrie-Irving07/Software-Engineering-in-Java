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
				st.execute("INSERT INTO Account (username, password, status, hours_worked, hourly_rate_of_pay) VALUE ('" + username + "', '" + password + "', '" + status + "', '0', '0');");
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
	
	static public void addWorkedHours(int userID, int addition) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conn = DriverManager.getConnection(url);
			st = conn.createStatement();
			rs = st.executeQuery("Select a.hours_worked FROM Account a WHERE userID = '" + userID + "'");
			rs.next();
			int hours_worked = rs.getInt("hours_worked") + addition;
			st.execute("Update Account \n"
					+ "SET hours_worked = '" + hours_worked + "'\n"
					+ "WHERE userID = '" + userID + "'");
			
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
	}
	
	static public void resetWorkedHours(int userID) {
		Connection conn = null;
		Statement st = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conn = DriverManager.getConnection(url);
			st = conn.createStatement();
			st.execute("Update Account \n"
					+ "SET hours_worked = '" + 0 + "'\n"
					+ "WHERE userID = '" + userID + "'");
			
		} catch (SQLException sqle) {
			System.out.println("sqle: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		}
		finally {
			try {
				if(st != null) {st.close();}
				if(conn != null) {conn.close();}
			} catch (SQLException sqle) {
				System.out.println("sqle closing stuff: " + sqle.getMessage());
			}
		}
	}
	
	static public void updateHourlyRateOfPay(int userID, int hourly_rate_of_pay) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conn = DriverManager.getConnection(url);
			st = conn.createStatement();
				st.execute("Update Account \n"
						+ "SET hourly_rate_of_pay = '" + hourly_rate_of_pay + "'\n"
						+ "WHERE userID = '" + userID + "'");
			
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
	}
	
	
	static public ArrayList<String> checkTime(String day, int clock, boolean taken) {
		// Input day, clock and a boolean value and then return the name of people already taken/unavailable at the time slot.
		ArrayList<String> people = new ArrayList<String>();
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String status = (taken)? "taken":"unavailable";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conn = DriverManager.getConnection(url);
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM Timeslot WHERE day='" + day + "' AND clock = '" + clock + "'");
			
			while(rs.next()) {
				String sta = rs.getString("status");
				if(status!=null && sta.equals(status)) {
					UserInfo info = GetUserInfo(rs.getInt("userID"));
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
	
	static public void takeTime(int userID, String day, int clock, boolean taken) {
		// Input userID, time and boolean value showing whether the user takes or is unavailable at the time slot.
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String status = (taken)? "taken":"unavailable";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conn = DriverManager.getConnection(url);
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM Timeslot WHERE day='" + day + "' AND clock = '" + clock + "' AND userID = '" + userID +"'");
			
			if(rs.next()) {
				int tID = rs.getInt("timeslotID");
				st.execute("Update Timeslot \n"
						+ "SET status = '" + status + "'\n"
						+ "WHERE timeslotID = '" + tID + "'");
			}
			else {
				st.execute("INSERT INTO Timeslot (userID, day, clock, status) VALUE ('" + userID + "', '" + day + "', '" + clock + "', '" + status +"');");
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
	}
	
	static public ArrayList<TimeInfo> getAllTime() {
		// Input day, clock and a boolean value and then return the name of people already taken/unavailable at the time slot.
		ArrayList<TimeInfo> output = new ArrayList<TimeInfo>();
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conn = DriverManager.getConnection(url);
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM Timeslot");
			
			while(rs.next()) {
				String sta = rs.getString("status");
				if(sta.equals("taken")) {
					TimeInfo info = new TimeInfo(); // GetUserInfo(rs.getInt("userID"));
					UserInfo user = GetUserInfo(rs.getInt("userID"));
					info.username = user.username;
					info.day = rs.getString("day");
					info.clock = rs.getInt("clock");
					info.status = "taken";
					output.add(info);
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
		return output;
	}
	
	static public void cleanTime(int userID, String day, int clock) {
		// Input userID, time and then delete the corresponding data in Timeslot table
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conn = DriverManager.getConnection(url);
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM Timeslot WHERE day='" + day + "' AND clock = '" + clock + "' AND userID = '" + userID +"'");
			
			if(rs.next()) {
				int tID = rs.getInt("timeslotID");
				st.execute("DELETE FROM Timeslot \n"
						+ "WHERE timeslotID = '" + tID + "'");
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
			info.hours_worked = rs.getInt("hours_worked");
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
	
	static public String getColor(int userID) {
		// Input userID and return user's information.
		String color = "";
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conn = DriverManager.getConnection(url);
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM Colors WHERE colorID='" + userID + "'");
			rs.next();
			color = rs.getString("color");
			
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
		return color;
	}
	
	static public int GetIdByName(String username) {
		// Input user name and return user's id if it exists else 0.
		UserInfo info = new UserInfo();
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		int id = 0;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conn = DriverManager.getConnection(url);
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM Account WHERE username='" + username + "'");
			rs.next();
			id = rs.getInt("userID");
			
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
		return id;
	}
	
	static public void clean() {
		// Clean all tables
		Connection conn = null;
		Statement st = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conn = DriverManager.getConnection(url);
			st = conn.createStatement();
			st.executeQuery("DELETE * FROM ACCOUNT;");
			st.executeQuery("DELETE * FROM Timeslot");
			
		} catch (SQLException sqle) {
			System.out.println("sqle: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		}
		finally {
			try {
				if(st != null) {st.close();}
				if(conn != null) {conn.close();}
			} catch (SQLException sqle) {
				System.out.println("sqle closing stuff: " + sqle.getMessage());
			}
		}
	}


}
