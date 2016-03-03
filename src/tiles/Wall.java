package tiles;

import java.awt.Color;

public class Wall extends Tile{
	
	public boolean canMoveInto() {
		return false;		
	}
	
	public Color getColor() {
		return Color.DARK_GRAY;
	}
	
}
