
public abstract class Employee extends Person {
	int employeeID;
	String jobTitle;
	String company;
	public Employee(String first_name, String last_name, String birth_day,
					int employeeid, String jobtitle, String Company)
	{
		super(first_name, last_name, birth_day);
		employeeID = employeeid;
		jobTitle = jobtitle;
		company = Company;
	}
	public String getEmployeeID()
	{
		return String.valueOf(this.employeeID);
	}
	public String getJobTitle()
	{
		return this.jobTitle;
	}
	public String getCompany()
	{
		return this.company;
	}
	public abstract double getAnnualSalary();
}
