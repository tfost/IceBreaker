package tiles;

import java.awt.Color;

/**
 * A button is a tile in a level that must be pushed. All buttons within a level
 * must be set into their valid state in order for the level to be completed.
 *
 *NOTE : this class is fairly generic. Clients should use subclasses of Buttons with
 *their own nuanced purposes, instead of using this parent class.
 * @author Tyler
 *
 */
public class Button extends Tile {
	public boolean canMoveInto() {
		return true;
	}
	
	public Color getColor() {
		return Color.BLUE;
	}
	
	//Updates this button according to what should happen once a player steps on this tile.
	public void onMoveInto() {
		
	}
	
	public boolean isValid() {
		return false;
	}
	
	public void reset() {
		
	}
}
