package tiles;

import java.awt.Color;

public class Floor extends Tile{
	
	public Floor() {
		super();
		this.setImgX(64);
		this.setImgY(16);
	}
	
	//we can move into floors.
	public boolean canMoveInto() {
		return true;
	}
	
	public Color getColor() {
		return Color.lightGray;
	}
	
		
}
