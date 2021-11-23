import java.util.*;

public class TextAdventure
// note: all instances of methods calling themselves are for beginning zones again after actions are made
{
  //Instance variables of a class can also be objects
  FancyConsole console;
  Scanner inScanner;
  Player ourHero;

  public TextAdventure()
  {
    //This creates the UI console that the user
    //can type in
    console = new FancyConsole("Minecraft Adventure!", 600, 600);
    inScanner = new Scanner(System.in);

    // see Player class for instance variables
    ourHero = new Player("Steve", 20, false, false, 0, 0);
  }

  public void play()
  {
    String input;
    //Start of adventure
    console.setImage("overworld.jpeg");

    // ask the user for their name.
    System.out.println("What is your name?\n");
    input = inScanner.nextLine();

    // Change ourHero's name
    ourHero.changeName(input);
    
    // describe the starting situation and gives choices
    System.out.println("\nYou find yourself in a strange block world.\nLost and confused, you are guided purely by an irrational urge to slay a dragon.\nFar in the distance you see a village, and to your right you see a lava pool that's been made obsidian.\n\nWhat would you like to do?\nvillage: walk over to the village\nobsidian: walk over to the obsidian\n");

    // get user input and go to the appropriate zone based on their input
    input = inScanner.nextLine();
    if (input.equals("village")) {
        enterVillage();
    } else if (input.equals("obsidian")) {
        enterObsidian();
    } else { // this is called if input doesn't match "village" or "obsidian"
        System.out.println("\nUnrecognized input\n");
        play(); // restarts zone
    }

  }

  private void enterVillage()
  {
    String input;
      
    // change image
    console.setImage("village.jpeg");

    // description of village and choices
    System.out.println("\nYou stumble into the village and look around. No villagers in sight, but you do spot a chest and a crafting table.\n\nWhat would you like to do?\nloot: loot the chest\ncraft: use the crafting table\nleave: head to the obsidian pit\n");

    // Take action or go to obsidian pit
    input = inScanner.nextLine();
    if (input.equals("loot")) {
        if (ourHero.getLootedChest()) { // checks if chest has already been looted
            System.out.println("\nYou've alreadly looted the chest!\n"); // if it has, you are not allowed to do it again
    } else { // will run if its the first attempt to loot
        System.out.println("\nYou found a flint and steel, 5 diamonds, a stack of gold, and 3 sticks. Lucky you!\n");
        // adds each item from the chest to the players inventory (ArrayList)
        ourHero.addItem("flint and steel");
        ourHero.addItem("5 diamonds");
        ourHero.addItem("3 sticks");
        ourHero.addItem("stack of gold");
        ourHero.loot(); // sets ourHero.chestLooted = true so that chest cant be looted again
    }
    enterVillage();
     } else if (input.equals("craft")) {
      if (ourHero.getInventory().indexOf("5 diamonds") != -1 && ourHero.getInventory().indexOf("3 sticks") != -1) { // checks if the player has necessary items in inventory to craft
          System.out.println("\nYou've successfully crafted a diamond pickaxe and a diamond sword!\n");
          // adds new crafted items to inventory and removes items used
          ourHero.addItem("diamond pickaxe");
          ourHero.addItem("diamond sword");
          ourHero.removeItem("5 diamonds");
          ourHero.removeItem("3 sticks");
        } else { // cant craft if you dont have right items
            System.out.println("\nYou have nothing to craft here.\n");
        }
        enterVillage();
    } else if (input.equals("leave")) { // sends player to obsidian pit if they say "leave"
        enterObsidian();
    } else { // similar to before: all unrecognized input
        System.out.println("Unrecognized input");
        enterVillage();
    }
  }
  private void enterObsidian()
  {
    String input;
    if (!ourHero.getPortalMade()) { // if the portal is not made, area will be different
        console.setImage("obsidian.png");
        System.out.println("\nYou come across the obsidian it, and it looks like there's just enough for a Nether portal.\n\nWhat would you like to do?\nmine: mine the obsidian and form a portal\nleave: head back to the village\n");
        input = inScanner.nextLine();
        if (input.equals("mine")) { // if the player decides to mine
            if (ourHero.getInventory().indexOf("diamond pickaxe") == -1) { // this is only allowed if the player has a diamond pick, so if they dont, nothing will happen
                System.out.println("\nYou have no pickaxe to mine this with.\n");
                enterObsidian();
            } else { // runs if they have a pick
                System.out.println("\nYou successfully mine the obsidian and construct a Nether portal\n");
                ourHero.makePortal(); // sets ourHero.portalMade = true
                enterObsidian(); // re-enters obsidian pit, but in version where portal is made
            }
        } else if (input.equals("leave")) { // sends to village if they say to leave
            enterVillage();
        } else {
            System.out.println("\nUnrecognized input\n");
            enterObsidian();
        }
    } else if (ourHero.getPortalMade()) { // runs if a portal was made
        console.setImage("unlit portal.jpeg");
        System.out.println("\nYou are standing in front of the portal you have constructed.\n\nWhat would you like to do?\nlight: light the portal\nleave: head to the village\n");
        input = inScanner.nextLine();
        if (input.equals("leave")) { // sent to village if ask to leave
            enterVillage();
        } else if (input.equals("light")) { // if they try to light portal
            if (ourHero.getInventory().indexOf("flint and steel") == -1) { // checks if theres a flint and steel in inventory, if not, portal cannot be lit
                System.out.println("\nYou have no flint and steel to light this\n");
                enterObsidian();
            } else { // if there is a flint and steel
                console.setImage("portal.jpeg");
                System.out.println("\nYou light the portal and it shines a brilliant purple.\nPress enter to step inside.\n");
                input = inScanner.nextLine(); // adds a buffer so that player can see picture and read
                enterNether(); // sends player to nether
            }
        } else {
            System.out.println("\nUnrecognized input\n");
            enterObsidian();
        }
    }
  }

  private void enterNether()
  {
    String input;
    // change image
    console.setImage("nether.jpeg");

    // describe the area/situation to the user. 
    // Give them options for choices.
    System.out.println("\nWhen you come out the other side of the portal, you find yourself in a different dimension.\nEverything is deathly hot and scarily dark.\nTo your left you can spot a Nether fortress. To your right, a Piglin bastion.\n\nWhat would you like to do?\nfortress: run over to the Nether fortress\nbastion: check out the Piglin bastion\n");

    // Take action or go to another zone
    input = inScanner.nextLine();
    // 2 options: player either goes to fortresss or bastion. choice sends them accordingly
    if (input.equals("fortress")) {
        enterFortress();
    } else if (input.equals("bastion")) {
        enterBastion();
    } else {
        System.out.println("\nUnrecognized input\n");
        enterNether();
    }
  }

  private void enterFortress()
  {
    String input;
    if (ourHero.getRods() >= 6 && ourHero.getPearls() >= 12) { // check for win condition - if these are met, do not load zone, instead end game
        gameEnd();
    } else { // if win conditions are not met, load zone.
    // change image
    console.setImage("fortress.png");

    // description and choices
    System.out.println("\nYou've reached the Nether fortress, and you're standing in front of the blaze spawner.\n6 blaze rods are needed to make enough eyes of ender. You have " + ourHero.getRods() + ".\n\nWhat would you like to do?\nfight: fight blazes for the chance to get blaze rods\nleave: head to the bastion (this will heal you)\n");

    // Take action or go to another zone based on their choice
    input = inScanner.nextLine();
    if (input.equals("fight")) {// if the player wants to fight blazes
        int success = (int)(Math.random() * 2); // 50/50 chance of success
        if (success == 0) {// 50% of the time is a failure, and you take damage
            int damage = (int)(Math.random() * 3) + 4; // damage also randomly calculated, damage btwn 4-6 health
            ourHero.setHealth(ourHero.getHealth() - damage); // sets ourHero.health to #damage less than it was before (subtracts #damage)
            if (ourHero.getHealth() < 1) { // if the player suffers a fatal blow
                System.out.println("\nYou were killed by a blaze!\nFortunately, you can respawn.\nUnfortunately, you have to start over...\n");
                ourHero.respawn(); // resets stats
                play(); // restarts game
            }
            System.out.println("\nYou were hit by a blaze for " + damage + " damage! Your health is now " + ourHero.getHealth() + "/20.\n");
            enterFortress();
        } else { // 50% of the time its a success, and you gain rods
            int rodsGained = (int)(Math.random() * 3) + 1; // rods gained is randomly calculated between 1 and 3
            ourHero.addRods(rodsGained); // adds gained rods to ourHero.rods, increasing total
            System.out.println("\nYou successfully killed a blaze and gained " + rodsGained + " rods! You now have " + ourHero.getRods() + "/6 rods.\n"); 
            enterFortress();
        }
    } else if (input.equals("leave")) { // sends player to bastion if they ask to leave
        enterBastion();
    } else {
        System.out.println("\nUnrecognized input\n");
        enterFortress();
    }
}
  }

  private void enterBastion()
  {
    String input;
    ourHero.setHealth(20); // heals player in case they took damage from blazes
    if (ourHero.getRods() >= 6 && ourHero.getPearls() >= 12) { // similar to above, checks for win condition
        gameEnd();
    } else {
        // change image
        console.setImage("bastion.png");
    
        // describe the area/situation to the user. 
        // Give them options for choices.
        System.out.println("\nYou've reached the bastion, and you come across a piglin.\n12 pearls are needed to make enough eyes of ender. You have " + ourHero.getPearls() + ".\n\nWhat would you like to do?\ntrade: trade some of your gold, hopefully for some ender pearls\nleave: head over to the fortress\n");
        
        // Take action or go to another zone based on their choice
        input = inScanner.nextLine();
        if (input.equals("trade")) { // if the player wants to trade for ender pearls
            int pearlsGained = (int)(Math.random() * 5); // pearls gained from trade is random 0-4
            if (pearlsGained == 0) { // gives different message if pearlsGained = 0, because its a failure
                System.out.println("\nSorry, you didn't get any pearls from this trade.\n");
                enterBastion();
            } else {
                ourHero.addPearls(pearlsGained); // adds pearlsGained to total number of pearls
                System.out.println("\nThe trade was successful and you gained " + pearlsGained + " pearls! You now have " + ourHero.getPearls() + "/12 pearls.\n");
                enterBastion();
            }
        } else if (input.equals("leave")) { // sends player to fortress if they ask to leave
            enterFortress();
        } else {
            System.out.println("\nUnrecognized input\n");
            enterBastion();
        }
    }
  }

  private void gameEnd()
  {
    // goes through entire end of the game, which is a series of slides
    // each one shows a picture and a description and waits for user to press enter as a buffer
    String input;
    console.setImage("eye.jpeg");
    System.out.println("\n\nUsing the blaze rods and ender pearls you've collected, you made enough eyes of ender.\n(Press enter to continue)\n");
    input = inScanner.nextLine();
    console.setImage("stronghold.jpeg");
    System.out.println("\n\nWith these, you found the stronghold and made your way into...\n(Press enter to continue)\n");
    input = inScanner.nextLine();
    console.setImage("end.png");
    System.out.println("\n\nTHE END!\n(Press enter to continue)\n");
    input = inScanner.nextLine();
    console.setImage("dragonDeath.png");
    System.out.println("\n\nUsing your mastery in Minecraft and supreme willpower, youve done it.\nYou've slayed the dragon, " + ourHero.getName() + "!\n");
    inScanner.close();
  }
}