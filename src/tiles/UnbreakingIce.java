package tiles;

import java.awt.Color;

public class UnbreakingIce extends Tile{
	
	
		public boolean canMoveInto() {
			return true;
		}
		
		public Color getColor() {
			return Color.cyan;
		}
		
		public boolean hasFriction() {
			return false;
		}
}
