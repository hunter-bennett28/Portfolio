/**
 * Program Name: Weapons.java
 * Purpose: A Weapon class that stores the stats of a weapon
 * Coder: Hunter Bennett
 * Date: Oct 25, 2019 
 */

public class Weapon
{
	private String name;
	private int damageLow;
	private int damageHigh;
	private int criticalChance;
	private int missChance;
	private int cost;
	
	//Constructors
	/* full initialization constructor */
	public Weapon(String name, int damageLow, int damageHigh, int criticalChance, int missChance, int cost)
	{
		this.name = name;
		this.damageLow = damageLow;
		this.damageHigh = damageHigh;
		this.criticalChance = criticalChance;
		this.missChance = missChance;
		this.cost = cost;
	}
	
	/* no cost constructor for weapons that don't require a cost */
	public Weapon(String name, int damageLow, int damageHigh, int criticalChance, int missChance)
	{
		this.name = name;
		this.damageLow = damageLow;
		this.damageHigh = damageHigh;
		this.criticalChance = criticalChance;
		this.missChance = missChance;
		this.cost = 0;
	}
	
	/* no name or cost constructor for generic creation */
	public Weapon(int damageLow, int damageHigh, int criticalChance, int missChance)
	{
		this.name = "";
		this.damageLow = damageLow;
		this.damageHigh = damageHigh;
		this.criticalChance = criticalChance;
		this.missChance = missChance;
		this.cost = 0;
	}
	
	//Getters
	public String getName()
	{
		return name;
	}
	
	public int getDamageLow()
	{
		return damageLow;
	}

	public int getDamageHigh()
	{
		return damageHigh;
	}

	public int getCriticalChance()
	{
		return criticalChance;
	}

	public int getMissChance()
	{
		return missChance;
	}	

	public int getCost()
	{
		return cost;
	}	
	
	/**
	 * Method Name: printWeapon()
	 * Purpose: outputs player's current weapon
	 * Accepts: No parameters
	 * Returns: Nothing
	 * Coder: Hunter Bennett
	 * Date: Oct 27, 2019 
	*/
	public void printWeapon()
	{
		System.out.println(name
				+ "\nDamage:\t\t\t" + damageLow + "-" + damageHigh
				+ "\nCritical Chance:\t" + criticalChance + "%"
				+ "\nMiss Chance:\t\t" + missChance + "%\n");
	}
}
 //end class