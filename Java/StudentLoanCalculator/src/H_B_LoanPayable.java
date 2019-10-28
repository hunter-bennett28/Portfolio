/**
 * Program Name: H_B_LoanPayable.java
 * Purpose: An interface that hold a value used to convert an
 * 					annual interest rate to a monthly one and one function
 * 					that uses that conversion to calculate monthly payments.
 * Coder: Hunter Bennett
 * Date: Apr 7, 2019 
 */

public interface H_B_LoanPayable
{
	//conversion rate for an annual interest rate to a monthly as a decimal
	double ANNUAL_RATE_TO_MONTHLY_RATE = 1.0/1200.0;
	
	/**
	 * Method Name: calculateLoanPayment
	 * Purpose: Calculates the monthly payments of a given loan with given annual interest
	 * Accepts: A double that is the loan amount, a double that is the annual interest rate
	 * 			and an int that is the amortization period in months.
	 * Returns: A Double that is the monthly payment
	 * Coder: Hunter Bennett
	 * Date: Apr 7, 2019 
	*/
	
	public double calculateLoanPayment(double principalAmount, double annualPrimeInterestRate,
			int amortizationPeriod);
}
 //end class