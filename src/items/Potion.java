package items;

import interfaces.Entity;

/**Provides an abstraction for potions. All potions do the same effect to whatever they are 
used on. So, it is up to subclasses to define the effects. However, some things
hold for all potions: if used on soluble enemies, for example, they get hurt (they dissolve!)
*/
public abstract class Potion extends Item {

	private int power;	
	public Potion(String name, int power) {
		super(name);
		this.power = power;
		// TODO Auto-generated constructor stub
	}
	
	// The magnitude of this potion.
	public int getPower() {
		return this.power;
	}
	
	// How the player should affect the user.
	public void use(Entity user) {
		this.applyEffect(user);
	}
	
	// What the potion should do if thrown at something.
	public void throwAt(Entity target) {
		this.applyEffect(target);
	}
	
	// The effect that must be defined for when this potion is used.
	public abstract void applyEffect(Entity e);

}
