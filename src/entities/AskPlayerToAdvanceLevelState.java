package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import display.Camera;
import interfaces.EntityState;
import io.KeyboardInput;
import main.GamePanel;

public class AskPlayerToAdvanceLevelState implements EntityState {

	private Player p;
	private boolean yes;
	
	public AskPlayerToAdvanceLevelState(Player p) {
		this.p = p;
		this.yes = false;
		System.out.println("On staircase");
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void paint(Graphics g, BufferedImage img, Camera c) {
		p.defaultPaint(g, img, c);
		g.setColor(Color.BLUE);
		g.drawString("Would you like to continue?", 100, 100);
		if (yes) {
			g.setColor(Color.green);
		} else {
			g.setColor(Color.red);
		}
		g.drawString("Yes", GamePanel.WIDTH / 4, GamePanel.HEIGHT * 3 / 4);
		if (yes) {
			g.setColor(Color.red);
		} else {
			g.setColor(Color.green);
		}
		g.drawString("No", GamePanel.WIDTH  * 3 / 4, GamePanel.HEIGHT * 3 / 4);
		
	}

	@Override
	public void handleInput(KeyboardInput input) {
		if (input.keyDownOnce(KeyEvent.VK_ENTER)) {
			if (yes) {
				this.p.setInlevel(false);
			} else {
				this.p.setState(new IdleState(p));
			}
		} else if (input.keyDownOnce(KeyEvent.VK_LEFT) || input.keyDownOnce(KeyEvent.VK_A) || 
					input.keyDownOnce(KeyEvent.VK_RIGHT) || input.keyDownOnce(KeyEvent.VK_D))  {
			this.yes = !this.yes;
		}
	}
	
}
