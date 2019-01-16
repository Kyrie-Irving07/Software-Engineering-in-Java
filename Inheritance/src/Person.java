
public class Person {
	String firstName;
	String lastName;
	String birthday;
	public Person(String first_name, String last_name, String birth_day) {
		firstName = first_name;
		lastName = last_name;
		birthday = birth_day;
	}
	public String getFirstName()
	{
		return this.firstName;
	}
	public String getLastName()
	{
		return this.lastName;
	}
	public String getBirthdate()
	{
		return this.birthday;
	}
}
