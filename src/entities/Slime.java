package entities;

import java.util.Random;

import interfaces.Entity;
import main.Level;

//This class represents a test of the entity framework. It uses
// a basic behavior object to move around. 
public class Slime extends Entity{
	private AIBehavior behavior;
	
	public Slime(int x, int y, Level l) {
		super(x, y, l);
		Random r = new Random();
		int bhv = r.nextInt(5); // TODO - update - right now, just a 1/5 chance to be a hunter. change at some point.
		if (bhv == 1) {
			this.behavior = new HunterBehavior(this, l);
		} else {
			this.behavior = new TestBehavior(l, this);
		}
		this.imgX = 64;
		this.imgY = 32;
		this.maxHp = this.hp = 10;
		this.atk = 1;
		this.def = 1;
		this.name = "Slime";
	}
	
	public void onTurnStart(int turnnum) {
		super.onTurnStart(turnnum);
		behavior.determineAction(turnnum);
	}
}
