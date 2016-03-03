package entities;

import java.awt.Color;
import java.awt.Graphics;

import interfaces.Direction;
import interfaces.Entity;
import interfaces.EntityState;
import io.KeyboardInput;
import main.Level;
import tiles.Tile;

public class Player extends Entity {
	///private int x;			//horizontal location of the player.
	//private int y;			//vertical location of the player
	//private Level l;
	//private EntityState state;
	
		
	public Player(int x, int y, Level l) {
		super(x, y, l);
		this.state = new IdleState(this);
	}
	
	public void handleInput(KeyboardInput input) {
		state.handleInput(input);
	}
	
	
	
	public Level getLevel() {
		return l;
	}
	
	protected Color getColor() {
		return Color.blue;
	}
	
	public void setState(EntityState state) {
		this.state = state;
	}

	public void update() {
		this.state.update();
		
	}
	
	public String toString() {
		return "X";
	}
	
}
