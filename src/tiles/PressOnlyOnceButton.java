package tiles;

import java.awt.Color;

/**
 * A PressOnlyOnceButton is a button that is only valid when it has been
 * pushed exactly one time.
 * It is yellow if it still needs to be pressed, green if it's valid,
 * and red otherwise.
 * @author Tyler
 *
 */
public class PressOnlyOnceButton extends Button {
	
	protected int presses;
	
	public void onMoveInto() {
		this.presses++;
	}
	
	public Color getColor() {
		if (presses == 0) {
			return Color.yellow;
		} else if (presses == 1) {
			return Color.GREEN;
		} else {
			return Color.RED;
		}
	}
	
	public boolean isValid() {
		return this.presses == 1;
	}
	
	public void reset() {
		this.presses = 0;
	}
}
