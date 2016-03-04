package tiles;

import java.awt.Color;

public class Wall extends Tile{
	
	public Wall() {
		super();
		this.setImgX(64);
	}
	
	public boolean canMoveInto() {
		return false;		
	}
	
	public Color getColor() {
		return Color.DARK_GRAY;
	}
	
}
