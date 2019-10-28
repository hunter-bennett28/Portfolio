/**
 * Program Name: WeaponsList.java
 * Purpose: A class that holds the shop inventory of weapons
 * Coder: Hunter Bennett
 * Date: Oct 25, 2019 
 */

import java.util.Vector;

public class WeaponsList
{
	private Vector<Weapon> list;
	
	/* Constructor that populates the list with all available weapons */
	public WeaponsList()
	{
		list = new Vector<Weapon>();
		list.add(new Weapon("hatchet", 2, 4, 10, 15, 15));
		list.add(new Weapon("rapier", 2, 6, 20, 20, 18));
		list.add(new Weapon("falchion", 4, 6, 12, 10, 40));
		list.add(new Weapon("battle axe", 8, 14, 15, 20, 90));
		list.add(new Weapon("war hammer", 6, 16, 10, 15, 105));
		list.add(new Weapon("broad sword", 10, 14, 15, 15, 120));
		list.add(new Weapon("halberd", 12, 16, 15, 10, 145));
	}
	
	//Getter
	public Vector<Weapon> getList()
	{
		return list;
	}
	
	/**
	 * Method Name: printList()
	 * Purpose: prints the list of weapons in a formatted way
	 * Accepts: No parameters
	 * Returns: Nothing
	 * Coder: Hunter Bennett
	 * Date: Oct 26, 2019 
	*/
	public void printList()
	{
		Weapon weapon;
		for(int i = 0; i < list.size(); i++)
		{
			weapon = list.get(i);
			System.out.println(weapon.getName() + " (" + (i + 1) + ")"
					+ "\nDamage:\t\t\t" + weapon.getDamageLow() + "-" + weapon.getDamageHigh()
					+ "\nCritical Chance:\t" + weapon.getCriticalChance() + "%"
					+ "\nMiss Chance:\t\t" + weapon.getMissChance() + "%"
					+ "\nCost:\t\t\t" + weapon.getCost() + " gold\n");
		}
	}
}
 //end class