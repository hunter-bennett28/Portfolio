/**
 * Program Name: Arena_main.java
 * Purpose: The starting point for the arena game containing the plot
 * Coder: Hunter Bennett
 * Date: Oct 25, 2019 
 */

import java.util.Scanner;
import java.util.Vector;

public class Arena_main
{

	public static void main(String[] args)
	{
		Scanner scanner = new Scanner(System.in);

		// INTRODUCTION
		// ======================================================================================
		
		//introduce program and get player name
		System.out.println("Welcome to Hunter Bennett's grand arena challenge!\n");
		System.out.print("Please enter your name: ");
		
		String playerName = scanner.nextLine();
		
		//introduce story
		System.out.println("\nYou are a roaming adventurer. You grew up in a small farming community\n"
				+ "where the most interesting thing that happens is a dear spotting. At a young\n"
				+ "age you developed a thirst for adventure aand excitement that corn could never\n"
				+ "sate. At the age of 16 you had finally had enough and were grown enough to venture\n"
				+ "out on your own. You packed a small bag with enough rations for a week, and set out\n"
				+ "blindly searching for anything new. Inexperienced, penniless and unprepared, you managed\n"
				+ "to stumble your way through the woods for a number of days, sleeping horribly with the\n"
				+ "threat of the wild keeping you from indulging in any true rest.\n");
		
		//teach user how to progress story
		System.out.println("Press Enter to continue.");
		
		//used as a pause in the story text requiring the "Enter" key to continue
		scanner.nextLine();
		
		System.out.println("As your rations run dry, you are forced to seek help and replenishment\n"
				+ "in a city you stumble across. It is vast and bustling, unlike any community you have ever seen.\n"
				+ "The people, however, are not friendly, and see your lack of food and coin as troubling\n"
				+ "as you do. One unsavoury character hints you towards the local arena should you\n"
				+ "want to earn your salvation.\n");
		
		scanner.nextLine();
		
		System.out.println("Without a semblance of choice, you find your way towards a building that the\n"
				+ "man rightfully assured you would not be able to miss. Towering what must be one hundred\n"
				+ "feet tall, supported by rigid pillars and giant, immaculately sculpted bricks, you enter\n"
				+ "the awe inspiring arena.\n");
		
		scanner.nextLine();
		
		System.out.println("An unenthused, grizzled looking man asks you if you are here for business or\n"
				+ "pleasure. \"Business.\", you say uncertainly. His eyes wander downward toward your belt.\n"
				+ "Noticing your lack of a weapon, he beckons you toward a table of complimentary ones.\n");
		
		scanner.nextLine();
		
		System.out.println("You pick:\n\tThe hammer (1).\n\tThe short sword (2).\n\tThe dagger(3).\n");
		

		//get weapon choice
		int choice = getUserInt(1,3, scanner);
		
		String weaponName = "";
		switch(choice)
		{
		case 1:
			weaponName = "hammer";
			break;
		case 2:
			weaponName = "short sword";
			break;
		case 3:
			weaponName = "dagger";
			break;
		}//end switch
		
		//create Player
		Player player;
		boolean godMode = false;
		
		//setting statistics very high if cheat name "GodMode" is chosen
		if(playerName.equals("GodMode"))
		{
			godMode = true;
			player = new Player(playerName, 1000, new Armour("invincible hauberk", 1000), 
					1000, new Weapon("infinity sword", 1000, 1005, 50, 0));
		}
		else
		{
			player = new Player(playerName, 20, new Armour("tattered clothing", 0), 0, 
					new Weapon(weaponName, 1, 3, 10, 15));
		}//end else
		
		
		System.out.println("\nYou take a second to stare at your new " + player.getWeapon().getName() + ".\n"
				+ "You have never used a weapon before and as you begin to give it a practice\n"
				+ "swing a man in a guard uniform grabs your arm and tells you you're up.\n"
				+ "He pushes you toward the gates to the arena. You can feel your pulse racing\n"
				+ "and heart pounding, your hands sweating so much you fear you may not be able to keep\n"
				+ "a grip on your " + player.getWeapon().getName() + ".\n");
		
		scanner.nextLine();
		
		
		// FIGHT 1
		// ======================================================================================
		System.out.println("Before you get a chance to change your mind, the gates begin to crank open and\n"
				+ "you're pushed so hard you fall face first into the dirt. You hear the crowd\n"
				+ "boasting a mixed reaction of cheers and laughs as you clamber to your feet. You look\n"
				+ "ahead and catch your first glimpse of your opponent. A thin, patchy furred wolf\n"
				+ "that looks at you with a fervent hunger. The handlers, seeing you\n"
				+ "standing and bearing a weapon, release the reins and it charges toward you.\n");
		
		System.out.println("\tAttack (1)\n\tRun (2)");
		
		//create wolf enemy
		Enemy enemy = new Enemy("wolf", 10, 0, 5, new Weapon("teeth", 1, 3, 5, 20));
		choice = getUserInt(1,2, scanner);
		
		//decide which character attacks first based on choice
		switch(choice)
		{
		case 1:
			player.attack(enemy);
			scanner.nextLine();
			
			//only allowing wolf to attack if not killed
			if(!godMode)
			{
				enemy.attack(player);
				scanner.nextLine();
			}
			break;
			
		case 2:
			System.out.println("\nIn a moment of panic you turn back toward the gate to find it just\n"
					+ "finishing its descent back into the ground. The guard looks you in the eye and\n"
					+ "shakes his head. You aren't getting out of this one without a fight.\n");
			scanner.nextLine();
			
			System.out.println("You turn back and see the wolf rapidly approaching. It leaps forward\n"
					+ "and attempts to bite you.\n");
			scanner.nextLine();
			
			enemy.attack(player);
			scanner.nextLine();
			
			break;
		}//end switch
		
		//creating the fight options vector and adding the current only option, "Fight"
		Vector<String> options = new Vector<String>();
		options.add("Fight");
		
		//skipping combat if GodMode, wolf already dead
		if(!godMode)
		{
			//entering combat with wolf, ending game if player dies
			if(!fight(player, enemy, scanner, options))
			{
				return;
			}
		}
		
		
		System.out.println("The wolf yelps one final time and sinks into the dirt.\n"
				+ "You've done it. The crowd roars and you feel mixed emotions of grief,\n"
				+ "having commit your first kill, and the adrenaline of victory and admiration.\n");
		
		scanner.nextLine();	
		
		System.out.println("The gate rises and the guard pulls you back into the hall. He tells you\n"
				+ "that you did a good job and that everybody is surprised. He opens your hand and\n"
				+ "thrusts a bag into it containing 5 gold. You're left in awe, looking at more money\n"
				+ "than your entire family usually makes in a month. He gives you the option to\n"
				+ "quit now, or fight again and try your hand at a bigger fortune.\n");
		
		scanner.nextLine();
		
		//pay player for victory
		player.addGold(enemy.getGold());
		scanner.nextLine();
		
		System.out.println("\tQuit (1)\n\tContinue (2)");
		choice = getUserInt(1, 2, scanner);
		
		//progress story based on choice
		if(choice == 1)
		{
			System.out.println("He sends you to the infirmary where a kind nurse patches your wounds,\n"
					+ "before you take your winnings and head to the local pub, where you enjoy\n"
					+ "the fruits of your labour. Perhaps a little too much, as you awake seemingly\n"
					+ "the next morning. Your gold purse is nowhere to be found.\n");
			
			scanner.nextLine();
			
			//set gold to zero because player robbed
			if(!godMode)
			{
				  player.setGold(0);
				  scanner.nextLine();
			}
			
			System.out.println("Embarassed, sore and hungover, you waddle back to the arena. As you\n"
					+ "walk in, you see the same guard from yesterday that pushed you into the ring.\n"
					+ "He smirks and says, \"They always come back\". He hands you back your\n"
					+ player.getWeapon().getName() + " and you stumble towards the gate, the familiar rush sobering\n"
					+ "you up quickly.\n");
			
			scanner.nextLine();
			
		}
		else
		{
			System.out.println("\nAfter a quick trip to the infirmary where a kind nurse patches your wounds,\n"
					+ "you head back to the gate feeling rejuvinated and less stressed than the first time.\n"
					+ "The guard stops you and hands you some leather armour, to \"make it fair\" he says.\n"
					+ "You put it on, and while it fits a little loose, you continue to the gates feeling a bit safer.\n");
			scanner.nextLine();
		}//end else
		
		// FIGHT 2
		// ======================================================================================
		
		//reset player's health to full
		player.fullHeal();
		
		//only setting new armour if player doesn't have better armour already
		if(!godMode)
			player.setArmour(new Armour("leather", 1));

		
		System.out.println("The gates open and you hear the crowd chanting your name; \""
				+ player.getName() + ", " + player.getName() + "\"!\n"
				+ "You find your way in a little more gracefully this time.\n"
				+ "You feel your stomach drop as you look up and realize that your opponent isn't\n"
				+ "a wolf this time. In fact, it isn't a beast at all. It's a man, about your height\n"
				+ "but a little stronger. He looks at you with a frightening determination and charges\n"
				+ "immediately. Your conscience is unsure about your willingness to kill a human being\n"
				+ "but the length of his stride tells you the choice is not up to you.\n");
		
		scanner.nextLine();
		
		//create man enemy
		enemy = new Enemy("man", 15, 1, 15, new Weapon("short sword", 1, 3, 5, 20));
		
		//begin fight, exit if player dies
		if(!fight(player, enemy, scanner, options))
		{
			return;
		}
		
		System.out.println("The man's eyes widen as your final blow saps the life from him.\n"
				+ "It's a terrifying yet relieving sight, and you cling to the belief that it's\n"
				+ "better him than you. In your moment of doubt you had tuned out the crowd, but\n"
				+ "coming back to your senses the roar pushes its way to the forefront of your mind. \n"
				+ "You are once again hit with that adrenaline, and stretch a guilty smile across your\n"
				+ "face. You walk back through to gate towards the guard who hands you a handsome 20 gold.\n"
				+ "The feeling is surreal yet undeniable. You want more.\n");
		
		scanner.nextLine();
		
		//pay player for victory
		player.addGold(20);
		scanner.nextLine();
		
		System.out.println("While getting stitched up in the infirmary the guard comes to ask you what\n"
				+ "you plan on doing with your new fortune. You begin to tell him of the adventures you\n"
				+ "always dreamt of and he laughingly interjects, \"No you idiot, I mean what are you going\n"
				+ "to buy for your next fight\". You're taken aback by his confidence but when your eyes meet\n"
				+ "you both know there will be a next fight.\n");
		
		player.fullHeal();
		scanner.nextLine();
		
		System.out.println("He leads you toward a sort of stall down in an area of the arena you had never\n"
				+ "seen before. The table is covered in various weapons, armour, trinkets and drinks. \"This\n"
				+ "one's on the house\" the guard says as he hands you a strange vial full of viscous red liquid.\n"
				+ "\"Drink that if you're feeling a little woozey, it'll give you the kick you need\".\n"
				+ "You clutch your coin purse and browse the table furtively.\n");
		
		scanner.nextLine();
		
		//add Inventory option to combat now that player has potion
		options.add("Check Inventory");
		player.potions.addQuantity(1);
		
		//Create shop inventory
		ArmourList armourList = new ArmourList();
		WeaponsList weaponsList = new WeaponsList();		
		
		//start shop interface
		shop(armourList, weaponsList, scanner, player);
		
		System.out.println("You feel an all too familiar hand on your shoulder as the guard informs\n"
				+ "you that it's time for your next fight. You assumed you would have more time and\n"
				+ "begin to feel a slight panic, but then the rush takes back over as you realize you're\n"
				+ "undefeated. Bring it on.");
		
		scanner.nextLine();
		
		System.out.println("You find yourself in the arena yet again, but alone this time. You look\n"
				+ "around and there's a hush over the audience as the gate across the way begins to lift.\n"
				+ "A hulking, 9 foot tall creature with stomping hooves and sharp horns rears its ugly head\n"
				+ "into the arena. The fear chills your entire body as your confidence is sapped out of you,\n"
				+ "as you turn and plea to the guard to let you out. He laughs and says, \"Not for less than\n"
				+ "20 gold\".");
		
		scanner.nextLine();
		
		System.out.println("\nYou:\n\tPay the man (1)\n\tTurn and fight (2)");
		System.out.print("\nEnter your choice: ");
		
		choice = getUserInt(1, 2, scanner);
		
		if(choice == 1 && player.spendGold(20))
		{
			System.out.println("\nThe guard counts the coins one by one and you get increasingly impatient as the\n"
					+ "beast draws closer. He touches the last one and nods at the other guard to lift the gate.\n"
					+ "You duck and crawl under before it's even fully open and claw your way as far away from the door\n"
					+ "as possible. You hear the crowd booing, but you don't care. Your life is worth more than admiration.");
		
			scanner.nextLine();
			
			System.out.println("\"No shame, kid. We all bailed at least once. If I'm being honest, you wouldn't have survived\n"
					+ "that fight. You made the right choice. You've got potential, if you ever want to try again there's always\n"
					+ "more fights to be fought and money to be made\".");
			
			scanner.nextLine();
		}
		else
		{
			if((choice == 1))
			{
				System.out.println("You count your coins and come up short. You beg the guard to let you out with the promise\n"
					+ "of paying the rest later but he shakes his head. You're not getting out of this one.");
				scanner.nextLine();
			}

			System.out.println("With shaking hands you draw your weapon and turn to face your potential demise,\n"
					+ "who you find already walking towards you with violence in his eyes. Here goes nothing.");
		
			//creating minotaur enemy if player still in the arena
			enemy = new Enemy("minotaur", 30, 0, 30, new Weapon("axe", 2, 5, 15, 15));
			
			if(!fight(player, enemy, scanner, options))
			{
				return;
			}
			
			System.out.println("The minotaur topples over with your final swing, creating a sonorous impact as he reaches\n"
					+ "the ground. The crowd errupts in the most cheering you have ever heard as you once again battle grief,\n"
					+ "panic and excitement. You wave to the crowd as you saunter back through the gate. \"I'll be honest, we\n"
					+ "didn't expect you to win that fight. Good work, kid\" the guard says with a smirk as he tosses you your winnings.\n" 
					+ "\"There will always be more fights for you and more money to be had. Come back any time\".");
			
			scanner.nextLine();
			
			player.addGold(enemy.getGold());
			scanner.nextLine();
		} //end if-else
		
		//introduce infinite combat now that plot is over
		System.out.println("You are now free to fight as you wish. You can shop and fight to your heart's content. Don't\n"
				+ "forget to stock up on potions, weapons and armour, you'll need it for what's to come.");
		
		//add Flea option to combat now that player is taught the 20 gold to quit rule
		options.add("Flea");
		scanner.nextLine();
		
		// INFINITE MODE
		// ===================================================================================
		
		//create a list of enemy types to randomly select enemies from
				String enemyTypes[] = {"minotaur", "bear", "knight", "zombie", "vampire",
										"werewolf", "troll", "giant", "dualist", "mutant"};

		int killCount;
		
		//setting kill count to two if minotaur was never fought, since last enemy would still be man
		if(enemy.getName().equals("man"))
			killCount = 2;
		else
			killCount = 3;
		
		boolean won = false;
		boolean quit = false;
		
		//start infinite combat
		do
		{
			//increase max health for every 5 kills
			if(killCount % 5 == 0)
			{
				player.setMaxHealth(player.getMaxHealth() + 5);
				System.out.println("\nYour experience in battle has hardened you. You feel stronger.");
				
				scanner.nextLine();
			}
			
			//reset health
			player.fullHeal();

			//give player choice to fight or shop
			do
			{	
				choice = getMenuOptions(scanner);
				if(choice == 2)
					shop(armourList, weaponsList, scanner, player);
				if(choice == 3)
					quit = true;
			}while(choice != 1 && !quit); //allow for multiple visits of shop until fight chosen
			
			//exit if player quit
			if(quit)
				break;
			
			//create a random enemy
			enemy = createRandomEnemy(enemyTypes, player, killCount, godMode);
			
			//introduce enemy
			System.out.println("Your new opponent is a " + enemy.getName() + ".\n");
			
			scanner.nextLine();
			
			//fight enemy
			won = fight(player, enemy, scanner, options);
			
			if(won)
			{
				killCount++;
				System.out.println("The " + enemy.getName() + " falls to the ground. You win!\n");
				
				scanner.nextLine();
				
				//get payment for kill
				player.addGold(enemy.getGold());
				scanner.nextLine();
			}
			else
			{
				//if player fled, restart options
				if(player.isAlive())
					continue;
			}//end if-else
		} while (player.isAlive());

		//output achievement
		System.out.println("Thank you for playing! You killed " + killCount + " monsters. Try again for a higher score.");
		
		scanner.close();
	}//end main
	
	/**
	 * Method Name:getUserInt()
	 * Purpose: a static method that gets and validates user input between given range
	 * Accepts: an int that is the lowest acceptable answer, one that is the highest
	 * 			and a scanner to get input with.
	 * Returns: a validated int within given range
	 * Coder: Hunter Bennett
	 * Date: Oct 27, 2019 
	*/
	public static int getUserInt(int low, int high, Scanner scanner)
	{
		int choice = -1;
		boolean valid = false;
		do
		{
			try
			{
				choice = Integer.parseInt(scanner.nextLine());
				if(choice >= low && choice <= high)
				{
					valid = true;
				}
			}
			catch(NumberFormatException ex)
			{
				choice = -1;
			}//end try-catch
			
			if(!valid)
			{
				System.out.print("Invalid input, please enter a number between " + low 
						+ " and " + high + " (inclusive): ");
			}
			
		} while (!valid);
		return choice;
	}
	
	/**
	 * Method Name: fight()
	 * Purpose: a static method that encompasses all aspects of combat including options, item use and attacks.
	 * Accepts: A player and an enemy object to fight, a scanner for user input, and a vector of strings
	 * 			containing the currently available combat options.
	 * Returns: True if player survived (won or fled) or false if they died
	 * Coder: Hunter Bennett
	 * Date: Oct 27, 2019 
	*/
	public static boolean fight(Player player, Enemy enemy, Scanner scanner, Vector<String> options)
	{
		int choice1 = -1;
		int choice2 = -1;
		do
		{
			printCombatOptions(options);
			choice1 = getUserInt(1, options.size(), scanner);
			switch(choice1)
			{
			//Fight
			case 1:
				player.attack(enemy);
				scanner.nextLine();
				break;
				
			//Inventory
			case 2:
				if(player.printInventory())
				{
					choice2 = getUserInt(0, 1, scanner);
					if(choice2 == 0)
						break;
					else
					{
						player.useHealthPotion();
						System.out.println("\nYou feel much better.");
					} //end if-else
				} //end if
				break;
				
			//Flea
			case 3:
				if(player.flea())
				{
					return false;
				}
				break;
			}
			
			if(enemy.isAlive())
			{
				enemy.attack(player);
				scanner.nextLine();
			}
		} while (player.isAlive() && enemy.isAlive());
		
		
		//return whether player survived or not
		if(player.isAlive())
			return true;
		else
		{
			player.die();
			return false;
		}//end if-else
	}
	
	/**
	 * Method Name: printCombatOptions()
	 * Purpose: a static method that prints the currently available combat options
	 * Accepts: a vector of strings containing the options
	 * Returns: Nothing
	 * Coder: Hunter Bennett
	 * Date: Oct 27, 2019 
	*/
	public static void printCombatOptions(Vector<String> options)
	{
		System.out.println("\nIt's your turn, you:\n");
		
		//print options
		for(int i = 0; i < options.size(); i++)
		{
			System.out.println("\t" + options.get(i) + " (" + (i + 1) + ")");
		}
		System.out.print("\nChoose an option: ");
	}
		
	/**
	 * Method Name: shop()
	 * Purpose: a static method that displays the shop's inventory and allows player to
	 * 			purchase items, validating that they have the gold to do so.
	 * Accepts: An armourList and weaponsList containing the available items, a scanner for input, and
	 * 			a player object to sell items to.
	 * Returns: Nothing
	 * Coder: Hunter Bennett
	 * Date: Oct 27, 2019 
	*/
	public static void shop(ArmourList armourList, WeaponsList weaponsList, Scanner scanner, Player player)
	{
		int choice1, choice2; 
		boolean exit = false;
		
		do
		{
			//introduce item categories
			System.out.println("\nYou have " + player.getGold() + " gold. What would you like to buy?");
			System.out.println("\n\tHealth Potions (1)"
					+ "\n\tWeapons (2)"
					+ "\n\tArmour (3)"
					+ "\n\tNothing (4)");
			
			//get selection
			choice1 = getUserInt(1, 4, scanner);
			switch(choice1)
			{
			//health potions
			case 1:
				System.out.print("Health potions cost " + player.getPotions().getCost() + " gold. You have " 
						+ player.potions.getQuantity() + ".\n"
						+ "How many would you like? ");
				choice2 = getUserInt(0, 10, scanner);
				
				//purchase potions
				if(choice2 > 0)
				{
					if(player.buyHealthPotions(choice2))
					{
						System.out.print("\nYou purchased " + choice2 + " health potion");
						
						//only make plural if necessary
						if(choice2 > 1)
							System.out.print("s");
						
						System.out.println(".");
					}
					else 
					{
						System.out.println("\nInsufficient funds.\n");
					}//end if-else
				}
				break;
				
			//weapons
			case 2:
				weaponsList.printList();
				
				System.out.print("Current Weapon:\t\t");
				player.getWeapon().printWeapon();
				
				System.out.print("\nWhich would you like to buy (0 to exit)? ");
				choice2 = getUserInt(0, weaponsList.getList().size(), scanner);
				
				//check exit condition
				if(choice2 == 0)
					break;
				
				//check if player has enough gold to buy selected item
				if(player.spendGold(weaponsList.getList().get(choice2 - 1).getCost()))
				{
					Weapon purchasedWeapon = weaponsList.getList().get(choice2-1);
					System.out.println("\nCongratulations! You now own a new " + 
					purchasedWeapon.getName() + ".\n");
					
					player.setWeapon(purchasedWeapon);
					
					scanner.nextLine();
				}
				else 
				{
					System.out.println("\nInsufficient funds.");
				}//end if-else
				 
				break;
				
			//armour
			case 3:
				armourList.printList();
				
				System.out.print("Current Armour:\t");
				player.getArmour().printArmour();
				
				System.out.print("\nWhich would you like to buy (0 to exit)? ");
				choice2 = getUserInt(0, armourList.getList().size(), scanner);
				
				if(choice2 == 0)
					break;
				
				if(player.spendGold(armourList.getList().get(choice2 - 1).getCost()))
				{
					Armour purchasedArmour = armourList.getList().get(choice2-1);
					System.out.println("\nCongratulations! You now own a new " + 
					purchasedArmour.getName() + ".\n");
					
					player.setArmour(purchasedArmour);
					
					scanner.nextLine();
				}
				else 
				{
					System.out.println("\nInsufficient funds.");
				}
				
				break;
			
			//nothing
			case 4:
				exit = true;
				break;
			}
		}while (!exit);
	}
	
	/**
	 * Method Name: getMenuOptions()
	 * Purpose: a static method that prints the out of combat menu options and gets a selection
	 * Accepts: a scanner object for input
	 * Returns: an int that is the selected option
	 * Coder: Hunter Bennett
	 * Date: Oct 27, 2019 
	*/
	public static int getMenuOptions(Scanner scanner)
	{
		System.out.println("\nWhat would you like to do?\n\tFight (1)\n\tShop (2)\n\tQuit (3)");
		return getUserInt(1, 3, scanner);
	}
	
	/**
	 * Method Name: createRandomEnemy()
	 * Purpose: a static method that generates a randomized enemy based on character's progression
	 * Accepts: A string array of enemy names, a player object and an integer number of kills to
	 * 			set enemy stats accordingly.
	 * Returns: An enemy object
	 * Coder: Hunter Bennett
	 * Date: Oct 27, 2019 
	*/
	public static Enemy createRandomEnemy(String enemyTypes[], Player player, int kills, boolean godMode)
	{
		String name = enemyTypes[generateRandomNumber(0, enemyTypes.length - 1)];
		int maxHealth = generateRandomNumber(player.getMaxHealth() - 10, player.getMaxHealth() + 10);
		if(godMode)
			maxHealth -= 980;
		int armour = generateRandomNumber(kills/5, kills/5 + 2); //intentional integer division
		int gold = generateRandomNumber(kills * 4, kills * 5);
		Weapon weapon = new Weapon(name, kills/5 + 1, 
				generateRandomNumber(kills/5 + 2, kills/5 + 4), 
				generateRandomNumber(5, 20), 
				generateRandomNumber(5, 25));
		
		return new Enemy(name, maxHealth, armour, gold, weapon);
	
	}
	
	/**
	 * Method Name: generateRandomNumber()
	 * Purpose: a static method that returns a random number within given range
	 * Accepts: an int that is the lowest desired number, and one that is the highest
	 * Returns: the randomly generated int
	 * Coder: Hunter Bennett
	 * Date: Oct 27, 2019 
	*/
	public static int generateRandomNumber(int low, int high)
	{
		return (int)(Math.random() * ((high - low) + 1)) + low;
	}
}//end class