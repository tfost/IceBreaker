package interfaces;

import java.awt.Graphics;

import io.KeyboardInput;

public interface EntityState {
	
	public void update();
	public void paint(Graphics g);
	public void handleInput(KeyboardInput input);
	
}
