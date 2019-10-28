/**
 * Program Name: Character.java
 * Purpose: An implementation of a character shared by both Enemy and Player characters
 * Coder: Hunter Bennett
 * Date: Oct 25, 2019 
 */

public class Character
{
	private String name;
	private int maxHealth;
	private int currentHealth;
	private Armour armour;
	private int gold;
	private Weapon weapon;
	
	//Constructors
	/* Full initialization constructor */
	public Character(String name, int maxHealth, Armour armour, int gold, Weapon weapon)
	{
		this.name = name;
		this.maxHealth = maxHealth;
		this.currentHealth = maxHealth;
		this.armour = armour;
		this.gold = gold;
		this.weapon = weapon;
	}
	
	/* Constructor without Armour object for generic character creation */
	public Character(String name, int maxHealth, int armour, int gold, Weapon weapon)
	{
		this.name = name;
		this.maxHealth = maxHealth;
		this.currentHealth = maxHealth;
		this.armour = new Armour("unnamed", armour);
		this.gold = gold;
		this.weapon = weapon;
	}
	
	//Getters
	public String getName()
	{
		return name;
	}
	
	public int getMaxHealth()
	{
		return maxHealth;
	}		
	
	public int getCurrentHealth()
	{
		return currentHealth;
	}
	
	public Armour getArmour()
	{
		return armour;
	}
	
	public int getGold()
	{
		return gold;
	}
	
	public Weapon getWeapon()
	{
		return weapon;
	}
	
	//Setters
	public void setName(String name)
	{
		this.name = name;
	}	
	
	public void setMaxHealth(int health)
	{
		this.maxHealth = health;
	}	
	
	public void setCurrentHealth(int currentHealth)
	{
		this.currentHealth = currentHealth;
	}

	public void setArmour(Armour armour)
	{
		this.armour = armour;
	}

	public void setGold(int gold)
	{
		this.gold = gold;
	}
	
	public void setWeapon(Weapon weapon)
	{
		this.weapon = weapon;
	}
	
	
	/**
	 * Method Name: getStatus()
	 * Purpose: Returns a status code based on remaining health
	 * Accepts: No parameters
	 * Returns: 0 if good condition, 1 if below half health, 2 if below half health
	 * 			and 2 if below 1 quarter health
	 * Coder: Hunter Bennett
	 * Date: Oct 26, 2019 
	*/
	public int getStatus()
	{
		if(currentHealth > maxHealth/2)
			return 0;
		else if(currentHealth > maxHealth/4 && currentHealth > 0)
			return 1;
		else 
			return 2;
	}
	
	/**
	 * Method Name: takeDamage()
	 * Purpose: Subtracts a damage amount from the character's health
	 * Accepts: int that is the damage amount
	 * Returns: Nothing
	 * Coder: Hunter Bennett
	 * Date: Oct 26, 2019 
	*/
	public void takeDamage(int damage)
	{
		currentHealth -= damage;
	}

	/**
	 * Method Name: getDamage()
	 * Purpose: Calculates the amount of damage dealt in the given weapon's damage range
	 * 			while accounting for armour, miss and critical chance, tripling damage on 
	 * 			a critical hit.
	 * Accepts: No parameters
	 * Returns: an int that is the calculated damage amount
	 * Coder: Hunter Bennett
	 * Date: Oct 26, 2019 
	*/
	public int getDamage()
	{
		int low = weapon.getDamageLow();
		int high = weapon.getDamageHigh();
		int damage = (int)(Math.random() * ((high - low) + 1)) + low;
		
		int eventChance = (int)(Math.random() * 100 + 1);
		
		//check for miss
		if(eventChance <= weapon.getMissChance())
		{
			return 0;
		}
		//check for critical
		else if(eventChance > weapon.getMissChance() && eventChance <= 
				(weapon.getMissChance() + weapon.getCriticalChance()))
		{
			return damage * 3;
		}
		
		return damage;
	}
	
	/**
	 * Method Name: heal()
	 * Purpose: adds a given number of health points to a character's current health
	 * Accepts: an int that is the points to heal
	 * Returns: Nothing
	 * Coder: Hunter Bennett
	 * Date: Oct 26, 2019 
	*/
	public void heal(int amount)
	{
		currentHealth += amount;
		
		if(currentHealth > maxHealth)
			currentHealth = maxHealth;
	}
	
	/**
	 * Method Name: fullheal()
	 * Purpose: resets a character's health to their max
	 * Accepts: No parameters
	 * Returns: Nothing
	 * Coder: Hunter Bennett
	 * Date: Oct 26, 2019 
	*/
	public void fullHeal()
	{
		currentHealth = maxHealth;
	}
	
	/**
	 * Method Name: isAlive()
	 * Purpose: checks if the character has health left
	 * Accepts: No parameters
	 * Returns: true if player has health left, or false if not
	 * Coder: Hunter Bennett
	 * Date: Oct 26, 2019 
	*/
	public boolean isAlive()
	{
		return currentHealth > 0;
	}
}//end class