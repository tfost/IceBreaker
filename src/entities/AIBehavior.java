package entities;

import main.Level;
import interfaces.Entity;

/** 
 * The AIBehavior class is responsible for determining what action
 * an AI should take. After calling the generateAction method, 
 * the entity will have either completed an action, or been primed to
 * complete an action.
 * @author Tyler
 *
 */
public class AIBehavior {
	protected Level l;
	protected Entity entity;
	
	public AIBehavior(Level l, Entity e) {
		this.l = l;
		this.entity = e;
	}
	
	
	
	/**Uses the current state of the level, entity, etc to figure out
	 * what action should be taken on this turn.
	 */	
	public void determineAction(int turnnum) {
		
	}
	
	
}
