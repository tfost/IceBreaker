package tiles;

import java.awt.Color;

public class Tile {
	
	public boolean canMoveInto() {
		return false;
	}
	
	public Color getColor() {
		return Color.MAGENTA;
	}
	
	public boolean hasFriction() {
		return true;
	}
	
	//Button Functions
	public void onMoveInto() {} //to be implemented by Button but maintain functionality here.
	public void onExit() {} //called after leaving a tile.
	public void reset() {}
}
