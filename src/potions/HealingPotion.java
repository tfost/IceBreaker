package potions;

import interfaces.Entity;
import items.Potion;

/**
 * The basic healing potion heals 20 points of damage to whatever entity
 * it is used or thrown at.
 * @author Tyler
 *
 */
public class HealingPotion extends Potion{
	
	
	public HealingPotion() {
		this("Basic Healing Potion", 50);
	}
	
	public HealingPotion(String name, int power) {
		super(name, power);
		// TODO Auto-generated constructor stub
	}
	
	public void applyEffect(Entity e) {
		e.setHP(e.getHP() + this.getPower());
	}

}
