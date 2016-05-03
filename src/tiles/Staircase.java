package tiles;

import entities.AskPlayerToAdvanceLevelState;
import entities.Player;
import interfaces.Entity;

public class Staircase extends Tile{
	public Staircase() {
		this.setImgX(112);
		this.setImgY(16);
	}
	
	public boolean canMoveInto() {
		return true;
	}
	
	public void onMoveInto(Entity p) {
		if (p.isPlayer()) {
			p.setState(new AskPlayerToAdvanceLevelState((Player) p));
		}
	}
}
