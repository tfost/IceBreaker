package tiles;

import java.awt.Color;

public class Floor extends Tile{

	//we can move into floors.
	public boolean canMoveInto() {
		return true;
	}
	
	public Color getColor() {
		return Color.lightGray;
	}
	
		
}
