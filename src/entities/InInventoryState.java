package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import display.Camera;
import interfaces.EntityState;
import io.KeyboardInput;
import main.GamePanel;

public class InInventoryState implements EntityState{
	
	private Player p;
	
	public InInventoryState(Player p) {
		this.p = p;
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void paint(Graphics g, BufferedImage img, Camera c) {
		this.p.defaultPaint(g, img, c);
		g.setColor(Color.blue);
		g.fillRect(200, 200, GamePanel.WIDTH - 2 * 200, GamePanel.HEIGHT - 2 * 200);
	}

	@Override
	public void handleInput(KeyboardInput input) {
		if (input.keyDownOnce(KeyEvent.VK_E)) {
			this.p.setState(new IdleState(p));
		}
		
	}

}
