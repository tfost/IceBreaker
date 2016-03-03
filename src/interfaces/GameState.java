package interfaces;

import java.awt.Graphics;

import io.KeyboardInput;

/**
 * The gamestate details all possible states of the game
 * These may include anything from the main menu, a level,
 * level select, world map, etc. 
 * 
 * @author Tyler
 *
 */
public abstract class GameState {
	
	protected KeyboardInput keyboard;
	
	public GameState(KeyboardInput input) {
		this.keyboard = input;
	}
	
	//runs update code for a frame of the game.
	public abstract void update();
	
	//draws a single frame of the game to the screen.
	public abstract void paint(Graphics g);
	
	//returns the next state the game should go into after
	//we finish with the current gamestate.
	public abstract GameState nextState(); 
	
	//returns if we should continue processing this state or not.
	public abstract boolean inState();
	
	//handles keyboard input
	//public void handleInput(KeyboardInput keyboard);
}
