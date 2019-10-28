/**
 * Program Name: StudentLoanApp.java
 * Purpose: Builds and handles functions of a GUI that calculates
 * 					loan payment information based on given data. 
 * Coder: Hunter Bennett
 * Date: Apr 7, 2019 
 */

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

@SuppressWarnings("serial") //hiding "No serialVersionUID" warning
public class StudentLoanApp extends JFrame
{
	ArrayList<Student> students;
	int currentStudent = -1; //starting at -1 to indicate no students exist yet
	JTextField idInput, fullNameInput, aptNumberInput, streetAddressInput, 
		cityInput, provinceInput, postalCodeInput, cslInput, oslInput,
		cslCalc, oslCalc, primeRate, amortizationMonths, monthlyCSL,
		monthlyOSL, totalMonthly, totalToPay, totalBorrowed, interest;
	JButton addNew, previous, next, calculate, clear;
	
	public StudentLoanApp()
	{
		students = new ArrayList<Student>();
		
		JFrame frame = new JFrame("Hunter Bennett - 0642511");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setSize(780, 430);
		frame.setLayout(new BorderLayout());
		frame.setVisible(true);
		
		Color lightPurple = new Color(166, 123, 209);
		
		JPanel titlePanel = new JPanel();
		titlePanel.setBackground(lightPurple);
		JLabel title = new JLabel("This Is Hunter Bennett's Student Loan Calculator");
		Font titleFont = new Font("Arial", Font.BOLD, 24);
		title.setFont(titleFont);
		title.setHorizontalAlignment(JLabel.CENTER);
		titlePanel.add(title);
		
		JPanel footerPanel = new JPanel();
		footerPanel.setBackground(lightPurple);
		JLabel footLabel = new JLabel("Copyright Hunter Bennett 2019");
		footLabel.setHorizontalAlignment(JLabel.CENTER);
		footerPanel.add(footLabel);
		
		JPanel input = buildInputPanel();
		JPanel output = buildCalculationPanel();
		input.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		output.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		frame.add(titlePanel, BorderLayout.NORTH);
		frame.add(input, BorderLayout.CENTER);
		frame.add(output, BorderLayout.EAST);
		frame.add(footerPanel, BorderLayout.SOUTH);
	}
	
	/**
	 * Method Name: buildInputPanel
	 * Purpose: Creates a form for user input including all of the necessary fields
	 * Accepts: No parameters
	 * Returns: A JPanel object
	 * Coder: Hunter Bennett
	 * Date: Apr 7, 2019 
	*/
	
	public JPanel buildInputPanel()
	{
		JLabel idLabel, fullNameLabel, aptNumberLabel, streetAddressLabel, 
			cityLabel, provinceLabel, postalCodeLabel, cslLoanAmountLabel, 
			oslLoanAmountLabel;
		
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(10, 1, 10, 10));
		inputPanel.setSize(400, 300);
		Color lightBlue = new Color(186, 207, 242);
		inputPanel.setBackground(lightBlue);
		
		JPanel idPanel = new JPanel();
		idPanel.setLayout(new GridLayout(1,2));
		idLabel = new JLabel("Student ID: ");
		idInput = new JTextField();
		idPanel.add(idLabel);
		idPanel.add(idInput);
		
		JPanel fullNamePanel = new JPanel();
		fullNamePanel.setLayout(new GridLayout(1,2));
		fullNameLabel = new JLabel("Full Name: ");
		fullNameInput = new JTextField();
		fullNamePanel.add(fullNameLabel);
		fullNamePanel.add(fullNameInput);
		
		JPanel aptNumberPanel = new JPanel();
		aptNumberPanel.setLayout(new GridLayout(1,2));
		aptNumberLabel = new JLabel("Apartment Number (Optional): ");
		aptNumberInput = new JTextField();
		aptNumberPanel.add(aptNumberLabel);
		aptNumberPanel.add(aptNumberInput);
		
		JPanel streetAddressPanel = new JPanel();
		streetAddressPanel.setLayout(new GridLayout(1,2));
		streetAddressLabel = new JLabel("Street Address: ");
		streetAddressInput = new JTextField();
		streetAddressPanel.add(streetAddressLabel);
		streetAddressPanel.add(streetAddressInput);
		
		JPanel cityPanel = new JPanel();
		cityPanel.setLayout(new GridLayout(1,2));
		cityLabel = new JLabel("City: ");
		cityInput = new JTextField();
		cityPanel.add(cityLabel);
		cityPanel.add(cityInput);
		
		JPanel provincePanel = new JPanel();
		provincePanel.setLayout(new GridLayout(1,2));
		provinceLabel = new JLabel("Province: ");
		provinceInput = new JTextField();
		provincePanel.add(provinceLabel);
		provincePanel.add(provinceInput);
		
		JPanel postalCodePanel = new JPanel();
		postalCodePanel.setLayout(new GridLayout(1,2));
		postalCodeLabel = new JLabel("Postal Code: ");
		postalCodeInput = new JTextField();
		postalCodePanel.add(postalCodeLabel);
		postalCodePanel.add(postalCodeInput);
		
		JPanel cslLoanPanel = new JPanel();
		cslLoanPanel.setLayout(new GridLayout(1,2));
		cslLoanAmountLabel = new JLabel("CSL Amount: ");
		cslInput = new JTextField();
		cslLoanPanel.add(cslLoanAmountLabel);
		cslLoanPanel.add(cslInput);
		
		JPanel oslLoanPanel = new JPanel();
		oslLoanPanel.setLayout(new GridLayout(1,2));
		oslLoanAmountLabel = new JLabel("OSL Amount: ");
		oslInput = new JTextField();
		oslLoanPanel.add(oslLoanAmountLabel);
		oslLoanPanel.add(oslInput);
		
		addNew = new JButton("Add New Student");
		addNew.addActionListener(new ButtonListener());
		
		idPanel.setBackground(lightBlue);
		inputPanel.setBackground(lightBlue);
		fullNamePanel.setBackground(lightBlue);
		aptNumberPanel.setBackground(lightBlue);
		streetAddressPanel.setBackground(lightBlue);
		cityPanel.setBackground(lightBlue);
		provincePanel.setBackground(lightBlue);
		postalCodePanel.setBackground(lightBlue);
		cslLoanPanel.setBackground(lightBlue);
		oslLoanPanel.setBackground(lightBlue);
		
		inputPanel.add(idPanel);
		inputPanel.add(fullNamePanel);
		inputPanel.add(aptNumberPanel);
		inputPanel.add(streetAddressPanel);
		inputPanel.add(cityPanel);
		inputPanel.add(provincePanel);
		inputPanel.add(postalCodePanel);
		inputPanel.add(cslLoanPanel);
		inputPanel.add(oslLoanPanel);
		inputPanel.add(addNew);
		
		return inputPanel;
	}
	
	/**
	 * Method Name: buildCalculationPanel
	 * Purpose: Creates a panel containing all of the necessary calculation fields
	 * Accepts: No parameters
	 * Returns: A JPanel object
	 * Coder: Hunter Bennett
	 * Date: Apr 7, 2019 
	*/
	
	public JPanel buildCalculationPanel()
	{
		JPanel calculationPanel = new JPanel();
		calculationPanel.setBackground(new Color(186, 207, 242));
		calculationPanel.setLayout(new GridLayout(12,2, 5, 5));
		
		JLabel cslAmountLabel = new JLabel("Canada Student Loan Amount: ");
		JLabel oslAmountLabel = new JLabel("Ontario Student Loan Amount: ");
		JLabel primeLabel = new JLabel("Current Prime Interest Rate: ");
		JLabel amorLabel = new JLabel("Amortization Period In Months: ");
		JLabel monthlyCSLLabel = new JLabel("Monthly CSL Payment Amount: ");
		JLabel monthlyOSLLabel = new JLabel("Monthly OSL Payment Amount: ");
		JLabel totalMonthlyLabel = new JLabel("Total Monthly Payment Amount: ");
		JLabel totalToPayLabel = new JLabel("Total Amount of Your Repaid Loan: ");
		JLabel borrowedLabel = new JLabel("Total Amount You Borrowed: ");
		JLabel interestLabel = new JLabel("Total Interest On Your Loans: ");
		
		cslCalc = new JTextField();
		oslCalc = new JTextField();
		primeRate = new JTextField();
		amortizationMonths = new JTextField();
		monthlyCSL = new JTextField();
		monthlyOSL = new JTextField();
		totalMonthly = new JTextField();
		totalToPay = new JTextField();
		totalBorrowed = new JTextField();
		interest = new JTextField();
		
		previous = new JButton("Previous Student");
		next = new JButton("Next Student");
		calculate = new JButton("Calculate Payments");
		clear = new JButton("Clear Fields");
		
		previous.addActionListener(new ButtonListener());
		next.addActionListener(new ButtonListener());
		calculate.addActionListener(new ButtonListener());
		clear.addActionListener(new ButtonListener());
		
		calculationPanel.add(cslAmountLabel);
		calculationPanel.add(cslCalc);
		calculationPanel.add(oslAmountLabel);
		calculationPanel.add(oslCalc);
		calculationPanel.add(primeLabel);
		calculationPanel.add(primeRate);
		calculationPanel.add(amorLabel);
		calculationPanel.add(amortizationMonths);
		calculationPanel.add(monthlyCSLLabel);
		calculationPanel.add(monthlyCSL);
		calculationPanel.add(monthlyOSLLabel);
		calculationPanel.add(monthlyOSL);
		calculationPanel.add(totalMonthlyLabel);
		calculationPanel.add(totalMonthly);
		calculationPanel.add(totalToPayLabel);
		calculationPanel.add(totalToPay);
		calculationPanel.add(borrowedLabel);
		calculationPanel.add(totalBorrowed);
		calculationPanel.add(interestLabel);
		calculationPanel.add(interest);
		calculationPanel.add(previous);
		calculationPanel.add(next);
		calculationPanel.add(calculate);
		calculationPanel.add(clear);
		
		return calculationPanel;
	}
	
	public static void main(String[] args)
	{
		new StudentLoanApp();
	}//end main
	
	private class ButtonListener implements ActionListener, H_B_LoanPayable
	{
		final double CSL_ADDITIONAL_INTEREST = 2.5;
		final double OSL_ADDITIONAL_INTEREST = 1.0;
		
		/**
		 * Method Name: actionPerformed	
		 * Purpose: Creates a new student in the array list, cycles between students,
		 * 					calculates payments or clears fields depending on the button that
		 * 					called it.
		 * Accepts: An ActionEvent object
		 * Returns: Nothing! Void method.
		 * Coder: Hunter Bennett
		 * Date: Apr 7, 2019 
		*/
		
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource().equals(addNew))
			{
				createNewStudent();
			}
			else if(e.getSource().equals(previous))
			{
				//only returning previous if exists
				if(currentStudent >= 1)
				{
					--currentStudent;
					updateFields(false);
				}
			}
			else if(e.getSource().equals(next))
			{
				//only returning next if exists
				if(currentStudent < students.size() - 1)
				{
					++currentStudent;
					updateFields(false);
				}
			}
			else if(e.getSource().equals(calculate))
			{
				calculateValues();
			}
			else
			{
				primeRate.setText("");
				amortizationMonths.setText("");
				primeRate.requestFocus();
			}
		}
		
		/**
		 * Method Name: createNewStudent
		 * Purpose: Validates then creates a new student object
		 * 					in the students array list
		 * Accepts: No parameters
		 * Returns: Nothing! Void method
		 * Coder: Hunter Bennett
		 * Date: Apr 7, 2019 
		*/
		
		public void createNewStudent()
		{
			boolean valid = true;
			String id = idInput.getText();
			for(int i = 0; i < id.length(); ++i)
			{
				if(Character.isAlphabetic(id.charAt(i))) 
				{
					idInput.setText("Enter digits only.");
					valid = false;
				}
			}
			
			String fullName = fullNameInput.getText();
			int numSpaces = 0;
			//counting number of spaces in user input
			for(int i = 0; i < fullName.length(); ++i)
			{
				if(fullName.charAt(i) == ' ')
				{
					++numSpaces;
				}
			}
			int firstSpace;
			int lastSpace;
			String firstName = "";
			String middleName = "";
			String surname = "";
			
			//allocating names based on number of names given
			if(numSpaces >= 2) {
				firstSpace = fullName.indexOf(" ");
				lastSpace = fullName.lastIndexOf(" ");
				firstName = fullName.substring(0, firstSpace);
				middleName = fullName.substring(firstSpace + 1, lastSpace);
				surname = fullName.substring(lastSpace + 1, fullName.length());
			}
			else if(numSpaces == 1)
			{
				firstSpace = fullName.indexOf(" ");
				firstName = fullName.substring(0, firstSpace);
				surname = fullName.substring(firstSpace + 1, fullName.length());
			}
			else
			{
				firstName = fullName;
			}
			
			String aptNumber = aptNumberInput.getText();
			String streetAddress = streetAddressInput.getText();
			String streetNumber = "";
			String streetName = "";
			String city = cityInput.getText();
			String province = provinceInput.getText();
			String postalCode = postalCodeInput.getText();
			double cslLoan = 0.0;
			double oslLoan = 0.0;
			
			//ensuring correct address input
			try 
			{
				if(!streetAddress.isEmpty()) {
					streetNumber = streetAddress.substring(0, 
							streetAddress.indexOf(" "));
					streetName = streetAddress.substring(
							streetAddress.indexOf(" ") + 1, streetAddress.length());
				}
			}
			catch(StringIndexOutOfBoundsException ex)
			{
				
				streetAddressInput.setText("Please enter a valid address.");
				valid = false;
			}
			
			//ensuring correct loan input
			try 
			{
				cslLoan = Double.parseDouble(cslInput.getText());
				oslLoan = Double.parseDouble(oslInput.getText());
				if(cslLoan < 0 || oslLoan < 0)
				{
					throw new H_B_NegativeValueException();
				}
			}
			catch(H_B_NegativeValueException ex)
			{
				JOptionPane.showMessageDialog(null, "Numeric values "
						+ "can not be negative. Input will be converted to its"
						+ " positive equivalent and processing will continue.", 
						"Error", JOptionPane.OK_OPTION);
				if(cslLoan < 0)
				{
					cslLoan *= -1.0; //flipping sign
					cslInput.setText(Double.toString(cslLoan));
				}
				
				if(oslLoan < 0)
				{
					oslLoan *= -1.0;
					oslInput.setText(Double.toString(oslLoan));
				}
			}
			catch(NumberFormatException ex)
			{
				//catching non-numeric input
				cslInput.setText("Please enter a number.");
				oslInput.setText("Please enter a number.");
				valid = false;
			}
			if(valid)
			{
				students.add(new Student(id, surname, middleName, firstName, aptNumber, streetNumber, 
						streetName, city, province, postalCode, cslLoan, oslLoan));
				currentStudent = students.size() - 1; //moving position to newly added element
				
				updateFields(true);
			}
		}
		
		/**
		 * Method Name: calculateValues
		 * Purpose: Takes the input loan, interest and months and
		 * 					calculates the monthly payments of each loan,
		 * 					the total monthly payments, the total amount 
		 * 					that will be paid, the amount borrowed,
		 * 					and the total interest paid.
		 * Accepts: No parameters
		 * Returns: Nothing! Void method
		 * Coder: Hunter Bennett
		 * Date: Apr 7, 2019 
		*/
		
		public void calculateValues()
		{
			boolean valid = true;
			double csl = 0.0; 
			double osl = 0.0;
			double annualInterest = 0;
			int months = 0;
			//ensuring valid loan amounts in case changed
			try
			{
				csl = Double.parseDouble(cslCalc.getText());
				osl = Double.parseDouble(oslCalc.getText());
				annualInterest = Double.parseDouble(primeRate.getText());
				months = Integer.parseInt(amortizationMonths.getText());
				
				if(osl < 0 || csl < 0 || annualInterest < 0 || months < 0)
				{
					throw new H_B_NegativeValueException();
				}
			}
			catch(NumberFormatException ex)
			{	
				oslCalc.setText("Error: invalid input");
				cslCalc.setText("Error: invalid input");
				primeRate.setText("Error: invalid input");
				amortizationMonths.setText("Error: invalid input");
				
				valid = false;
			}
			catch(H_B_NegativeValueException ex)
			{
				JOptionPane.showMessageDialog(null, "Numeric values "
						+ "can not be negative. Input will be converted to its"
						+ " positive equivalent and processing will continue.", 
						"Error", JOptionPane.OK_OPTION);
				if(csl < 0)
				{
					csl *= -1.0; // switching sign
					cslCalc.setText(Double.toString(csl));
				}
				
				if(osl < 0)
				{
					osl *= -1.0;
					oslCalc.setText(Double.toString(osl));
				}
				
				if(annualInterest < 0)
				{
					annualInterest *= -1.0;
					primeRate.setText(Double.toString(annualInterest));
				}
				
				if(months < 0)
				{
					months *= -1;
					amortizationMonths.setText(Integer.toString(months));
				}
			}
			
			if((annualInterest * 100) % 25 != 0)
			{
				primeRate.setText("Rates may only be quarter percents");
				valid = false;
			}
			
			if(valid) {
				double monthlyCSLPayment = calculateLoanPayment(
						csl, annualInterest + CSL_ADDITIONAL_INTEREST, months);
				monthlyCSLPayment = (int)Math.round(monthlyCSLPayment
						 * 100)/100.0; //rounding to two decimal places
				monthlyCSL.setText("$" + String.format("%.2f", monthlyCSLPayment));
				
				double monthlyOSLPayment = calculateLoanPayment(
						osl, annualInterest + OSL_ADDITIONAL_INTEREST, months);
				monthlyOSLPayment = (int)Math.round(monthlyOSLPayment
						 * 100)/100.0; //rounding to two decimal places
				monthlyOSL.setText("$" + String.format("%.2f", monthlyOSLPayment));
				
				double totalMonthlyPayment = monthlyCSLPayment +
						monthlyOSLPayment;
				totalMonthly.setText("$" + String.format("%.2f", totalMonthlyPayment));
				
				double totalRepaidLoan = totalMonthlyPayment * months;
				totalToPay.setText("$" + String.format("%.2f", totalRepaidLoan));
				
				double amountBorrowed = csl + osl;
				totalBorrowed.setText("$" + String.format("%.2f", amountBorrowed));
				
				double totalInterest = totalRepaidLoan - amountBorrowed;
				interest.setText("$" + String.format("%.2f", totalInterest));
			}
		}
		
		/**
		 * Method Name: calculateLoanPayment
		 * Purpose: Calculates the monthly payments of a given loan with given annual interest
		 * Accepts: A double that is the loan amount, one that is the annual interest rate
		 * 					and an int that is the amortization period in months
		 * Returns: A Double that is the monthly payment
		 * Coder: Hunter Bennett
		 * Date: Apr 7, 2019 
		*/
		
		public double calculateLoanPayment(double principalAmount, 
				double annualPrimeInterestRate, int amortizationPeriod)
		{
			double monthlyInterest = annualPrimeInterestRate * ANNUAL_RATE_TO_MONTHLY_RATE;
			double monthlyPayment = principalAmount * monthlyInterest
					* Math.pow(1 + monthlyInterest, amortizationPeriod) / (Math.pow(
					1 + monthlyInterest, amortizationPeriod) - 1.0);
			return monthlyPayment;
		}
		
		/**
		 * Method Name: updateFields
		 * Purpose: Updates the fields of both the input form and the loan amounts on
		 * 					the calculation form to the currently selected student. If the
		 * 					fields are already correctly displaying the current student in
		 * 					the input form, only the calculation form is updated.
		 * Accepts: A boolean that is whether or not the function was called by the 'Add
		 * 					New Student' button
		 * Returns: Nothing! Void method
		 * Coder: Hunter Bennett
		 * Date: Apr 7, 2019 
		*/
		
		public void updateFields(boolean newEntry)
		{
			oslCalc.setText(Double.toString(students.get(currentStudent).getOslLoanAmount()));
			cslCalc.setText(Double.toString(students.get(currentStudent).getCslLoanAmount()));
			if(!newEntry) //only updating fields if not already accurate
			{
				Student current = students.get(currentStudent);
				idInput.setText(current.getStudentId());
				fullNameInput.setText(current.getFirstName() + " "
						+ current.getMiddleName() + " " + current.getSurname());
				aptNumberInput.setText(current.getAptNumber());
				streetAddressInput.setText(current.getStreetNumber() + " "
						+ current.getStreetName());
				cityInput.setText(current.getCity());
				provinceInput.setText(current.getProvince());
				postalCodeInput.setText(current.getPostalCode());
				cslInput.setText(Double.toString(current.getCslLoanAmount()));
				oslInput.setText(Double.toString(current.getOslLoanAmount()));
			}
		}
	}
}
 //end class