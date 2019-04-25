import java.util.ArrayList;

public class JDBCTest {
	public static void main(String arg[]) {
		
		JDBCDriver.clean();
		
		// Firstly test the function of register and login
		
		// Register
		// Different password and confirmed password
		System.out.println("--Different password and confirmed password");
		System.out.println(JDBCDriver.register("Tom", "123456", "12345a", true));
		// Password's length less than 6
		System.out.println("--Password's length less than 6");
		System.out.println(JDBCDriver.register("Tom", "123", "123", true));
		// Successful register
		System.out.println("--Sucessful register");
		System.out.println(JDBCDriver.register("Tom", "123456", "123456", true));
		// Existing User name
		System.out.println("--Existing User Name");
		System.out.println(JDBCDriver.register("Tom", "abcdef", "abcdef", true));
		System.out.println("\n*******************************************\n");
		
		// Login
		System.out.println("Test Log In");
		// Not existing User name
		System.out.println("--Not existing Username");
		System.out.println(JDBCDriver.login("Jerry", "123456"));
		// Error password
		System.out.println("--Error Password");
		System.out.println(JDBCDriver.login("Tom", "abcdef"));
		// Successful Login
		System.out.println("--Successful Login");
		System.out.println(JDBCDriver.login("Tom", "123456"));
		System.out.println("\n*******************************************\n");
		
		// Update Information
		System.out.println("Updating information");
		// Length of new password is less than 6
		System.out.println("--Length of new password is less than 6");
		System.out.println(JDBCDriver.updateAccount(1, "Jane", "123", "123"));
		// Passwords do not match
		System.out.println("--different password and comfirmed password");
		System.out.println(JDBCDriver.updateAccount(1, "Jane", "123456", "abcdef"));
		// Existing name
		System.out.println("--Existing Name");
		JDBCDriver.register("Jane", "abcdef", "abcdef", false);
		System.out.println(JDBCDriver.updateAccount(1, "Jane", "abcdef", "abcdef"));
		// Successful
		System.out.println("--Successful Update");
		System.out.println(JDBCDriver.updateAccount(1, "Tomy", "abcdef", "abcdef"));
		System.out.println("\n*******************************************\n");
		
		
		// Get Information from table
		System.out.println("Get Information from table");
		for(int i = 1; true; i ++) {
			UserInfo info = new UserInfo();
			info = JDBCDriver.GetUserInfo(i);
			if(info.empty())
				break;
			System.out.println("# User: " + i);
			System.out.println(info.username);
			System.out.println(info.password);
			System.out.println(info.status);
		}
		System.out.println("\n*******************************************\n");
		
		
		// Set time for a user
		System.out.println("Set time for a user");
		
		// Tomy takes time 8:00-9:00
		System.out.println("-- Kyrie takes time 8:00-9:00");
		JDBCDriver.takeTime(1, "Monday", 8, true);
		
		// Jane takes time 8:00-9:00
		System.out.println("-- Jane takes time 8:00-9:00");
		JDBCDriver.takeTime(2, "Monday", 8, true);
		
		// Jane is unavailable at 8:00-9:00
		System.out.println("-- Jane is unavailable at time 8:00-9:00");
		JDBCDriver.takeTime(2, "Monday", 8, false);
		
		// Another user Kyrie takes time 8:00-9:00
		System.out.println("-- Another user Kyrie takes time 8:00-9:00");
		System.out.println(JDBCDriver.updateAccount(1, "Kyrie", "123456", "123456"));
		JDBCDriver.takeTime(3, "Monday", 8, true);
		
		// Check people taking time at Monday 8:00-9:00
		ArrayList<String> peoples = JDBCDriver.checkTime("Monday", 8, true);
		System.out.println("-- People taking monday 8-9");
		for(int i = 0; i < peoples.size(); i ++)
			System.out.println(peoples.get(i));
		
		ArrayList<String> peoples1 = JDBCDriver.checkTime("Monday", 8, false);
		System.out.println("-- People unavailable on monday 8-9");
		for(int i = 0; i < peoples1.size(); i ++)
			System.out.println(peoples1.get(i));
		
		// Clean Time taken or unavailable
		System.out.println("-- Kyrie delete his data at 8-9 Mon");
		JDBCDriver.cleanTime(1, "monday", 8);
		System.out.println("-- People taking monday 8-9");
		peoples = JDBCDriver.checkTime("Monday", 8, true);
		for(int i = 0; i < peoples.size(); i ++)
			System.out.println(peoples.get(i));
		
		// Test Hours
		System.out.println("\n*******************************************\n");
		
		System.out.println("-- Kyrie has worked for 3 hours");
		JDBCDriver.addWorkedHours(JDBCDriver.GetIdByName("Kyrie"), 3);
		System.out.println("-- Kyrie has worked for 2 more hours");
		JDBCDriver.addWorkedHours(JDBCDriver.GetIdByName("Kyrie"), 2);
		System.out.println("-- Jane has worked for 9 hours");
		JDBCDriver.addWorkedHours(JDBCDriver.GetIdByName("Jane"), 9);
		System.out.println("-- Jane's worked hours are reset to 0");
		JDBCDriver.resetWorkedHours(JDBCDriver.GetIdByName("Jane"));
		System.out.println("Get Information from table");
		for(int i = 1; true; i ++) {
			UserInfo info = new UserInfo();
			info = JDBCDriver.GetUserInfo(i);
			if(info.empty())
				break;
			System.out.println("# User: " + i);
			System.out.println(info.username);
			System.out.println("Hours_worked: " + info.hours_worked);
		}
		
		// Test Info
		System.out.println("Get all information");
		ArrayList<TimeInfo> data = new ArrayList<TimeInfo>();
		data = JDBCDriver.getAllTime();
		for(int i = 0; i < data.size(); i ++) {
			data.get(i).printAll();
		}
		
	}
}
