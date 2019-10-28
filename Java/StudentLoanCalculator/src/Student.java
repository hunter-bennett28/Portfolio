/**
 * Program Name: Student.java
 * Purpose: A class representing a student and their loan amounts.
 * Coder: Hunter Bennett
 * Date: Apr 7, 2019 
 */

public class Student
{
	private String studentId;
	private String surname;
	private String middleName;
	private String firstName;
	private String aptNumber;
	private String streetNumber;
	private String streetName;
	private String city;
	private String province;
	private String postalCode;
	private double cslLoanAmount;
	private double oslLoanAmount;
	
	public Student(String studentId, String surname, String middleName, String firstName, String aptNumber,
			String streetNumber, String streetName, String city, String province, String postalCode,
			double cslLoanAmount, double oslLoanAmount)
	{
		this.studentId = studentId;
		this.surname = surname;
		this.middleName = middleName;
		this.firstName = firstName;
		this.aptNumber = aptNumber;
		this.streetNumber = streetNumber;
		this.streetName = streetName;
		this.city = city;
		this.province = province;
		this.postalCode = postalCode;
		this.cslLoanAmount = cslLoanAmount;
		this.oslLoanAmount = oslLoanAmount;
	}

	public String getStudentId()
	{
		return studentId;
	}

	public String getSurname()
	{
		return surname;
	}

	public void setSurname(String surname)
	{
		this.surname = surname;
	}

	public String getMiddleName()
	{
		return middleName;
	}

	public void setMiddleName(String middleName)
	{
		this.middleName = middleName;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getAptNumber()
	{
		return aptNumber;
	}

	public void setAptNumber(String aptNumber)
	{
		this.aptNumber = aptNumber;
	}

	public String getStreetNumber()
	{
		return streetNumber;
	}

	public void setStreetNumber(String streetNumber)
	{
		this.streetNumber = streetNumber;
	}

	public String getStreetName()
	{
		return streetName;
	}

	public void setStreetName(String streetName)
	{
		this.streetName = streetName;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public String getProvince()
	{
		return province;
	}

	public void setProvince(String province)
	{
		this.province = province;
	}

	public String getPostalCode()
	{
		return postalCode;
	}

	public void setPostalCode(String postalCode)
	{
		this.postalCode = postalCode;
	}

	public double getCslLoanAmount()
	{
		return cslLoanAmount;
	}

	public void setCslLoanAmount(double cslLoanAmount)
	{
		this.cslLoanAmount = cslLoanAmount;
	}

	public double getOslLoanAmount()
	{
		return oslLoanAmount;
	}

	public void setOslLoanAmount(double oslLoanAmount)
	{
		this.oslLoanAmount = oslLoanAmount;
	}
	
	/**
	 * Method Name: toString
	 * Purpose: Returns certain data about a student object in a legible way
	 * Accepts: No parameters
	 * Returns: A String
	 * Coder: Hunter Bennett
	 * Date: Apr 7, 2019 
	*/
	
	public String toString()
	{
		return "Student Name: " + surname + ", " + firstName + " " + middleName
		+ "\nStudent Number: " + studentId + "\nCSL Amount is $" + cslLoanAmount
		+ "\nOSL Amount is $" + oslLoanAmount;
	}
}
 //end class