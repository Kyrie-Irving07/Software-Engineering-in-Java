
public class JDBCTest {
	public static void main(String arg[]) {
		
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
			System.out.println("User: " + i);
			System.out.println(info.username);
			System.out.println(info.password);
			System.out.println(info.status);
		}
		
		
	}
}
