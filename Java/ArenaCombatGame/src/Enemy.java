/**
 * Program Name: Enemy.java
 * Purpose: An extension of the Character class for enemies
 * Coder: Hunter Bennett
 * Date: Oct 25, 2019 
 */

public class Enemy extends Character
{

	//Constructor
	public Enemy(String name, int maxHealth, int armour, int gold, Weapon weapon)
	{
		super(name, maxHealth, armour, gold, weapon);
	}
	
	/**
	 * Method Name: attack()
	 * Purpose: simulates an attack against the player, calculating and applying damage
	 * Accepts: a Player object that is the player
	 * Returns: Nothing
	 * Coder: Hunter Bennett
	 * Date: Oct 26, 2019 
	*/
	public void attack(Player player)
	{
		int damage = this.getDamage();
		
		//subtracting armour from the damage
		if(damage > 0)
		{
			damage -= player.getArmour().getArmourValue();
			//ensuring damage doesn't go below 1
			damage = Math.max(damage, 1);
		}
		
		int playerStartingStatus = player.getStatus();
		int playerCurrentStatus;
		
		System.out.println("\nThe " + this.getName() + " attacks.\n");
		if(damage <= 0)
		{
			//if miss, warning player and returning
			System.out.println("You manage to evade the attack.");
			return;
		}
		else
		{
			player.takeDamage(damage);
			
			//checking for critical hit
			if(damage > this.getWeapon().getDamageHigh())
				System.out.print("It's a critical hit! ");
			
			//outputting damage
			System.out.print("You take " + damage + " point");
			if(damage != 1)
				System.out.print("s");
			System.out.println(" of damage.");
			
			playerCurrentStatus = player.getStatus();
			
			//printing status message if damage caused a change in status
			if(playerStartingStatus < playerCurrentStatus && player.isAlive())
				{
					printStatus(playerCurrentStatus);
					playerStartingStatus = playerCurrentStatus;
				}
		}
	}
	
	/**
	 * Method Name: printStatus()
	 * Purpose: outputs a message related to a new player status
	 * Accepts: an int that is the player's status
	 * Returns: Nothing
	 * Coder: Hunter Bennett
	 * Date: Oct 26, 2019 
	*/
	public void printStatus(int status)
	{
		switch(status)
		{
		case 1:
			System.out.println("You're starting to feel the wear of battle.\n");
			break;
		case 2:
			System.out.println("Your vision is geting blurry and you're having trouble standing.\n");
		}
	}
	
}
 //end class