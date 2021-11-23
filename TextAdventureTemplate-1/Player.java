import java.util.*;

public class Player
{
  // instance variables - made private as best practice
  
  // basic instance variables for the game
  private String name; 
  private int health;
  private ArrayList<String> inventory = new ArrayList(); // inventory is an ArrayList so things can be added and removed
  // booleans used to distinguish what actions the player has already taken - impacts which code to run
  private boolean lootedChest;
  private boolean portalMade;
  // decided to make these two int variables (instead of items in inventory) so that the amounts can be easily manipulated
  private int pearls;
  private int rods;
  
  // constructor - initializes all instance variables
  public Player(String playerName, int startingHealth, boolean chest, boolean portal, int startingPearls, int startingRods)
  {
    name = playerName;
    health = startingHealth;
    lootedChest = chest;
    portalMade = portal;
    pearls = startingPearls;
    rods = startingRods;
  }

  public String getName() // getter for name
  {
    return name;
  }

  public void changeName(String newName) // changes name at beginning
  {
    name = newName;
  }

  public int getHealth() // getter for health
  {
    return health;
  }

  public void setHealth(int newHealth) // setter for health
  {
    health = newHealth;
  }
  
  public ArrayList getInventory() { // getter for inventory
      return inventory;
  }
  
  public void addItem(String newItem) { // adds items to inventory
      inventory.add(newItem);
    }
    
  public void removeItem(String oldItem) { // removes items
      inventory.remove(inventory.indexOf(oldItem));
    }
    
  public void loot() { // sets lootedChest to true after player decides to
      lootedChest = true;
    }
    
  public void makePortal() { // sets portalMade to true after player does so
      portalMade = true;
    }
    
  public void respawn() { // if player dies, this method resets all instance variables so game can be replayed
      health = 20;
      lootedChest = false;
      portalMade = false;
      pearls = 0;
      rods = 0;
      inventory.clear();
    }
    
  public int getRods() { // getter for rods
      return rods;
    }
    
  public int getPearls() { // getter for pearls
      return pearls;
    }
    
  public void addRods(int newRods) { // adds new rods, updates total
      rods += newRods;
    }
    
  public void addPearls(int newPearls) { // adds new pearls, updates total
      pearls += newPearls;
    }
  
  public boolean getPortalMade() { // getter for portalMade
      return portalMade;
    }
    
    public boolean getLootedChest() { // getter for lootedChest
        return lootedChest;
    }
}