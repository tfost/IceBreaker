package potions;

import interfaces.Entity;
import items.Potion;

public class HarmingPotion extends Potion{
	
	public HarmingPotion(String name, int power) {
		super(name, power);
	}
	
	public HarmingPotion() {
		this("Harming Potion", 10);
	}
	
	public void applyEffect(Entity e) {
		e.setHP(e.getHP() - this.getPower());
	}
}
