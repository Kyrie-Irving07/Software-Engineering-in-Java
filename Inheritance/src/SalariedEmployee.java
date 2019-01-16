
public class SalariedEmployee extends Employee {
	double annualSalary;
	public SalariedEmployee(String first_name, String last_name, String birth_day,
							int employeeid, String jobtitle, String Company,
							double annualsalary)
	{
		super(first_name, last_name, birth_day,
			  employeeid, jobtitle, Company);
		annualSalary = annualsalary;
	}
	@Override
	public double getAnnualSalary() 
	{
		return this.annualSalary;
	}	
}
