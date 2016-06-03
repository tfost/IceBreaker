package items;

import interfaces.Entity;
import tiles.Tile;

/**
 * An item is an object that can be picked up and added to the player's or monster's inventory. 
 * Items have many different purposes: some can be equipped, some can be used, some are good
 * for crafting. 
 * @author Tyler
 *
 */
public abstract class Item {
	
	protected String name;
	protected float weight;
	
	public Item(String name) {
		this(name, 0);
	}
	
	public Item(String name, float weight) {
		this.name = name;
		this.weight = weight;
	}
	
	//to be called when used by someone.
	public abstract void use(Entity user);
	
	// to be called when its thrown at a person
	public abstract void throwAt(Entity target);
	
	//TODO: allow items to be thrown at walls and stuff to do different effects.
	//public abstract void throwAt(Tile t);
	
	//called when the item is picked up.
	public void onPickup(Entity pickeruper) {
		System.out.println(pickeruper.getName() + " picked up " + this.name);
	}
	
	
}
