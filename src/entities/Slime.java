package entities;

import interfaces.Entity;
import main.Level;

//This class represents a test of the entity framework. It uses
// a basic behavior object to move around. 
public class Slime extends Entity{
	private AIBehavior behavior;
	
	public Slime(int x, int y, Level l) {
		super(x, y, l);
		this.behavior = new TestBehavior(l, this);
		this.imgX = 0;
		this.imgY = 48;
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
