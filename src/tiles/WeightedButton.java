package tiles;

import java.awt.Color;

public class WeightedButton extends Button{
	
	private boolean pressed;
	
	public WeightedButton() {
		super();
		pressed = false;
	}
	
	public void onMoveInto() {
		pressed = true;
	}
	
	public void onExit() {
		pressed = false;
	}
	
	public Color getColor() {
		if (pressed) {
			return Color.green;
		} else {
			return Color.gray;
		}
	}
	
	public boolean isValid() {
		return pressed;
	}
	
	public void reset() {
		pressed = false;;
	}
}
