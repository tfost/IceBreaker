package interfaces;

import java.awt.Color;
import java.awt.Graphics;

import entities.IdleState;
import io.KeyboardInput;
import main.Level;

/**
 * An Entity is an object in the game that can move. Examples include
 * the Player, boxes, or rocks that can be pushed around. 
 * @author Tyler
 *
 */
public class Entity {
	protected int x;			//horizontal location of the player.
	protected int y;			//vertical location of the player
	protected Level l;
	protected EntityState state;
	

	public Entity(int x, int y, Level l) {
		this.x = x;
		this.y = y;
		this.l = l;
		this.state = new IdleState(this);
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public void handleInput(KeyboardInput input) {
		//state.handleInput(input);//default does nothing.
	}
	
	//TODO : move in a way that takes time, instead of instantaneously moving
	//which will allow frictionless tiles.
	public void move(int x, int y) {
		if (!l.getTile(x, y).canMoveInto()) {
			throw new IllegalStateException("It is forbidden to move into tile " + x + ", " + y);
		}
		l.getTile(this.x, this.y).onExit();
		this.x = x;
		this.y = y;
		l.getTile(x, y).onMoveInto();
	}
	
	public Level getLevel() {
		return l;
	}
	
	public void paint(Graphics g) {
		g.setColor(this.getColor());
		g.fillOval(this.x * Level.TILE_SIZE, this.y * Level.TILE_SIZE, Level.TILE_SIZE, Level.TILE_SIZE);
	}
	
	protected Color getColor() {
		return Color.GREEN;
	}
	
	public void setState(EntityState state) {
		this.state = state;
	}

	public void update() {
		this.state.update();
		
	}
}
