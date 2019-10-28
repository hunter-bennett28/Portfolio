/**
 * Program Name: ArmourList.java
 * Purpose: A container class that holds a list of armour available in the store
 * Coder: Hunter Bennett
 * Date: Oct 25, 2019 
 */

import java.util.Vector;

public class ArmourList
{
	private Vector<Armour> list;
	
	/* Constructor that populates the list with all available armour */
	public ArmourList()
	{
		list = new Vector<Armour>();
		list.add(new Armour("studded leather", 2, 30));
		list.add(new Armour("chain mail", 4, 65));
		list.add(new Armour("breast plate", 6, 100));
		list.add(new Armour("plate mail", 10, 210));
	}
	
	//Getter
	public Vector<Armour> getList()
	{
		return list;
	}
	
	/**
	 * Method Name: printList()
	 * Purpose: prints the contents of the list in a formatted manor
	 * Accepts: No parameters
	 * Returns: Nothing
	 * Coder: Hunter Bennett
	 * Date: Oct 26, 2019 
	*/
	public void printList()
	{
		Armour armour;
		for(int i = 0; i < list.size(); i++)
		{
			armour = list.get(i);
			System.out.println(armour.getName() + " (" + (i + 1) + ")"
					+ "\nArmour Value:\t" + armour.getArmourValue() 
					+ "\nCost:\t\t" + armour.getCost() + " gold\n");
		}
	}
}
 //end class