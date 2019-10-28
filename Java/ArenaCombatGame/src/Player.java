/**
 * Program Name: Player.java
 * Purpose: An extension of the Character class for the player including health potions
 * Coder: Hunter Bennett
 * Date: Oct 25, 2019 
 */

public class Player extends Character
{
	HealthPotion potions;
	
	//Constructor
	public Player(String name, int maxHealth, Armour armour, int gold, Weapon weapon)
	{
		super(name, maxHealth, armour, gold, weapon);
		potions = new HealthPotion();
		
	}
	
	//Getter
	public HealthPotion getPotions()
	{
		return potions;
	}
	
	/**
	 * Method Name: attack()
	 * Purpose: simulates an attack against an enemy, calculating and applying damage
	 * Accepts: an Enemy object that is the player
	 * Returns: Nothing
	 * Coder: Hunter Bennett
	 * Date: Oct 26, 2019 
	*/
	public void attack(Enemy enemy)
	{
		int damage = this.getDamage();
		
		//subtracting armour from the damage
		if(damage > 0)
		{
			damage -= enemy.getArmour().getArmourValue();
			//ensuring damage doesn't go below 1
			damage = Math.max(damage, 1);
		}
	
;		int enemyStartingStatus = enemy.getStatus();
		int enemyCurrentStatus;
		
		if(damage <= 0)
		{
			//if miss or armour reduces damage to zero, warning player and returning
			System.out.println("\nYou swing wide and miss.");
			return;
		}
		else
		{
			enemy.takeDamage(damage);
			
			//checking for critical hit
			if(damage > this.getWeapon().getDamageHigh())
				System.out.print("\nIt's a critical hit! ");
			
			//outputting damage
			System.out.print("\nYou deal " + damage + " point");
			if(damage != 1)
				System.out.print("s");
			System.out.println(" of damage to the " + enemy.getName() + ".");
			
			enemyCurrentStatus = enemy.getStatus();
			
			//printing status message if damage caused a change in status
			if(enemyStartingStatus < enemyCurrentStatus && enemy.isAlive())
				{
					printStatus(enemyCurrentStatus, enemy);
					enemyStartingStatus = enemyCurrentStatus;
				}
		}
	}
	
	/**
	 * Method Name: printStatus()
	 * Purpose: outputs a message related to a new enemy status
	 * Accepts: an int that is the enemy's status
	 * Returns: Nothing
	 * Coder: Hunter Bennett
	 * Date: Oct 26, 2019 
	*/
	public void printStatus(int status, Character enemy)
	{
		switch(status)
		{
			case 1:
				System.out.println("The " + enemy.getName() + " looks bloodied.");
				break;
			case 2:
				System.out.println("The " + enemy.getName() + " is barely standing.");
			default: 
				break;
		
		}
	}
	
	/**
	 * Method Name: die()
	 * Purpose: outputs a generic death message
	 * Accepts: No parameters
	 * Returns: Nothing
	 * Coder: Hunter Bennett
	 * Date: Oct 26, 2019 
	*/
	public void die()
	{
		System.out.println("Your eyes become heavy and you lose the strength to hold your weapon.\n"
				+ "It slips from your fingers and falls to the ground, and you follow shortly after.\n"
				+ "In your final seconds you hear the roar of the chearing crowd.\n\n"
				+ "You have died.");
	}
	
	/**
	 * Method Name: addGold()
	 * Purpose: adds a number of gold to player
	 * Accepts: an int that is the amount of gold
	 * Returns: Nothing
	 * Coder: Hunter Bennett
	 * Date: Oct 26, 2019 
	*/
	public void addGold(int amount)
	{
		this.setGold(this.getGold() + amount);
		System.out.println("You receive " + amount + " gold!\n");
	}
	
	/**
	 * Method Name: spendGold()
	 * Purpose: Tests for a valid amount of gold to spend then subtracts it from player total
	 * Accepts: an int that is the amount of gold attempting to be spent
	 * Returns: true is sufficient gold or false if not
	 * Coder: Hunter Bennett
	 * Date: Oct 26, 2019 
	*/
	public boolean spendGold(int amount)
	{
		//ensure player has enough gold to spend
		if(amount <= this.getGold())
		{
			this.setGold(this.getGold() - amount);
			return true;
		}
		else 
			return false;
	}
	
	/**
	 * Method Name: buyHealthPotions()
	 * Purpose: adds a number of health potions to player's inventory at the cost of gold
	 * Accepts: an int that is the amount of potions attempting to be bought
	 * Returns: true if potions successfully purchased or false if not
	 * Coder: Hunter Bennett
	 * Date: Oct 26, 2019 
	*/
	public boolean buyHealthPotions(int quantity)
	{
		int totalCost = quantity * potions.getCost();
		if(spendGold(totalCost))
		{
			potions.addQuantity(quantity);
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Method Name: printInventory()
	 * Purpose: prints the player's weapon and armour stats as well as potions
	 * Accepts: No parameters
	 * Returns: True if player has a usable potion or false if not
	 * Coder: Hunter Bennett
	 * Date: Oct 26, 2019 
	*/
	public boolean printInventory()
	{
		System.out.print("\nArmour\nType:\t\t");
		getArmour().printArmour();
				//+ "\nArmour Value:\t" + getArmour().getArmourValue()); 
		
		System.out.print("\nWeapon\nName:\t\t\t");
		getWeapon().printWeapon();
//		+ getWeapon().getName()
//				+ "\nDamage:\t\t\t" + getWeapon().getDamageLow() + "-" + getWeapon().getDamageHigh()
//				+ "\nCritical Chance:\t" + getWeapon().getCriticalChance()
//				+ "\nMiss Chance:\t\t" + getWeapon().getMissChance());
		
			System.out.println("\nPotions\nName:\t\tHealth Potion"
					+ "\nQuantity:\t" + potions.getQuantity());
			
			//if player has potions, give the option to use one
			if(potions.getQuantity() > 0)
			{
				System.out.println("\nWhat would you like to use?");
			    System.out.println("\n\tNothing (0)\n\tHealth Potion (1)");
			    System.out.print("Enter your choice: ");	
			    return true;
			}
			else
			{
				System.out.println("You have no usable items.\n");
				return false;
			}
	}  
	
	/**
	 * Method Name: useHealthPotion()
	 * Purpose: uses a health potion to heal if available
	 * Accepts: No parameters
	 * Returns: true if potion available and used or false if not
	 * Coder: Hunter Bennett
	 * Date: Oct 26, 2019 
	*/
	public boolean useHealthPotion()
	{
		if(potions.getQuantity() > 0)
		{
			potions.use();
			heal(potions.getHealAmount());
			return true;
		}
		
		return false;
	}
	
	/**
	 * Method Name: flea()
	 * Purpose: checks if player has the gold requires to flea and outputs a message
	 * Accepts: No parameters
	 * Returns: true if sufficient gold or false if not
	 * Coder: Hunter Bennett
	 * Date: Oct 26, 2019 
	*/
	public boolean flea()
	{
		if(spendGold(20))
		{
			System.out.println("\nYou pay the 20 gold to spare your life. Better luck next time.");
			return true;
		}
		else 
		{
			System.out.println("You don't have enough gold to flea.");
			return false;
		}
	}
}//end class