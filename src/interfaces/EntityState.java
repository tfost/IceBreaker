package interfaces;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import io.KeyboardInput;

public interface EntityState {
	
	public void update();
	public void paint(Graphics g, BufferedImage img);
	public void handleInput(KeyboardInput input);
	
}
