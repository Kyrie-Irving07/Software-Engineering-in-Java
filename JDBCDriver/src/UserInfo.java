
public class UserInfo {
	public int userID;
	public String username;
	public String password;
	public String status;
	public int hour_worked;
	public int hourly_rate_of_pay;
	
	public boolean empty() {
		if(this.userID == 0 && this.username == null && this.password == null && this.status == null)
			return true;
		else
			return false;
	}
}
