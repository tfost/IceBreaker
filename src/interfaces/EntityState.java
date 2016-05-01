package interfaces;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import display.Camera;
import io.KeyboardInput;

public interface EntityState {
	
	public void update();
	public void paint(Graphics g, BufferedImage img, Camera c);
	public void handleInput(KeyboardInput input);
	
}
