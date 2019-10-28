/**
 * Program Name: Armour.java
 * Purpose: A class representing a piece of armour
 * Coder: Hunter Bennett
 * Date: Oct 25, 2019 
 */

public class Armour
{
	private String name;
	private int armourValue;
	private int cost;
	
	// Constructors
	public Armour(int armourValue)
	{
		this.name = "";
		this.armourValue = armourValue;
		this.cost = 0;
	}
	public Armour(String name, int armourValue)
	{
		this.name = name;
		this.armourValue = armourValue;
		this.cost = 0;
	}
	
	public Armour(String name, int armourValue, int cost)
	{
		this.name = name;
		this.armourValue = armourValue;
		this.cost = cost;
	}

	//Getters
	public String getName()
	{
		return name;
	}

	public int getArmourValue()
	{
		return armourValue;
	}	
	
	public int getCost()
	{
		return cost;
	}
	
	/**
	 * Method Name: printArmour()
	 * Purpose: outputs the player's current armour
	 * Accepts: No parameters
	 * Returns: Nothing
	 * Coder: Hunter Bennett
	 * Date: Oct 27, 2019 
	*/
	public void printArmour()
	{
		System.out.println(name + "\nArmour Value:\t" + armourValue + "\n");
	}
}
 //end class