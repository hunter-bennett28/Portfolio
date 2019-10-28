/**
 * Program Name: H_B_NegativeValueException.java
 * Purpose: An exception class used to indicate that a given value
 * 					that has to be positive was negative.
 * Coder: Hunter Bennett
 * Date: Apr 7, 2019 
 */
@SuppressWarnings("serial") //suppressing no serialVersionIUD warning
public class H_B_NegativeValueException extends Exception
{
	public H_B_NegativeValueException()
	{
		super("Value of CSL and OSL amount must be positive.");
	}
}
 //end class