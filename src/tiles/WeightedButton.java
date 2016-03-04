package tiles;

import java.awt.Color;

public class WeightedButton extends Button{
	
	private boolean pressed;
	
	public WeightedButton() {
		super();
		pressed = false;
		this.setImgX(80);
		this.setImgY(16);
	}
	
	public void onMoveInto() {
		pressed = true;
		this.setImgX(96);
	}
	
	public void onExit() {
		pressed = false;
		this.setImgX(80);
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
		pressed = false;
		this.setImgX(80);
		this.setImgY(16);
	}
}
