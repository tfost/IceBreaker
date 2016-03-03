package tiles;

import java.awt.Color;

public class SlipperyPressOnlyOnceButton extends PressOnlyOnceButton{
	Color unpressed;
	Color pressed;
	Color pressedTooMuch;	
	
	public SlipperyPressOnlyOnceButton() {
		super();
		unpressed = new Color(200,200,120);
		pressed = new Color(0,200,100);
		pressedTooMuch = new Color(255, 0, 0);
	}
	@Override
	public boolean hasFriction() {
		
		return false;
	}
	
	public Color getColor() {
		if (this.presses == 0) {
			return unpressed;
		} else if (this.presses == 1) {
			return pressed;
		} else {
			return pressedTooMuch;
		}
	}
}
