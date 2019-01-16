
public class CommissionEmployee extends SalariedEmployee {
	double salesTotal;
	double commissionPercentage;
	public CommissionEmployee(String first_name, String last_name, String birth_day,
							  int employeeid, String jobtitle, String Company,
							  double annualsalary,
							  double salestotal, double commissionpercentage)
	{
		super(first_name, last_name, birth_day,
			  employeeid, jobtitle, Company,
			  annualsalary);
		salesTotal = salestotal;
		commissionPercentage = commissionpercentage;
	}
	@Override
	public double getAnnualSalary() 
	{
		return salesTotal * commissionPercentage + this.annualSalary;
	}
}
