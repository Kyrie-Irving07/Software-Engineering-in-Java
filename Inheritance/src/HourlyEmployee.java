
public class HourlyEmployee extends Employee {
	double hourlyRate;
	double numberHoursPerWeek;
	public HourlyEmployee(String first_name, String last_name, String birth_day,
						  int employeeid, String jobtitle, String Company,
						  double hourlyrate, double numberhoursperweek)
	{
		super(first_name, last_name, birth_day,
			  employeeid, jobtitle, Company);
		hourlyRate = hourlyrate;
		numberHoursPerWeek = numberhoursperweek;
	}
	@Override
	public double getAnnualSalary()
	{
		return this.hourlyRate * this.numberHoursPerWeek * 52.;
	}
}
