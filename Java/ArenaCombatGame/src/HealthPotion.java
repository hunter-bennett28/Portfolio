/**
 * Program Name: HealthPotion.java
 * Purpose: A class represention a health potion, which heals the player
 * Coder: Hunter Bennett
 * Date: Oct 25, 2019 
 */

public class HealthPotion
{
	private int cost;
	private int quantity;
	private int healAmount;
	
	/* Creates a default health potion object with a quantity of 0 for player start */
	public HealthPotion()
	{
		this.cost = 5;
		this.quantity = 0;
		this.healAmount = 20;
	}

	//Getters
	public int getCost()
	{
		return cost;
	}
	
	public int getQuantity()
	{
		return quantity;
	}
	
	public int getHealAmount()
	{
		return healAmount;
	}
	
	/**
	 * Method Name: addQuantity()
	 * Purpose: adds a number of potions
	 * Accepts: an int that is the number of potions to add
	 * Returns: Nothing
	 * Coder: Hunter Bennett
	 * Date: Oct 26, 2019 
	*/
	public void addQuantity(int quantity)
	{
		this.quantity += quantity;
	}

	/**
	 * Method Name: use()
	 * Purpose: subtracts one from quantity, checking for > 0 happens in function
	 * 			that calls it so the safety check is not repeated
	 * Accepts: No parameters
	 * Returns: Nothing
	 * Coder: Hunter Bennett
	 * Date: Oct 26, 2019 
	*/
	public void use()
	{
		quantity -= 1;
	}
}
 //end class